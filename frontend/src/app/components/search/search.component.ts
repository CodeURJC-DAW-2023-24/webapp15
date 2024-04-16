import { Component } from "@angular/core";
import { SearchService } from "../../services/search.service";
import { LoginService } from "../../services/login.service";

@Component({
    templateUrl: 'search.component.html'
})

export class SearchComponent {

    constructor(private searchService: SearchService, private loginService: LoginService) { }

    test(): void {
        //this.loginService.requestIsLogged();
        this.searchService.search({

        })
    }
}
