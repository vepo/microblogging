import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { PostService } from '../_services/post.service';
import { Page } from '../_model/page.model';
import { Post } from '../_model/posts.model';

import { MbTimelineComponent } from './mb-timeline.component';

describe('MbTimelineComponent', () => {
  let component: MbTimelineComponent;
  let fixture: ComponentFixture<MbTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MbTimelineComponent],
      imports: [RouterTestingModule, HttpClientTestingModule],
    })
      .compileComponents();

    fixture = TestBed.createComponent(MbTimelineComponent);
    let postService = fixture.debugElement.injector.get(PostService);
    let firstPage: Page<Post> = {
      items: Array(20).fill(1)
        .map((x, y) => x + y)
        .map(index => ({
          id: index,
          content: `POST-${index}`,
          createdAt: new Date()
        })),
      offset: 0
    };
    let secondPage: Page<Post> = {
      items: Array(20).fill(1)
        .map((x, y) => x + y + 20)
        .map(index => ({
          id: index,
          content: `POST-${index}`,
          createdAt: new Date()
        })),
      offset: 20
    };
    spyOn(postService, 'getPosts').and.returnValue(of(firstPage))
      .and.returnValue(of(secondPage));
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should show the timeline', async () => {
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelectorAll('.post').length).toBe(20);
    window.scrollTo(0, document.body.scrollHeight);
    document.dispatchEvent(new Event('scroll'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compiled.querySelectorAll('.post').length).toBe(40);
  });

});
