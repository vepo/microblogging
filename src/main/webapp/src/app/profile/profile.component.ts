import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Profile } from '../_model/user.model';
import { ProfileService } from '../_services/profile.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.less']
})
export class ProfileComponent implements OnInit {
  profile: Profile | null = null;

  constructor(private route: ActivatedRoute, private profileService: ProfileService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => this.profileService.findProfileByHandle(params.get('handle')!)
      .subscribe({ next: profile => this.profile = profile }));
  }
}
