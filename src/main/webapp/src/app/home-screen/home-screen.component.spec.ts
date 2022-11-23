import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MbEditorComponent } from '../mb-editor/mb-editor.component';
import { MbTimelineComponent } from '../mb-timeline/mb-timeline.component';

import { HomeScreenComponent } from './home-screen.component';

describe('HomeScreenComponent', () => {
  let component: HomeScreenComponent;
  let fixture: ComponentFixture<HomeScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeScreenComponent, MbEditorComponent, MbTimelineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
