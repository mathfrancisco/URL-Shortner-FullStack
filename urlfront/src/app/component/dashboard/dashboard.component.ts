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

  constructor(private urlShorterService: UrlShorterService) {}

  ngOnInit(): void {
    this.isUrlGenerated = false;
  }

  generateShortUrl() {
    if (!this.url) {
      this.isErrorGenerated = true;
      return;
    }

    this.urlShorterService.getUrlShorterUrl(this.url).subscribe(
      (res: { shortUrl: string; originalUrl: string; } | null) => {
        if (res == null) {
          this.isErrorGenerated = true;
          this.isUrlGenerated = false;
        } else {
          this.isUrlGenerated = true;
          this.isErrorGenerated = false;
          this.shortUrl = res.shortUrl;
          this.originalUrl = res.originalUrl;
        }
      },
      err => {
        console.error('Error:', err);
        this.isUrlGenerated = false;
        this.isErrorGenerated = true;
      }
    );
  }

  getFullShortUrl(): string {
    return `https://link/${this.shortUrl}`;
  }

  copyToClipboard() {
    const fullUrl = this.getFullShortUrl();
    navigator.clipboard.writeText(fullUrl).then(() => {
      alert('URL copiada para a área de transferência!');
    }, (err) => {
      console.error('Erro ao copiar URL: ', err);
    });
  }
}
