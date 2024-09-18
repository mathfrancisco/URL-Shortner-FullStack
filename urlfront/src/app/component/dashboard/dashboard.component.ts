import { Component } from '@angular/core';
import { UrlShorterService } from "../../shared/urlshort.service";
import { Clipboard } from '@angular/cdk/clipboard';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  url: string = "";
  originalUrl: string = "";
  isUrlGenerated: boolean = false;
  isErrorGenerated: boolean = false;
  shortUrl: string = "";
  errorMessage: string = "";
  isNewUrl: boolean = false;

  constructor(
    private urlShorterService: UrlShorterService,
    private clipboard: Clipboard
  ) {}

  generateShortUrl() {
    if (!this.url) {
      this.setErrorState("Por favor, insira uma URL válida.");
      return;
    }

    this.resetState();

    this.urlShorterService.getUrlShorterUrl(this.url).subscribe({
      next: (response) => {
        console.log('Resposta recebida:', response);
        this.isUrlGenerated = true;
        this.shortUrl = response.shortUrl;
        this.originalUrl = response.originalUrl;

      },
      error: (err) => {
        console.error('Erro:', err);
        this.setErrorState(err.message || "Ocorreu um erro ao encurtar a URL. Por favor, tente novamente mais tarde.");
      }
    });
  }

  getFullShortUrl(): string {
    return `${window.location.origin}/api/${this.shortUrl}`;
  }

  copyToClipboard() {
    if (this.shortUrl) {
      this.clipboard.copy(this.getFullShortUrl());
      alert('URL copiada para a área de transferência!');
    } else {
      alert('Por favor, gere uma URL curta primeiro.');
    }
  }

  private resetState() {
    this.isUrlGenerated = false;
    this.isErrorGenerated = false;
    this.errorMessage = "";
    this.shortUrl = "";
    this.originalUrl = "";
  }

  private setErrorState(message: string) {
    this.isErrorGenerated = true;
    this.isUrlGenerated = false;
    this.errorMessage = message;
  }
}
