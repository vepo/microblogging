import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CreatePostRequest, Post } from './posts/posts.model';
import { Observable } from 'rxjs';
import { Pagging } from './posts/pagging.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  create(request: CreatePostRequest): Observable<Post> {
    return this.http.post<Post>("/api/post", request);
  }

  getPosts(offset: number = 0): Observable<Pagging<Post>> {
    console.log("Loading page", `page: ${Math.ceil(offset / 15)}`, `offset: ${offset}`);
    return this.http.get<Pagging<Post>>("/api/post/stream", {
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
