import { Component, Input } from '@angular/core';
import { Profile } from '../_model/user.model';
import { ProfileService } from '../_services/profile.service';
import { faImage } from '@fortawesome/free-solid-svg-icons';
import { UserService } from '../_services/user.service';
@Component({
  selector: 'app-profile-avatar',
  templateUrl: './profile-avatar.component.html',
  styleUrls: ['./profile-avatar.component.less']
})
export class ProfileAvatarComponent {
  @Input() profile: Profile | null = null;
  faImage = faImage;

  constructor(private userService: UserService) { }

  loadImage(): void {
    document.getElementById('file-avatar')?.click()
  }

  uploadFile(evnt: any) {
    console.log(evnt);
    const file: File = evnt.target.files[0];

    if (file) {
      var reader = new FileReader();
      reader.readAsDataURL(file)
      const instance = this;
      reader.onloadend = function () {
        if (this.result && typeof this.result == 'string') {
          instance.userService.uploadAvatar(instance.profile?.handle!, this.result)
            .subscribe({
              next: data => console.log(data),
              error: data => console.log(data)
            });
        }
      };
      console.log(file);
    }
    return false;
  }
}
