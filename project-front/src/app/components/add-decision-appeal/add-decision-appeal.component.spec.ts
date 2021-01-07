import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDecisionAppealComponent } from './add-decision-appeal.component';

describe('AddDecisionAppealComponent', () => {
  let component: AddDecisionAppealComponent;
  let fixture: ComponentFixture<AddDecisionAppealComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDecisionAppealComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDecisionAppealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
