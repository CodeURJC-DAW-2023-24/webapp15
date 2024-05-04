import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

    username: string = ''
    password: string = ''

    error: string = ''

    constructor(private loginService: LoginService) { }

    submitLoginForm() {
      if (this.username != '' && this.password != '') {
        this.loginService.login(this.username, this.password)
      } else {
        this.error = 'Campos vacíos.'
      }
    }
}
