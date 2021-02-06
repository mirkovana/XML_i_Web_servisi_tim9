import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSilenceAppealComponent } from './add-silence-appeal.component';

describe('AddSilenceAppealComponent', () => {
  let component: AddSilenceAppealComponent;
  let fixture: ComponentFixture<AddSilenceAppealComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddSilenceAppealComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSilenceAppealComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
