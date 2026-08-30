import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeAssistanceOfferComponent } from './make-assistance-offer.component';

describe('MakeAssistanceOfferComponent', () => {
  let component: MakeAssistanceOfferComponent;
  let fixture: ComponentFixture<MakeAssistanceOfferComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MakeAssistanceOfferComponent]
    });
    fixture = TestBed.createComponent(MakeAssistanceOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
