import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { PostService } from './post.service';

describe('PostService', () => {
  let postService: PostService;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ imports: [HttpClientTestingModule] }).compileComponents();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
    postService = TestBed.inject(PostService);
  });

  afterEach(() => {
    httpTestingController.verify();
  })

  it('should be created', () => {
    expect(postService).toBeTruthy();
  });

  it('should allow create posts', () => {
    postService.create({ content: "Text" })
      .subscribe({
        next: res => expect(res).toBeTruthy(),
        error: fail
      });
    const req = httpTestingController.expectOne(postService.mainPostUrl);
    expect(req.request.method).toEqual('POST');
  });

  it('should allow read a post', () => {
    postService.getPost(444)
      .subscribe({
        next: res => expect(res).toBeTruthy(),
        error: fail
      });
    const req = httpTestingController.expectOne(postService.mainPostUrl + "/444");
    expect(req.request.method).toEqual('GET');
  });

  it('should allow reading global timeline', () => {
    postService.getPosts()
      .subscribe({
        next: res => expect(res).toBeTruthy(),
        error: fail
      });
    const req = httpTestingController.expectOne(postService.streamPostUrl + '?pageSize=15&page=0');
    expect(req.request.method).toEqual('GET');
  });

  it('should allow delete a post', () => {
    postService.delete({ id: 444, content: "bla bla bla", createdAt: new Date() }).subscribe({
      next: res => expect(res).toBeTruthy(),
      error: fail
    });

    const req = httpTestingController.expectOne(postService.mainPostUrl + "/444");
    expect(req.request.method).toEqual('DELETE');
  });
});
