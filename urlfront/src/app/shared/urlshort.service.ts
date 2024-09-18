import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UrlShorterService {
  serviceUrl: string = 'http://54.232.58.42/api/'; // Certifique-se de que esta URL está correta

  constructor(private http: HttpClient) {}

  getUrlShorterUrl(url: string): Observable<{shortUrl: string, originalUrl: string}> {
    const headers = new HttpHeaders({ 'Content-Type': 'text/plain' });

    return this.http.post<any>(this.serviceUrl, url, {
      headers,
      withCredentials: true,
      observe: 'response'
    }).pipe(
      map(response => {
        console.log('Resposta completa do servidor:', response);
        if (response.body && response.body.shortUrl && response.body.originalUrl) {
          return {
            shortUrl: response.body.shortUrl,
            originalUrl: response.body.originalUrl
          };
        } else {
          throw new Error('Resposta inválida do servidor');
        }
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
      if (error.status === 0) {
        errorMessage = 'Não foi possível conectar ao servidor. Verifique sua conexão de internet.';
      } else if (error.status === 400) {
        errorMessage = 'URL inválida ou já existente.';
      } else if (error.status === 404) {
        errorMessage = 'URL curta não encontrada.';
      }
    }

    return throwError(() => new Error(errorMessage));
  }
}
