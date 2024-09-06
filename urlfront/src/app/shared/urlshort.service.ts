import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError,of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl: string = 'http://54.232.58.42/api/';

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
          console.log('Redirecionando para:', newUrl);
          return this.handleRedirect(newUrl || this.serviceUrl, url, headers);
        }
        return this.handleSuccessResponse(response);
      }),
      catchError(this.handleError)
    );
  }

  private handleRedirect(url: string, originalUrl: string, headers: HttpHeaders): Observable<any> {
    return this.http.post<any>(url, originalUrl, { headers }).pipe(
      switchMap(response => this.handleSuccessResponse(response))
    );
  }

  private handleSuccessResponse(response: any): Observable<any> {
    if (response.body && typeof response.body === 'object') {
      return of(response.body);
    } else if (typeof response === 'string') {
      return of({ shortened_url: response });
    } else {
      throw new Error('Resposta inválida do servidor');
    }
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
