import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VictimComponent } from './victim.component';

describe('VictimComponent', () => {
  let component: VictimComponent;
  let fixture: ComponentFixture<VictimComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VictimComponent]
    });
    fixture = TestBed.createComponent(VictimComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
