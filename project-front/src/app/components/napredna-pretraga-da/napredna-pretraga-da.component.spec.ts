import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NaprednaPretragaDaComponent } from './napredna-pretraga-da.component';

describe('NaprednaPretragaDaComponent', () => {
  let component: NaprednaPretragaDaComponent;
  let fixture: ComponentFixture<NaprednaPretragaDaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NaprednaPretragaDaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NaprednaPretragaDaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
