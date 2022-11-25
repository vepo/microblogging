import { HttpClientTestingModule } from "@angular/common/http/testing";
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";

import { ActivatedRoute, convertToParamMap, ParamMap } from '@angular/router';
import { BehaviorSubject, of } from 'rxjs';
import { PostService } from '../_services/post.service';
import { Post } from '../_model/posts.model';
import { MbViewerComponent } from './mb-viewer.component';
import { By } from "@angular/platform-browser";

describe('MbViewerComponent', () => {
  let component: MbViewerComponent;
  let fixture: ComponentFixture<MbViewerComponent>;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MbViewerComponent],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          paramMap: new BehaviorSubject<ParamMap>(
            convertToParamMap({ id: '1' })
          )
        }
      }]
    }).compileComponents();

    fixture = TestBed.createComponent(MbViewerComponent);
    let postService = fixture.debugElement.injector.get(PostService);
    let post: Post = {
      id: 1,
      content: 'POST',
      createdAt: new Date()
    };
    spyOn(postService, 'getPost').withArgs(1).and.returnValue(of(post));
    spyOn(postService, 'delete').withArgs(post)
      .and.returnValue(of(post));

    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show the post', async () => {
    fixture.detectChanges();
    await fixture.whenStable();
  });


  it('should show allow delete the post', async () => {
    fixture.detectChanges();
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    console.log(fixture.nativeElement)
    const btnDelete = fixture.nativeElement.querySelector('.btn-delete');
    console.log(btnDelete);
    btnDelete.click();
  });
});
