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

  getPosts(): Observable<Pagging<Post>> {
    return this.http.get<Pagging<Post>>("/api/post/stream");
  }

  getPost(postId: number): Observable<Post> {
    return this.http.get<Post>(`/api/post/${postId}`);
  }
}
