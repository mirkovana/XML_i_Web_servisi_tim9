import { TestBed } from '@angular/core/testing';

import { SilenceAppealService } from './silence-appeal.service';

describe('SilenceAppealService', () => {
  let service: SilenceAppealService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SilenceAppealService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
