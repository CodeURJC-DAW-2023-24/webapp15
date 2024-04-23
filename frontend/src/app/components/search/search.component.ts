import { Component } from "@angular/core";
import { SearchService } from "../../services/search.service";
import { HttpErrorResponse } from "@angular/common/http";
import { Concert } from "../../models/concert.model";
import { SearchParamsFields } from "../../utils/search-params";
import { Observable, catchError, map, of, throwError } from 'rxjs';
import { SpringResponse } from "../../utils/spring-response";
import { Artist } from "../../models/artist.model";

@Component({
    templateUrl: 'search.component.html',
})

export class SearchComponent {

    query?: SpringResponse<Concert[]>;
    concerts: Concert[] = [];
    artists: Artist[] = [];
    locations: string[] = [];

    currentPage: number = 0;
    last: boolean = false;

    private checkedArtists: string[] = [];
    private checkedLocations: string[] = [];

    constructor(private searchService: SearchService) { }

    ngOnInit() {
        this.searchConcerts(true);
        this.getArtistsList();
        this.getLocationsList();
    }

    private searchPage<T>(relativeUrl: string, params?: SearchParamsFields): Observable<SpringResponse<T>> {

        return this.searchService.search<SpringResponse<T>>(relativeUrl, params ?? {})
            .pipe(
                map((response: SpringResponse<T>) => {
                    return response;
                }),
                catchError((error: HttpErrorResponse) => {
                    return throwError(() => error);
                })
            )
    }

    private search<T>(relativeUrl: string, params?: SearchParamsFields): Observable<T> {

        return this.searchService.search<T>(relativeUrl, params ?? {})
            .pipe(
                map((response: T) => {
                    return response;
                }),
                catchError((error: HttpErrorResponse) => {
                    throw error;
                })
            )
    }

    searchConcerts(clearSearch: boolean): void {
        if (clearSearch) {this.concerts = [] }
        clearSearch
            ? this.currentPage = 0
            : this.currentPage++;

        this.searchPage<Concert[]>("/concerts", this.getParams(this.currentPage))
            .subscribe((response: SpringResponse<Concert[]>) => {
                this.query = response;
                this.concerts.push(...this.convertDatetime(response.content));
            })

        // This should be included in SpringResponse object, but it doesn't seem to work well
        // so, sketchy workaround for now.
        this.isLast().subscribe((response: boolean) => {
            this.last = response;
        })
    }

    private isLast(): Observable<boolean> {
        return this.searchPage<Concert[]>("/concerts", this.getParams(this.currentPage + 1))
            .pipe(
                map(() => false),
                catchError((error: HttpErrorResponse) => {
                    return of(error.status === 404 ? true : false)
                })
            )
    }

    private convertDatetime(concerts: Concert[]): Concert[] {
        concerts.forEach((concert) => {
            concert.datetime = new Date(concert.datetime);
        })
        return concerts;
    }

    private getArtistsList(): void {
        this.search<Artist[]>("/concerts/artists")
            .subscribe((response: Artist[]) => {
                this.artists = response;
            })
    }

    private getLocationsList(): void {
        this.search<string[]>("/concerts/locations")
            .subscribe((response: string[]) => {
                this.locations = response;
            })
    }

    updateCheckedArtists(artistName: string): void {
        this.checkedArtists = this.updateCheckedList(this.checkedArtists, artistName);
        this.searchConcerts(true);
    }

    updateCheckedLocations(location: string): void {
        this.checkedLocations = this.updateCheckedList(this.checkedLocations, location);
        this.searchConcerts(true);
    }

    private updateCheckedList(list: string[], value: string): string[] {
        return list.includes(value)
            ? list.filter((item) => item !== value)
            : list.concat(value);
    }

    private getParams(page: number): SearchParamsFields {
        return {
            page: page,
            size: 6,
            artists: this.checkedArtists,
            locations: this.checkedLocations,
        }
    }
}
