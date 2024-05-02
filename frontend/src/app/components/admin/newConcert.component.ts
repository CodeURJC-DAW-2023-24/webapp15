import { Component, OnInit } from '@angular/core';
import { ConcertService } from '../../services/newConcert.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Genre } from '../../models/genre.model';
import { Artist } from '../../models/artist.model';
import { Time } from '@angular/common';
import { convertDatetime } from '../../utils/datetime-utils';


@Component({
  selector: 'newConcert',
  templateUrl: './newConcert.component.html',
  styleUrls: ['./newConcert.component.css']
})
export class NewConcertComponent implements OnInit {
  date:Date|undefined;
  time:Time|undefined;
  place: string ="";
  numTicket: number | undefined;
  price: number | undefined;
  info: string | undefined;
  artist: number | undefined;
  genre: String = "";
  artists: any[] = []; // Aquí puedes definir el tipo de datos para los artistas
  genres: any[] = []; // Aquí puedes definir el tipo de datos para los géneros

  constructor(private http: HttpClient,private concertService: ConcertService) {}

  ngOnInit(): void {
    this.loadArtists();
    this.loadGenres();
  }

  loadArtists(): void {
    
    this.concertService.getArtists().subscribe((artists) => {
    this.artists = artists;
   });
  }

  loadGenres(): void {
  
    this.concertService.getGenres().subscribe((genres) => {
      this.genres = genres;
    });
  }

  createConcert(): void {
    const datetimeString = `${this.date}T${this.time}`;
    const datetime = new Date(datetimeString);
    console.log(this.artist);
    console.log(this.genre);
    const requestBody = {"place": this.place, "datetime": datetime,"num_tickets":this.numTicket,"price":this.price,"info":this.info,"genre":this.genre,"artistId":this.artist}
    this.concertService.createConcert(requestBody).subscribe({
      next: (v) => {
          console.log(v);
      },
      error: (e: HttpErrorResponse) => {
          
      }
  })
}
}
