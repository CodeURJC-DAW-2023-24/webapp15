import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Ticket} from '../models/ticket.model';
import { Concert } from '../models/concert.model'
import { Observable } from 'rxjs/internal/Observable';

// TODO: Change this to relative route
const BASE_URL = '/api/tickets'

@Injectable({
  providedIn: 'root'
})

export class PaymentService{

    constructor(private http: HttpClient, private router: Router) {
    }

    processPayment(user: User, concert: Concert, num_tickets: number){
        this.http.post(BASE_URL, {"user": user, "concert":concert, "num_tickets": num_tickets},  { withCredentials: true })
            .subscribe({
                next: (v) => {
                    console.log(v)
                    this.router.navigate(['/']);
                },
                error: (e: HttpErrorResponse) => {
                    console.log(e)
                }
        })

    }

    getTickets(user: User){
        return this.http.get(BASE_URL).pipe() as Observable<Ticket[]>;
    }
}
