import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssistantDetailsComponent } from './assistant-details.component';

describe('AssistantDetailsComponent', () => {
  let component: AssistantDetailsComponent;
  let fixture: ComponentFixture<AssistantDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssistantDetailsComponent]
    });
    fixture = TestBed.createComponent(AssistantDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
