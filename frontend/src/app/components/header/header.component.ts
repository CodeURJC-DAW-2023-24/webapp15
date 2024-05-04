import { LoginService } from './../../services/login.service';
import { Component } from "@angular/core";

@Component({
    selector: 'header',
    templateUrl: 'header.component.html',
    styleUrl: 'header.component.css'
})

export class HeaderComponent {

    constructor (public loginService: LoginService) {
    }

    logOut() {
        this.loginService.logout();
    }

}
