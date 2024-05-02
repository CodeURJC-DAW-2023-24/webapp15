import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../models/user.model';

const BASE_URL = '/api'

@Injectable({
  providedIn: 'root'
})
export class SignUpService {

    private logged: boolean = false;
    private user: User | undefined;

    constructor(private http: HttpClient, private router: Router) {}

    registerUser(name: string, email: string, password: string) {
        this.http.post(BASE_URL + "/users/new", { "name": name, "email":email, "password": password }, { withCredentials: false })
            .subscribe({
                next: () => {
                    this.router.navigate(['/']);
                },
                error: (e: HttpErrorResponse) => {
                    alert("Error al registrarse.")
                }
            })
    }

}
