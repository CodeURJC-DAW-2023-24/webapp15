import { Component } from "@angular/core";
import { SearchService } from "../../services/search.service";
import { LoginService } from "../../services/login.service";
import { NgbNavModule, NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { Concert } from "../../models/concert.model";
import { SearchParamsFields } from "../../utils/search-params";
import { Observable, catchError, firstValueFrom, map } from 'rxjs';
import { SpringResponse } from "../../utils/spring-response";

@Component({
    templateUrl: 'search.component.html',
})

export class SearchComponent {

    query?: SpringResponse<Concert[]>;
    concerts: Concert[] = [];

    constructor(private searchService: SearchService, private loginService: LoginService) {
        this.searchConcerts();
    }

    private search<T>(params?: SearchParamsFields): Observable<SpringResponse<T>> {

        return this.searchService.search(params ?? {})
            .pipe(
                map((response: SpringResponse<T>) => {
                    return response;
                }),
                catchError((error: HttpErrorResponse) => {
                    throw error;
                })
            )
    }

    searchConcerts(params?: SearchParamsFields) {
        this.search<Concert[]>(params ?? {})
            .subscribe((response: SpringResponse<Concert[]>) => {
                this.query = response;
                this.concerts.push(...this.convertDatetime(response.content));
            })
    }

    private convertDatetime(concerts: Concert[]) {
        concerts.forEach((concert) => {
            concert.datetime = new Date(concert.datetime);
        })
        return concerts;
    }
}
