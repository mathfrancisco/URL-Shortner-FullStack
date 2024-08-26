import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UrlShorterService } from "../../shared/url-shortner.service";

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
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
    this.urlShorterService.getUrlShorterUrl(this.url).subscribe({
      next: (res: { shortUrl: string; originalUrl: string; } | null) => {
        if (res == null) {
          this.isErrorGenerated = true;
        } else {
          this.isUrlGenerated = true;
          this.shortUrl = res.shortUrl;
          this.originalUrl = res.originalUrl;
        }
      },
      error: (err) => {
        this.isUrlGenerated = false;
        this.isErrorGenerated = true;
      }
    });
  }
}
