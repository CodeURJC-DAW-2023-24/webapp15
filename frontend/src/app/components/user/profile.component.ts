import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";

@Component({
    selector: 'app-root',
    templateUrl: './profile.component.html'
})
export class ProfileComponent{

    name = 'aaa'
    email = 'aaa@aaa.com'

    editattname = false
    editattemail = false

    constructor(private loginService: LoginService) { }

    editName(){
        this.editattname = !this.editattname
    }

    editEmail(){
        this.editattemail = !this.editattemail
    }
}