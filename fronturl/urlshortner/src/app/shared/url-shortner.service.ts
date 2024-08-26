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
  private apiUrl: string = 'http://localhost:8081/url/shorter'; // Updated URL

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<UrlResponse> {
  return this.http.post<UrlResponse>(`${this.apiUrl}/`, { url })
    .pipe(
      catchError(this.handleError('getUrlShorterUrl', []))
    );
}

private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error); // log to console instead
    return of(result as T);
  };
}

}
