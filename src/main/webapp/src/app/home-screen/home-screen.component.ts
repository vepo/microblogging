import { Component } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-home-screen',
  templateUrl: './home-screen.component.html',
  styleUrls: ['./home-screen.component.less']
})
export class HomeScreenComponent {
  constructor(private tokenStorage: TokenStorageService) { }

  isLoggedIn(): boolean {
    return this.tokenStorage.getUser() != null;
  }
}
