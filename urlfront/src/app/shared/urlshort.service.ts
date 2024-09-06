import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl: string = 'http://54.232.58.42/api/';  // Updated with trailing slash

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'text/plain' });
    return this.http.post<any>(this.serviceUrl, url, {
      headers,
      observe: 'response',
      withCredentials: true
    }).pipe(
      switchMap(response => {
        if (response.status === 301 || response.status === 302) {
          const newUrl = response.headers.get('Location');
          return this.http.post<any>(newUrl || this.serviceUrl, url, { headers });
        }
        return response.body ? response.body : response;
      }),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Algo deu errado; por favor, tente novamente mais tarde.';

    if (error.error instanceof ErrorEvent) {
      console.error('Ocorreu um erro:', error.error.message);
    } else {
      console.error(`Backend retornou código ${error.status}, corpo era:`, error.error);
      if (error.status === 405) {
        errorMessage = 'O método de requisição não é permitido para este recurso.';
      }
    }

    return throwError(() => new Error(errorMessage));
  }
}
