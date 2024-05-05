import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";
import { User } from '../../models/user.model';
import { Ticket } from '../../models/ticket.model';
import { PaymentService } from "../../services/payment.service";
import { Router } from '@angular/router';
import { convertTicketDatetime } from '../../utils/datetime-utils';
import jsPDF from "jspdf";

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
    imageSrc:string|undefined;

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
    
    functionChangeImage(event: any) {
        const reader = new FileReader();
        reader.onload = (event: any) => {
          this.imageSrc = event.target.result;
        };
        reader.readAsDataURL(event.target.files[0]);
      
    }

    formatHour(date: Date): string {
        let str = date.getHours().toString() + ":";
        date.getMinutes() < 10
            ? str += "0" + date.getMinutes()
            : str += date.getMinutes();

        return str;
    }
    
    downloadTicket(ticketId: string, num_ticket: number, eventName: string, name: string, date: string, month: string, time: string, location: string) {
        const doc = new jsPDF();
        // PDF Content
        // Incluye la imagen del código QR
        const qrImg1 = ""; // Coloca la imagen del código QR aquí
        const pageWidth = doc.internal.pageSize.getWidth(); // Obtiene el ancho de la página
        const marginLeft = 10;
        const marginRight = 10; // Margen derecho deseado
        const imageWidth = pageWidth - marginLeft - marginRight; // Nuevo ancho de la imagen para respetar el margen derecho
        
        doc.addImage(qrImg1, 10, 10, 10, imageWidth); // Ajusta el ancho para incluir el margen derecho
        doc.setFontSize(16);
        doc.text(eventName, 10, 90);
        doc.setFontSize(12);
        doc.text(` ${name}`, 10, 100);
        doc.text(`Fecha: ${date} ${month}`, 10, 110)
        doc.text(`Hora: ${time}`, 10, 120);
        doc.text(`Lugar: ${location}`, 10, 130);
        doc.text(`${num_ticket}`, 10, 140);
    
        // Trigger PDF download
        doc.save(`ticket-${ticketId}.pdf`);
    }

}