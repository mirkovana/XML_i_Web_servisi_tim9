import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DecisionAppealsComponent } from './decision-appeals.component';

describe('DecisionAppealsComponent', () => {
  let component: DecisionAppealsComponent;
  let fixture: ComponentFixture<DecisionAppealsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DecisionAppealsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DecisionAppealsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
