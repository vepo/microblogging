import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MbEditorComponent } from './mb-editor.component';

describe('MbEditorComponent', () => {
  let component: MbEditorComponent;
  let fixture: ComponentFixture<MbEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MbEditorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MbEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
