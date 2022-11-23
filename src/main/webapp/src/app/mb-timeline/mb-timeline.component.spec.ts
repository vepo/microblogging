import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MbTimelineComponent } from './mb-timeline.component';

describe('MbTimelineComponent', () => {
  let component: MbTimelineComponent;
  let fixture: ComponentFixture<MbTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MbTimelineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MbTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
