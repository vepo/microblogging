import { Component, HostListener, OnInit } from '@angular/core';
import { PostService } from '../post.service';
import { Post } from '../posts/posts.model';

@Component({
  selector: 'app-mb-timeline',
  templateUrl: './mb-timeline.component.html',
  styleUrls: ['./mb-timeline.component.less']
})
export class MbTimelineComponent implements OnInit {
  posts: Post[] = [];
  hasMore: boolean = true;
  loadingItems: boolean = false;
  constructor(private postService: PostService) { }
  ngOnInit(): void {
    if (!this.loadingItems) {
      this.loadingItems = true;
      this.postService.getPosts()
        .subscribe(page => {
          this.posts.push(...page.items);
          this.hasMore = page.items.length > 0;
          this.loadingItems = false;
        });
    }
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: Event) {
    if (this.hasMore && !this.loadingItems && event.target instanceof Document) {
      let scrollTop = typeof event.target.scrollingElement?.scrollTop == 'number' ? event.target.scrollingElement.scrollTop : 0;
      let clientHeight = typeof event.target.scrollingElement?.clientHeight == 'number' ? event.target.scrollingElement.clientHeight : 0;
      let scrollHeight = typeof event.target.scrollingElement?.scrollHeight == 'number' ? event.target.scrollingElement.scrollHeight : 0;
      if (scrollTop + clientHeight > 0.85 * scrollHeight) {
        this.loadingItems = true;
        this.postService.getPosts(this.posts.length)
          .subscribe(page => {
            this.posts.push(...page.items);
            this.hasMore = page.items.length > 0;
            this.loadingItems = false;
          });
      }
    }
  }
}
