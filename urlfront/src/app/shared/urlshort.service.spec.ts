import { TestBed } from '@angular/core/testing';

import {UrlShorterService} from "./urlshort.service";

describe('UrlShorterService', () => {
  let service: UrlShorterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlShorterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
