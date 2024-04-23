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

    search<T>(relativeUrl: string, params: SearchParamsFields): Observable<T> {
        return this.http.get<T>(BASE_URL + relativeUrl, { params: this.getParams(params) })
    }

    private getParams(params: SearchParamsFields): HttpParams {
        return new SearchParams(params).toHttpParams();
    }
}
