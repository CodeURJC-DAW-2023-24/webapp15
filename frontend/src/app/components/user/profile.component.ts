import { Component } from '@angular/core'
import { LoginService } from "../../services/login.service";
import { User } from '../../models/user.model';
import { Ticket } from '../../models/ticket.model';
import { PaymentService } from "../../services/payment.service";
import { Router } from '@angular/router';
import { convertTicketDatetime } from '../../utils/datetime-utils';
import jsPDF from "jspdf";
import { HttpClient } from '@angular/common/http';

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
    imageSrc?: string;

    hasImage = false;
    imageRoute: string;
    user?: User;

    constructor(public loginService: LoginService, private ticketService: PaymentService, private router: Router, private http: HttpClient) {
        this.imageRoute = "/api/users/" + this.loginService.getUser()!.id + "/image?" + Date.now();
    }

    ngOnInit() {
        this.loadCurrentUser();
        this.loadImage();
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

    loadImage(): void {
        this.http.head(("/api/users/" + this.loginService.getUser()!.id + "/image"))
            .subscribe(() => {
                this.hasImage = true;
            })
    }

    getImageRoute(): string {
        return this.hasImage
            // ? "/api/users/" + this.loginService.getUser()!.id + "/image"
            ? this.imageRoute
            : "assets/images/default-profile-picture.jpg";
    }

    functionChangeImage(event: any) {
        const reader = new FileReader();
        reader.onload = (event: any) => {
          this.imageSrc = event.target.result;
        };

        const image = event.target.files[0];

        reader.readAsDataURL(image);

        this.putUserImage(image);

    }

    private putUserImage(image: any): void {
        const formData = new FormData();
        formData.append('imageFile', image);
        this.http.put(("/api/users/" + this.user!.id + "/image"), formData)
            .subscribe((response) => {
                this.hasImage = true;
                this.imageRoute = "/api/users/" + this.loginService.getUser()!.id + "/image?" + Date.now();
            })
    }

    formatHour(date: Date): string {
        let str = date.getHours().toString() + ":";
        date.getMinutes() < 10
            ? str += "0" + date.getMinutes()
            : str += date.getMinutes();

        return str;
    }

    downloadTicket(ticket: Ticket) {

        const doc = new jsPDF();
        // PDF Content
        // Incluye la imagen del código QR
        const qrImg1 = ""; // Coloca la imagen del código QR aquí
        const pageWidth = doc.internal.pageSize.getWidth(); // Obtiene el ancho de la página
        const marginLeft = 10;
        const marginRight = 10; // Margen derecho deseado
        const imageWidth = pageWidth - marginLeft - marginRight; // Nuevo ancho de la imagen para respetar el margen derecho

        // doc.addImage(qrImg1, 10, 10, 10, imageWidth); // Ajusta el ancho para incluir el margen derecho
        doc.setFontSize(16);
        doc.text(ticket.concert.place, 10, 90);
        doc.setFontSize(12);
        doc.text(`${ticket.concert.artist.name}`, 10, 100);
        doc.text(`Fecha: ${ticket.concert.datetime.getDate()} ${ticket.concert.datetime.toLocaleString("es-ES", { month: "long"})}`, 10, 110)
        doc.text(`Hora: ${this.formatHour(ticket.concert.datetime)}`, 10, 120);
        doc.text(`Lugar: ${ticket.concert.place}`, 10, 130);
        doc.text(`Número de tickets: ${ticket.num_ticket}`, 10, 140);

        const qrImg2 = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABIMAAASDAgMAAACXZRdkAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJUExURf///z8/PwAAAABE24UAAAfdSURBVHja7d2xceMwEAVQjgMFKoVVXBNKVMJVwSacK3AgscqL7ASzxAIkJfr0fioI2H1Id8BhkMV8ICBEiBAhQoQIERJChAgRIkSIECEhRIgQIUKECBESQoQIESJEiBAhIUSIECFChAgREkKECBEiRIgQISFEiBAhQoQIERJChAgRIkSIECEhRIgQoeMLnecVuffsMn3/adtdujIRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFC/4PQ7Xvt2LHLPZ48fJ7Q45LO9ZBCn/kG+oS+8gOjp0MK/ck38JcQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKE+oTqM22nROHxQT3tl4hTKJQ4iBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIEfpFQmVV8UFlVfVRx3IaciBEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoTeRGho6C1eMjcgEiJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQr9RqJ6MUENvLc4lYiGUCCFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAi1CT0u6VwPKfSZb2DuEurJoYR68jyh+FO9T1tCiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIHU5om8Qf/B3qQ4r3ntua9mmEECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgR2lAoMRiY+Jpv4inBVQOTLQeVV/HT/rpJT0KECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRGh7oXi/YdsJxK5atjmIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgReopQQ/v3hl2GNaOOC0sSQ4rFL7ewJkKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRGhXoaKqOJnn+4qq4m8CtyAeYdKTECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRWhbqSjwY2NJ+AjFekkhXLYQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiNCeQg37/WSvdwLjJXFu9Qud180xEiJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQo2vDcaFJ4YU411ariLxlGBcy0L7hAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSI0GuEVs0xfvRUlbiKoS7UNaQY70KIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAjtINT1CGBiArGlqnr75W2N4cDkuecqCBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQoR2EtknXO4ENhQ89VxE/SEiIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAg9RSg+ciE944VxuoTmhvbjcgkRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIEKE9hYaeqoreWjJ1tL/uKggRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIEKEXC4319qf6kWMDVaKWbUOIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAi9SGhVWt4sHMPCz/WrSBx0bjCbCBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQoR2FWvab670lZgd73gmcG2YqE48jxh8fJkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChI4rVA4G1qtKCK2bYyxqmeNyF/Z7XNK5vqfQV77uEyFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAgRIkSIECFChAi9qVB9dvAUDym29JYYA0y03zPqGIcQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBE6nFBijnHeSWjVkokQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBH6PUJFVQuvDTY4lxn3OYgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBHaVaiehdcGx7k966YhY6EWREKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBEiRIgQIUKECBFaEnpc0rm+p9C8pjdC7b0Va291xHgCca4LDfFtESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQoQIESJEiBAhQq8REkKECBEiRIgQISFEiBAhQoQIERJChAgRIkSIECEhRIgQIUKECBESQoQIESJEiBAhIUSIECFChAgREkKECBEiRIgQIUIICBEiRIgQIUKEhBAhQoSOLfQPo9dNYRDWvZ8AAAAASUVORK5CYII='; // Your base64-encoded image string here
        doc.addImage(qrImg2, 'PNG', 10, 150, 50, 50);

        // Trigger PDF download
        doc.save(`ticket-${ticket.id}.pdf`);
    }

}
