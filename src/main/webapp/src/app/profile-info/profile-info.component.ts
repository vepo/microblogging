import { Component, Input } from '@angular/core';
import { Profile } from '../_model/user.model';

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrls: ['./profile-info.component.less']
})
export class ProfileInfoComponent {
  @Input() profile: Profile | null = null;
}
