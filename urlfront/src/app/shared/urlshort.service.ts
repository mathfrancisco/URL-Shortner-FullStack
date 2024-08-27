import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Corrected import
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl : string = 'http://localhost:8080/url/shorten'; // Ensure this URL is correct and accessible

  constructor(private http : HttpClient) {}

  getUrlShorterUrl(url: string): Observable<any> {
  const headers = new HttpHeaders({ 'Content-Type': 'text/plain' });
  return this.http.post<any>(this.serviceUrl, url, { headers });
}
}
