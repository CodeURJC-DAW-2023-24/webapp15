import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";
import { User } from '../../models/user.model';
import { Ticket } from '../../models/ticket.model';
import { PaymentService } from "../../services/payment.service";

@Component({
    selector: 'app-root',
    templateUrl: './profile.component.html',
    styleUrl: './profile.component.css'
})
export class ProfileComponent{

    name = 'aaa'
    email = 'aaa@aaa.com'
    tickets: Ticket[] = []

    editattname = false
    editattemail = false

    private user: User | undefined;

    constructor(private loginService: LoginService, private ticketService: PaymentService) { 
        this.loadCurrentUser()
        this.loadTickets()
    }

    
    loadCurrentUser(){
        this.user = this.loginService.currentUser()
        if (this.user !== undefined){
            this.name = this.user.name
            this.email = this.user.email
        }
    }

    loadTickets(){
        if (this.user !== undefined){
            this.ticketService.getTickets(this.user).subscribe(
                tickets => this.tickets = tickets,
                error => console.log(error)
            );
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