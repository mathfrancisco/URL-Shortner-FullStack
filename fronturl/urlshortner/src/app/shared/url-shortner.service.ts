import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface UrlResponse {
  shortUrl: string;
  originalUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  private serviceUrl: string = 'http://localhost:8080/url/shorter';

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<UrlResponse> {
    return this.http.post<UrlResponse>(this.serviceUrl, { url });
  }
}
