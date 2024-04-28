import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { SearchService } from "../../services/search.service";
import { Concert } from "../../models/concert.model";
import { HttpErrorResponse } from "@angular/common/http";
import { formatHour } from "../../utils/datetime-utils";

@Component({
    templateUrl: './payment.component.html',
    styleUrls: ['./payment.component.css', '../../../styles/concert-style.css'],
  })
export class PaymentComponent {

    concertId?: number;
    concert?: Concert;
    error: boolean = false;

    constructor(private searchService: SearchService, private route: ActivatedRoute) { }

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
                    console.log(value);
                    this.concert = value;
                    this.concert.datetime = new Date(this.concert.datetime);
                    this.error = !this.isValidDate(this.concert);
                    console.log(this.concert);
                },
                error: (error: HttpErrorResponse) => {
                    this.error = true;
                }
            })
    }

    private isValidDate(concert: Concert): boolean {
        console.log(new Date(), concert.datetime, new Date() < concert.datetime)
        return new Date() < concert.datetime;
    }

    formatHour(date: Date): string {
        return formatHour(date);
    }
}