import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignUpComponent {

    username: string = ''
    email: string = ''
    name: string = ''
    password: string = ''

    constructor(private loginService: LoginService) { }

    submitRegisterForm() {
        this.loginService.login(this.username, this.password)
    }
}
