import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http'


import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl : string = '';
  constructor(private http : HttpClient) {
    this.serviceUrl = "http://localhost:8080/url/shorter";
  }

  getUrlShorterUrl(url : string) {
    return this.http.post<any>(this.serviceUrl,url);
  }


}
