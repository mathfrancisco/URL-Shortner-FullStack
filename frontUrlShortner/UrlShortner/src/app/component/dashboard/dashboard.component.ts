import {Component, OnInit} from '@angular/core';
import {UrlShorterService} from "../../shared/url-shorter.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  url: string="";
  originalUrl: string="";
  isUrlGenerated : boolean = false;
  isErrorGenerated: boolean = false;
  shortUrl: string="";

  constructor(private urlShorterService: UrlShorterService){}

  ngOnInit(): void{
  this.isUrlGenerated= false;
}
  generateShortUrl(){
    this.urlShorterService.getUrlShorterUrl(this.url).subscribe((res: { shortUrl: string; originalUrl: string; } | null)=>{
      if (res==null){
        this.isErrorGenerated=true;
      }else {
        this.isUrlGenerated=true;
        this.shortUrl=res.shortUrl;
        this.originalUrl=res.originalUrl;
      }
    },err=>{
      this.isUrlGenerated=false;
      this.isErrorGenerated=true;
    })
  }
}
