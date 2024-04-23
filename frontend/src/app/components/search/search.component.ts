import { Component } from "@angular/core";
import { SearchService } from "../../services/search.service";
import { LoginService } from "../../services/login.service";
import { NgbNavModule, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { Concert } from "../../models/concert.model";
import { SearchParamsFields } from "../../utils/search-params";
import { Observable, catchError, firstValueFrom, map } from 'rxjs';
import { SpringResponse } from "../../utils/spring-response";
import { Artist } from "../../models/artist.model";

@Component({
    templateUrl: 'search.component.html',
})

export class SearchComponent {

    query?: SpringResponse<Concert[]>;
    concerts: Concert[] = [];
    artists: Artist[] = [];
    locations: String[] = [];

    constructor(private searchService: SearchService, private loginService: LoginService) { }

    ngOnInit() {
        this.searchConcerts();
        this.getArtists();
        this.getLocations();
    }

    private searchPage<T>(relativeUrl: string, params?: SearchParamsFields): Observable<SpringResponse<T>> {

        return this.searchService.search<SpringResponse<T>>(relativeUrl, params ?? {})
            .pipe(
                map((response: SpringResponse<T>) => {
                    return response;
                }),
                catchError((error: HttpErrorResponse) => {
                    throw error;
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

    searchConcerts(params?: SearchParamsFields): void {
        this.searchPage<Concert[]>("/concerts", params ?? {})
            .subscribe((response: SpringResponse<Concert[]>) => {
                this.query = response;
                this.concerts.push(...this.convertDatetime(response.content));
            })
    }

    private convertDatetime(concerts: Concert[]) {
        concerts.forEach((concert) => {
            concert.datetime = new Date(concert.datetime);
        })
        return concerts;
    }

    getArtists(): void {
        this.search<Artist[]>("/concerts/artists")
            .subscribe((response: Artist[]) => {
                this.artists = response;
            })
    }

    getLocations(): void {
        this.search<String[]>("/concerts/locations")
            .subscribe((response: String[]) => {
                this.locations = response;
            })
    }
}
