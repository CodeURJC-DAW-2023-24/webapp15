import { HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SearchParams, SearchParamsFields } from '../utils/search-params';
import { Observable } from 'rxjs';

const BASE_URL = 'https://localhost:8443/api'

@Injectable({
  providedIn: 'root'
})
export class SearchService {

    constructor(private http: HttpClient) { }

    search(params: SearchParamsFields): Observable<any> {
        return this.http.get(BASE_URL + "/concerts", { params: this.getParams(params) })
    }

    private getParams(params: SearchParamsFields): HttpParams {
        return new SearchParams(params).toHttpParams();
    }
}
