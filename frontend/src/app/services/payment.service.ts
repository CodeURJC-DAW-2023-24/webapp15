import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Ticket } from '../models/ticket.model';
import { Observable } from 'rxjs/internal/Observable';

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

    getTickets(user: User){
        return this.http.get(BASE_URL).pipe() as Observable<Ticket[]>;
    }
}
