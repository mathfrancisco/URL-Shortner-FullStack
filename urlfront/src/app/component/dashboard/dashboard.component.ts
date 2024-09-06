import { Component, OnInit } from '@angular/core';
import { UrlShorterService } from "../../shared/urlshort.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  url: string = "";
  originalUrl: string = "";
  isUrlGenerated: boolean = false;
  isErrorGenerated: boolean = false;
  shortUrl: string = "";
  errorMessage: string = "";

  constructor(private urlShorterService: UrlShorterService) {}

  ngOnInit(): void {
    this.resetState();
  }

  generateShortUrl() {
    if (!this.url) {
      this.setErrorState("Por favor, insira uma URL válida.");
      return;
    }

    this.resetState();

    this.urlShorterService.getUrlShorterUrl(this.url).subscribe({
      next: (res: { shortUrl: string; originalUrl: string; } | null) => {
        if (res == null) {
          this.setErrorState("Não foi possível gerar a URL curta. Por favor, tente novamente.");
        } else {
          this.isUrlGenerated = true;
          this.shortUrl = res.shortUrl;
          this.originalUrl = res.originalUrl;
        }
      },
      error: (err) => {
        console.error('Error:', err);
        this.setErrorState("Ocorreu um erro ao encurtar a URL. Por favor, tente novamente mais tarde.");
      }
    });
  }

  getFullShortUrl(): string {
    return `https://link/${this.shortUrl}`;
  }

  copyToClipboard() {
    const fullUrl = this.getFullShortUrl();
    navigator.clipboard.writeText(fullUrl).then(
      () => {
        this.showAlert('URL copiada para a área de transferência!');
      },
      (err) => {
        console.error('Erro ao copiar URL: ', err);
        this.showAlert('Não foi possível copiar a URL. Por favor, tente copiar manualmente.');
      }
    );
  }

  private resetState() {
    this.isUrlGenerated = false;
    this.isErrorGenerated = false;
    this.errorMessage = "";
  }

  private setErrorState(message: string) {
    this.isErrorGenerated = true;
    this.isUrlGenerated = false;
    this.errorMessage = message;
  }

  private showAlert(message: string) {
    // You might want to replace this with a more user-friendly notification system
    alert(message);
  }
}
