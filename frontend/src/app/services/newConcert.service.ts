import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConcertService {
  private baseUrl = '/api/concerts'; // Cambia esto según tu configuración de API

  constructor(private http: HttpClient) {}

  getArtists(): Observable<any[]> {
    return this.http.get<any[]>(`/api/concerts/artists`);
  }

  getGenres(): Observable<any[]> {
    return this.http.get<any[]>(`/api/concerts/genres`);
  }

  createConcert(requestBody: any) {
    return this.http.post("/api/concerts", requestBody);

  }
}
