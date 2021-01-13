import { TestBed } from '@angular/core/testing';

import { DecisionAppealService } from './decision-appeal.service';

describe('DecisionAppealService', () => {
  let service: DecisionAppealService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DecisionAppealService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
