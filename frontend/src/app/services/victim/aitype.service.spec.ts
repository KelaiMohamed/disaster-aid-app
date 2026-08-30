import { TestBed } from '@angular/core/testing';

import { AitypeService } from './aitype.service';

describe('AitypeService', () => {
  let service: AitypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AitypeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
