import { Component } from '@angular/core';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

    username: string = ''
    password: string = ''

    constructor(private loginService: LoginService) { }

    submitLoginForm() {
        this.loginService.login(this.username, this.password)
    }
}
