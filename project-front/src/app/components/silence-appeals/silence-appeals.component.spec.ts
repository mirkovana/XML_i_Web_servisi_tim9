import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SilenceAppealsComponent } from './silence-appeals.component';

describe('SilenceAppealsComponent', () => {
  let component: SilenceAppealsComponent;
  let fixture: ComponentFixture<SilenceAppealsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SilenceAppealsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SilenceAppealsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
