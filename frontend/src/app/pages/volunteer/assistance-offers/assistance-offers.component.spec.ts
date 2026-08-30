import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssistanceOffersComponent } from './assistance-offers.component';

describe('AssistanceOffersComponent', () => {
  let component: AssistanceOffersComponent;
  let fixture: ComponentFixture<AssistanceOffersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssistanceOffersComponent]
    });
    fixture = TestBed.createComponent(AssistanceOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
