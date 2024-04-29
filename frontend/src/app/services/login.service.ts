import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

// TODO: Change this to relative route
const BASE_URL = 'https://localhost:8443/api'

@Injectable({
  providedIn: 'root'
})
export class LoginService {

    private logged: boolean = false;
    private user: User | undefined;

    constructor(private http: HttpClient, private router: Router) { }

    // TODO: Copiar del ejemplo de la fase 4 ???? no se como persistir la sesion.
    requestIsLogged() {
        this.http.get(BASE_URL + "/users/me", { withCredentials: true} ).subscribe({
            // next: (v) => this.user = v as User,
            next: (v) => {
                this.logged = true
                //this.user = v as User
                console.log(v)
            },
            error: (e: HttpErrorResponse) => {
                if (e.status == 401) {
                    this.logged = false
                }
                console.error(e)
            }
        })
    }

    login(username: string, password: string) {
        this.http.post(BASE_URL + "/login", { "username": username, "password": password }, { withCredentials: true })
            .subscribe({
                next: (v) => {
                    this.user = v as User;
                    this.logged = true;
                    console.log(v)
                    this.router.navigate(['/']);
                },
                error: (e: HttpErrorResponse) => {
                    console.log(e)
                }
            })
    }

    isLogged() { return this.logged }

    isRole(role: string): boolean {
        if (!this.user) { return false }

        return this.user.roles.includes(role);
    }
}