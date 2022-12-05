import { Component, Input } from '@angular/core';
import { Profile } from '../_model/user.model';
import { faImage } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-profile-header',
  templateUrl: './profile-header.component.html',
  styleUrls: ['./profile-header.component.less']
})
export class ProfileHeaderComponent {

  @Input() profile: Profile | null = null;
  faImage = faImage;

  constructor() { }
}
