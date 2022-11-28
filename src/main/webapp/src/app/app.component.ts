import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { LoginResponse } from './_model/user.model';
import { AuthService, loginCallback } from './_services/auth.service';
import { TokenStorageService } from './_services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit, loginCallback {
  title = 'MicroBlogging';
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  roles: string[] = [];
  handle: string = "";


  constructor(private tokenStorage: TokenStorageService,
    private authService: AuthService) { }


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
    this.authService.onLogin(this);
  }

  onLogin(LoginResponse: LoginResponse | null): void {
    if (LoginResponse != null) {
      this.isLoggedIn = true;
      this.roles = [];
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
      this.handle = LoginResponse.handle;
    } else {
      this.isLoggedIn = false;
      this.showAdminBoard = false;
      this.showModeratorBoard = false;
    }
  }

  logout(): boolean {
    this.tokenStorage.signOut();
    this.showModeratorBoard = this.showAdminBoard = this.isLoggedIn = false;
    return false;
  }
}
