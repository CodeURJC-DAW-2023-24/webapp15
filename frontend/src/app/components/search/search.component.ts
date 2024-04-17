import { Component } from "@angular/core";
import { SearchService } from "../../services/search.service";
import { LoginService } from "../../services/login.service";
import { NgbNavModule, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { Concert } from "../../models/concert.model";
import { SearchParamsFields } from "../../utils/search-params";

@Component({
    templateUrl: 'search.component.html',
})

export class SearchComponent {

    concerts: Concert[] = this.search();

    constructor(private searchService: SearchService, private loginService: LoginService) { }

    search(params?: SearchParamsFields): Concert[] {
        let concerts: Concert[] = [];
        //this.loginService.requestIsLogged();
        this.searchService.search(params ?? {})
            .subscribe({
                next: (response: HttpResponse<any>) => {
                    console.log(response)
                    const content: Concert[] = response.body.content;
                    for (let concert of content) {
                        concert.datetime = new Date(concert.datetime);
                        concerts.push(concert);
                    }
                    console.log(this.concerts)
                },
                error: (e: HttpErrorResponse) => {
                    console.error(e)
                }
            })

        return concerts;
    }
}
