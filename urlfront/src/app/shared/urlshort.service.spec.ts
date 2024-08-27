import { TestBed } from '@angular/core/testing';

import { UrlshortService } from './urlshort.service';

describe('UrlshortService', () => {
  let service: UrlshortService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UrlshortService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
