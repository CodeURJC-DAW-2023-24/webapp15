import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Concert } from '../models/concert.model';

const BASE_URL = "/api"

@Injectable({
    providedIn: 'root'
})
export class DeleteService {

    constructor(private http: HttpClient) { }

    deleteConcert(id: number): Observable<Concert> {
        return this.http.delete<Concert>(BASE_URL + "/concerts/" + id.toString());
    }
}
