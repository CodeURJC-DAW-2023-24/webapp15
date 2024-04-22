import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";

@Component({
    selector: 'app-root',
    templateUrl: './profile.component.html'
})
export class ProfileComponent{

    username: string = "FUNCIONA"
    password: string = 'AAAA'

    constructor(private loginService: LoginService) { }
}