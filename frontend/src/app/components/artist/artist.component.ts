import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Artist } from '../../models/artist.model';
import { SearchService } from '../../services/search.service';
import { SpringResponse } from '../../utils/spring-response';
import { Concert } from '../../models/concert.model';
import { convertDatetime, formatHour } from '../../utils/datetime-utils';
import { Observable, catchError, map, of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-artist',
  templateUrl: './artist.component.html',
  styleUrls: ['./artist.component.css', '../../../styles/concert-style.css']
})
export class ArtistComponent {

    artistId?: number;
    artist?: Artist;
    concerts: Concert[] = [];

    currentPage: number = 0;
    last: boolean = false;

    constructor(private searchService: SearchService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        const artistIdStr = this.route.snapshot.paramMap.get('id') ?? undefined;
        artistIdStr ? this.artistId = parseInt(artistIdStr) : undefined;
        console.log(this.artistId);

        this.searchArtist();
    }

    private searchArtist(): void {
        this.searchService.search<Artist>("/artists/" + this.artistId)
            .subscribe((response: Artist) => {
                this.artist = response;
                this.searchConcerts();
            });
    }

    searchConcerts(): void {
        this.searchService.search<SpringResponse<Concert[]>>("/concerts", { page: this.currentPage, artists: [this.artist!.name] })
            .subscribe((response: SpringResponse<Concert[]>) => {
                this.concerts.push(...convertDatetime(response.content));
            })

        // Workaround
        this.isLast().subscribe((response: boolean) => {
            this.last = response;
        });

        this.currentPage++;
    }

    private isLast(): Observable<boolean> {
        return this.searchService.search<Concert[]>("/concerts", { page: this.currentPage + 1, artists: [this.artist!.name] })
            .pipe(
                map(() => false),
                catchError((error: HttpErrorResponse) => {
                    return of(error.status === 404 ? true : false)
                })
            )
    }

    formatHour(date: Date): string {
        return formatHour(date);
    }
}
