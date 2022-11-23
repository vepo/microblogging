import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";

import { MbViewerComponent } from './mb-viewer.component';

describe('MbViewerComponent', () => {
  let component: MbViewerComponent;
  let fixture: ComponentFixture<MbViewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MbViewerComponent ],
      imports: [RouterTestingModule, HttpClientTestingModule],
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
