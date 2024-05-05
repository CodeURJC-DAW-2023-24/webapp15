import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Ticket } from '../models/ticket.model';
import { Observable } from 'rxjs/internal/Observable';
import { SpringResponse } from '../utils/spring-response';
import { catchError, map } from 'rxjs';

// TODO: Change this to relative route
const BASE_URL = '/api/tickets'

@Injectable({
  providedIn: 'root'
})

export class PaymentService{

    constructor(private http: HttpClient) {
    }

    processPayment(concertId: number, num_tickets: number){
        const requestBody = {"concertId": concertId, "numberOfTickets": num_tickets};
        return this.http.post(BASE_URL, requestBody);

    }

    getTickets(page: number): Observable<Ticket[]> {
        return this.http.get<SpringResponse<Ticket[]>>(BASE_URL, {params: {page: page} })
            .pipe(
                map((response: SpringResponse<Ticket[]>) => {
                    return response.content;
                }),
                catchError((error: HttpErrorResponse) => {
                    throw error;
                })
            )
    }
}
