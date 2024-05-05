import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";
import { User } from '../../models/user.model';
import { Ticket } from '../../models/ticket.model';
import { PaymentService } from "../../services/payment.service";
import { Router } from '@angular/router';
import { convertTicketDatetime } from '../../utils/datetime-utils';

@Component({
    selector: 'app-root',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css', '../../../styles/concert-style.css']
})
export class ProfileComponent{

    name = ''
    email = ''
    tickets: Ticket[] = []

    editattname = false
    editattemail = false

    user?: User;

    constructor(public loginService: LoginService, private ticketService: PaymentService, private router: Router) { }

    ngOnInit() {
        this.loadCurrentUser();
        this.loadTickets();
    }

    
    loadCurrentUser(){
        this.user = this.loginService.getUser();
        if (this.user !== undefined){
            this.name = this.user.name
            this.email = this.user.email
        }
    }

    loadTickets(){
        if (this.user !== undefined) {
            this.ticketService.getTickets(0)
                .subscribe((response: Ticket[]) => {
                    this.tickets = this.tickets.concat(convertTicketDatetime(response));
                    console.log(this.tickets);
                })
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
        this.editattemail = !this.editattemail
        if (this.user !== undefined){
            this.user.email = this.email
            this.loginService.updateUser(this.user)
            this.loginService.logout();
            this.router.navigate(['/']);
        }
    }

    formatHour(date: Date): string {
        let str = date.getHours().toString() + ":";
        date.getMinutes() < 10
            ? str += "0" + date.getMinutes()
            : str += date.getMinutes();

        return str;
    }

}