import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SearchParams, SearchParamsFields } from '../utils/search-params';

const BASE_URL = 'https://localhost:8443/api'

@Injectable({
  providedIn: 'root'
})
export class SearchService {

    constructor(private http: HttpClient) { }

    search(params: SearchParamsFields): void {
        this.http.get(BASE_URL + "/concerts", { params: this.getParams(params) })
            .subscribe({
                next: (v) => {
                    console.log(v)
                },
                error: (e: HttpErrorResponse) => {
                    console.error(e)
                }
            })
    }

    private getParams(params: SearchParamsFields): HttpParams {
        return new SearchParams(params).toHttpParams();
    }
}
