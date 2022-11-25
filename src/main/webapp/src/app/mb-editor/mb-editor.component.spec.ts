import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';

import { MbEditorComponent } from './mb-editor.component';

describe('MbEditorComponent', () => {
  let component: MbEditorComponent;
  let httpTestingController: HttpTestingController;
  let fixture: ComponentFixture<MbEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MbEditorComponent],
      imports: [HttpClientTestingModule, FormsModule]
    }).compileComponents();

    httpTestingController = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(MbEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  afterEach(() => {
    httpTestingController.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show an empty text area and a disabled button', () => {
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('textarea')).toBeDefined();
    expect(compiled.querySelector('button')?.disabled).toBeTruthy();
  });

  it('should enable the button when there is a value', () => {

    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    let txt = compiled.querySelector('textarea');
    if (txt != undefined) {
      txt.innerText = "My first post!";
    }
    txt?.dispatchEvent(new Event('Input'));
    fixture.detectChanges();
    expect(compiled.querySelector('button')?.disabled).toBeTruthy();
    compiled.querySelector('button')?.click();
    fixture.detectChanges();
    expect(compiled.querySelector('textarea')?.value).toBe('');
  });
});