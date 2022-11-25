import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { switchMap } from 'rxjs';
import { PostService } from '../post.service';
import { Post } from '../posts/posts.model';

@Component({
  selector: 'app-mb-viewer',
  templateUrl: './mb-viewer.component.html',
  styleUrls: ['./mb-viewer.component.less']
})
export class MbViewerComponent implements OnInit {
  post: Post = { id: 0, content: "", createdAt: new Date() };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService) { }
  ngOnInit(): void {
    this.route.paramMap.pipe(switchMap((params: ParamMap) => this.postService.getPost(Number.parseInt(params.get('id')!))))
      .subscribe(post => this.post = post);
  }

  delete(): void {
    this.postService.delete(this.post)
      .subscribe({
        next: (post) => this.router.navigate(['/']),
        error: (error) => this.router.navigate(['/'])
      });
  }
}
