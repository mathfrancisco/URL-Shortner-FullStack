import { TestBed } from '@angular/core/testing';

import { UrlShorterService } from './url-shorter.service'

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
