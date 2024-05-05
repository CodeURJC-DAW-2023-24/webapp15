import { Component } from '@angular/core';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  
})
export class ProfileComponent {

    constructor(private loginService: LoginService) {
        //loginService.requestIsLogged();
    }

    test() {
        this.loginService.requestIsLogged();
    }
}
