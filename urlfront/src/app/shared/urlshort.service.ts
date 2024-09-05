import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl: string = '/api';

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'text/plain' });
    return this.http.post<any>(this.serviceUrl, url, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('Ocorreu um erro:', error.error.message);
    } else {
      console.error(
        `Backend retornou código ${error.status}, ` +
        `corpo era: ${error.error}`);
    }
    return throwError('Algo deu errado; por favor, tente novamente mais tarde.');
  }
}
