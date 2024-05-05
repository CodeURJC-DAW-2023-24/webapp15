import { Component } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { SearchService } from "../../services/search.service";
import { LoginService } from "../../services/login.service";
import { PaymentService } from "../../services/payment.service";
import { Concert } from "../../models/concert.model";
import { User } from "../../models/user.model"
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { formatHour } from "../../utils/datetime-utils";

@Component({
    templateUrl: './payment.component.html',
    styleUrls: ['./payment.component.css', '../../../styles/concert-style.css'],
  })
export class PaymentComponent {

    concertId?: number;
    concert?: Concert;
    user?: User;
    error: boolean = false;
    numberOfTickets: number = 1;

    constructor(private searchService: SearchService, private loginService: LoginService, private paymentService: PaymentService, private http: HttpClient, private route: ActivatedRoute, private router: Router) {
        this.user = loginService.currentUser()
    }

    ngOnInit(): void {
        const concertIdStr = this.route.snapshot.paramMap.get('id') ?? undefined;
        concertIdStr ? this.concertId = parseInt(concertIdStr) : undefined;

        if (!this.concertId) {
            return;
        }

        this.getConcert(this.concertId);
    }

    private getConcert(id: number): void {
        this.searchService.search<Concert>("/concerts/" + id)
            .subscribe({
                next: (value: Concert) => {
                    this.concert = value;
                    this.concert.datetime = new Date(this.concert.datetime);
                    this.error = !this.isValidDate(this.concert);
                },
                error: (error: HttpErrorResponse) => {
                    this.error = true;
                }
            })
    }

    private isValidDate(concert: Concert): boolean {
        return new Date() < concert.datetime;
    }

    formatHour(date: Date): string {
        return formatHour(date);
    }

    purchaseTicket(concertId: number, numberOfTickets: number) {
        numberOfTickets = Math.min(numberOfTickets, 20);
        numberOfTickets = Math.max(numberOfTickets, 1);
        if (this.user !== undefined && this.concert !== undefined){
            this.paymentService.processPayment(concertId, numberOfTickets)
                .subscribe((response) => {
                    console.log(response);
                    this.router.navigate(['/']);
                })
        }
    }
}
