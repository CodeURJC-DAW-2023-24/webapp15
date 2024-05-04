import { Component } from '@angular/core';
import { SignUpService } from '../../services/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignUpComponent {

    email: string = '';
    name: string = '';
    password: string = '';

    error: string = '';

    constructor(private signupService: SignUpService) { }

    submitRegisterForm() {
      if (this.email !== '' || this.name != '' || this.password != '') {
        this.signupService.registerUser(this.name, this.email, this.password);
      } else {
        this.error = 'Datos inválidos.';
      }
    }
}
