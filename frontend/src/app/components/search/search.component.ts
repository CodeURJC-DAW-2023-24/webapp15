import { Component } from "@angular/core";
import { LoginService } from "../../services/login.service";

@Component({
    templateUrl: 'search.component.html'
})

export class SearchComponent {

    constructor(private loginService: LoginService) { }

    test(): void {
        this.loginService.requestIsLogged();
    }
}
