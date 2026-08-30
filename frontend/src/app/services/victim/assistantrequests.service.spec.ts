import { TestBed } from '@angular/core/testing';

import { AssistantrequestsService } from './assistantrequests.service';

describe('AssistantrequestsService', () => {
  let service: AssistantrequestsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssistantrequestsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
