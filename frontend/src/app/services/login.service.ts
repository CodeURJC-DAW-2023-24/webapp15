import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    logged: boolean = false;
    user: User | null = null;

    constructor(private http: HttpClient) {
        this.requestIsLogged();
    }

    requestIsLogged() {
        this.http.get("/api/users/me").subscribe({
            next: (v) => this.user = v as User,
            error: (e) => console.error(e)
        })
    }
}
