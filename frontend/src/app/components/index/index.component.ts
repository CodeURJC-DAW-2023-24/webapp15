import { Artist } from './../../models/artist.model';
import { Component } from '@angular/core';
import { SearchService } from './../../services/search.service';
import { SpringResponse } from '../../utils/spring-response';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent {

    mainArtist?: Artist;
    artists: Artist[] = [];
    isLast: boolean = false;
    recommendedArtists: Artist[] = [];

    constructor(private searchService: SearchService) { }

    ngOnInit() {
        this.searchArtists();
    }

    searchArtists() {
        this.searchService.search<SpringResponse<Artist[]>>("/artists", { size: 4 })
            .subscribe((response: SpringResponse<Artist[]>) => {
                this.artists = this.artists.concat(response.content);
                this.mainArtist = response.content[0];
                this.recommendedArtists = response.content; // temporal
            })
    }
}
