import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl: string = 'http://54.232.58.42/api/';

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<{shortUrl: string, originalUrl: string}> {
    const headers = new HttpHeaders({ 'Content-Type': 'text/plain' });

    return this.http.post<{shortUrl: string, originalUrl: string}>(this.serviceUrl, url, {
      headers,
      withCredentials: true
    }).pipe(
      map(response => {
        console.log('Resposta do servidor:', response);
        return response;
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    let errorMessage = 'Ocorreu um erro ao encurtar a URL. Por favor, tente novamente mais tarde.';

    if (error.error instanceof ErrorEvent) {
      console.error('Erro do cliente:', error.error.message);
    } else {
      console.error(`Erro do servidor: ${error.status}, corpo:`, error.error);
      if (error.status === 405) {
        errorMessage = 'O método de requisição não é permitido para este recurso.';
      }
    }

    return throwError(() => new Error(errorMessage));
  }
}
