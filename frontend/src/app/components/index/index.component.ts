import { Artist } from './../../models/artist.model';
import { Component } from '@angular/core';
import { SearchService } from './../../services/search.service';
import { SpringResponse } from '../../utils/spring-response';
import { Observable, catchError, map, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent {

    mainArtist?: Artist;
    artists: Artist[] = [];
    recommendedArtists: Artist[] = [];

    currentPage: number = 0;
    last: boolean = false;

    constructor(private searchService: SearchService, private loginService: LoginService) { }

    ngOnInit() {
        this.searchArtists();
        this.loginService.isLogged()
            ? this.getRecommendedArtists()
            : this.defaultRecommendedArtists();
    }

    searchArtists() {
        this.searchService.search<SpringResponse<Artist[]>>("/artists", { page: this.currentPage, size: 4 })
            .subscribe((response: SpringResponse<Artist[]>) => {
                this.artists = this.artists.concat(response.content);
                if (!this.mainArtist) this.mainArtist = response.content[0];
            });

        // Workaround
        this.isLast()
            .subscribe((response: boolean) => {
                this.last = response;
            });

        this.currentPage++;
    }

    private isLast(): Observable<boolean> {
        return this.searchService.search<SpringResponse<Artist[]>>("/artists", { page: this.currentPage + 1, size: 4})
            .pipe(
                map(() => false),
                catchError((error: HttpErrorResponse) => {
                    return of(error.status === 404 ? true : false);
                })
            )
    }

    private getRecommendedArtists(): void {
        // TODO
    }

    private defaultRecommendedArtists(): void {
        this.searchService.search<SpringResponse<Artist[]>>("/artists", { page: 0, size: 4 })
            .subscribe((response: SpringResponse<Artist[]>) => {
                this.recommendedArtists = response.content;
            });
    }
}
