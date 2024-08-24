import { TestBed } from '@angular/core/testing';

import { UrlShortnerService } from './url-shortner.service';

describe('UrlShortnerService', () => {
  let service: UrlShortnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlShortnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
