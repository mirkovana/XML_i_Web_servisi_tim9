import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NaprednaPretragaSaComponent } from './napredna-pretraga-sa.component';

describe('NaprednaPretragaSaComponent', () => {
  let component: NaprednaPretragaSaComponent;
  let fixture: ComponentFixture<NaprednaPretragaSaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NaprednaPretragaSaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NaprednaPretragaSaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
