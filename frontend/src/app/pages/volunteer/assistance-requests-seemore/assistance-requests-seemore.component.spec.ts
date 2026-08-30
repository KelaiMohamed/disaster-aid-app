import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssistanceRequestsSeemoreComponent } from './assistance-requests-seemore.component';

describe('AssistanceRequestsSeemoreComponent', () => {
  let component: AssistanceRequestsSeemoreComponent;
  let fixture: ComponentFixture<AssistanceRequestsSeemoreComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssistanceRequestsSeemoreComponent]
    });
    fixture = TestBed.createComponent(AssistanceRequestsSeemoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
