import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddExplanationComponent } from './add-explanation.component';

describe('AddExplanationComponent', () => {
  let component: AddExplanationComponent;
  let fixture: ComponentFixture<AddExplanationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddExplanationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddExplanationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
