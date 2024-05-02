import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";
import { User } from '../../models/user.model';

@Component({
    selector: 'app-root',
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.css'
})
export class ProfileComponent{

    name = 'aaa'
    email = 'aaa@aaa.com'

    editattname = false
    editattemail = false

    private user: User | undefined;

    constructor(private loginService: LoginService) { 
        this.loadCurrentUser()
    }

    loadCurrentUser(){
        this.user = this.loginService.currentUser()
        if (this.user !== undefined){
            this.name = this.user.name
            this.email = this.user.email
        }
    }

    editName(){
        this.editattname = !this.editattname
    }

    editEmail(){
        this.editattemail = !this.editattemail
    }

    updateName(){
        this.editattname = !this.editattname
        if (this.user !== undefined){
            this.user.name = this.name
            this.loginService.updateUser(this.user)
        }
    }

    updateEmail(){
        this.editattname = !this.editattname
        if (this.user !== undefined){
            this.user.name = this.name
            this.loginService.updateUser(this.user)
        }
    }

}