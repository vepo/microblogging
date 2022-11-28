import { Component } from '@angular/core';
import { PostService } from '../_services/post.service';


@Component({
  selector: 'app-mb-editor',
  templateUrl: './mb-editor.component.html',
  styleUrls: ['./mb-editor.component.less']
})
export class MbEditorComponent {
  content: string = "";

  constructor(private postService: PostService) { }

  create() {
    this.postService.create({ content: this.content })
      .subscribe(post => this.content = "");
  }
}
