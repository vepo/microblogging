import { Component, Inject, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  title = 'MicroBlogging';
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  roles: string[] = [];
  handle: string = "";

  constructor(private tokenStorage: TokenStorageService, @Inject(Window) private window: any) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorage.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorage.getUser();
      if (user != null) {
        this.roles = user.roles;

        this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
        this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

        this.handle = user.handle;
      }
    }
  }

  logout(): boolean {
    this.tokenStorage.signOut();
    this.window.location.reload();
    return false;
  }
}
