import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

// TODO: Change this to relative route
const BASE_URL = 'https://localhost:8443/api'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    logged: boolean = false;
    user: User | undefined;

    constructor(private http: HttpClient) {
        this.requestIsLogged();
    }

    requestIsLogged() {
        console.log("bbbbbb")
        this.http.get(BASE_URL + "/users/me").subscribe({
            // next: (v) => this.user = v as User,
            next: (v) => {
                this.logged = true
                //this.user = v as User
                console.log(v)
            },
            error: (e: HttpErrorResponse) => {
                if (e.status == 401) {
                console.log("aaaaaaaaaaaa")
                this.logged = false
            }
            console.error(e)
            }
        })
    }

    login(username: string, password: string) {
        console.log(username)
        this.http.post(BASE_URL + "/login", { "username": username, "password": password }, { withCredentials: true })
            .subscribe({
                next: (v) => {
                    this.user = v as User
                    console.log(v)
                },
                error: (e: HttpErrorResponse) => {
                    console.log(e)
                }
            })
    }
}
