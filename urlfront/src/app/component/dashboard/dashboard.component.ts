import { Component, OnInit } from '@angular/core';
import { UrlShorterService } from "../../shared/urlshort.service";
import { Clipboard } from '@angular/cdk/clipboard';
import {HttpResponse} from "@angular/common/http";

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

  constructor(
    private urlShorterService: UrlShorterService,
    private clipboard: Clipboard  // Inject Clipboard service
  ) {}

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
      next: (response: HttpResponse<any>) => { // Update the type here
        if (response.body) {
          this.isUrlGenerated = true;
          this.shortUrl = response.body.shortUrl; // Accessing the body
          this.originalUrl = response.body.originalUrl; // Accessing the body
        } else {
          this.setErrorState("Não foi possível gerar a URL curta. Por favor, tente novamente.");
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
    if (this.shortUrl) {
      this.clipboard.copy(this.getFullShortUrl()); // Use the copy method
      alert('URL copiada para a área de transferência!'); // Optional: Notify user
    } else {
      alert('Por favor, gere uma URL curta primeiro.');
    }
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
}
