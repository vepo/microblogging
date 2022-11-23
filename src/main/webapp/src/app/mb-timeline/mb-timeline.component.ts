import { Component, OnInit } from '@angular/core';
import { PostService } from '../post.service';
import { Post } from '../posts/posts.model';

@Component({
  selector: 'app-mb-timeline',
  templateUrl: './mb-timeline.component.html',
  styleUrls: ['./mb-timeline.component.less']
})
export class MbTimelineComponent implements OnInit {
  posts: Post[] = [];
  constructor(private postService: PostService) { }
  ngOnInit(): void {
    this.postService.getPosts()
      .subscribe(page => this.posts.push(...page.items));
  }
}
