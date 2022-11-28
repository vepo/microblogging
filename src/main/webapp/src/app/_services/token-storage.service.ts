import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../_model/user.model';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  tokenObservable: Observable<string>;

  constructor() {
    this.tokenObservable = new Observable<string>();
  }

  signOut() {
    window.sessionStorage.clear();
  }

  public token(): Observable<string> {
    return this.tokenObservable;
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): User | null {
    const userData = window.sessionStorage.getItem(USER_KEY);
    if (userData != null) {
      return JSON.parse(userData);
    } else {
      return null;
    }
  }

  public isLoggedIn(): boolean {
    return window.sessionStorage.getItem(TOKEN_KEY) != null;
  }

}
