import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Artist } from '../models/artist.model';



@Injectable({
  providedIn: 'root'
})
export class newArtistService {
  
  constructor(private http: HttpClient) { }

  createArtist(requestBody:any):Observable<any> { 
    return this.http.post("/api/artists", requestBody);
  }
  setArtistImage(artist: Artist, data:FormData):Observable<any> {
    return this.http.put("/api/artists/" + artist.id + '/image', data);
  }
}
