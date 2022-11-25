import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreatePostRequest, Post } from './posts/posts.model';
import { Observable } from 'rxjs';
import { Page } from './posts/page.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  mainPostUrl: string = "/api/post";
  streamPostUrl: string = "/api/post/stream";


  constructor(private http: HttpClient) { }

  create(request: CreatePostRequest): Observable<Post> {
    return this.http.post<Post>(this.mainPostUrl, request);
  }

  getPosts(offset: number = 0): Observable<Page<Post>> {
    return this.http.get<Page<Post>>(this.streamPostUrl, {
      params: {
        pageSize: 15,
        page: Math.ceil(offset / 15)
      }
    });
  }

  getPost(postId: number): Observable<Post> {
    return this.http.get<Post>(`/api/post/${postId}`);
  }


  delete(post: Post): Observable<Post> {
    return this.http.delete<Post>(`/api/post/${post.id}`);
  }
}
