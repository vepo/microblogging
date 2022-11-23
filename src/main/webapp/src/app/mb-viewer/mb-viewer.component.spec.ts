import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MbViewerComponent } from './mb-viewer.component';

describe('MbViewerComponent', () => {
  let component: MbViewerComponent;
  let fixture: ComponentFixture<MbViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MbViewerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MbViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
