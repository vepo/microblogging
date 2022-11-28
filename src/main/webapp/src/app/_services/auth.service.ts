import { HttpClient, HttpHeaders } from '@angular/common/http';
import { createNgModule, Injectable } from '@angular/core';
import { convertToParamMap } from '@angular/router';
import { combineLatest, Observable, share } from 'rxjs';
import { Credentials, LoginResponse, RegisterRequest, User } from '../_model/user.model';

const AUTH_API = 'http://localhost:8080/api/auth';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

export interface loginCallback {
  onLogin(LoginResponse: LoginResponse | null): void;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginUri = AUTH_API + '/login';
  registerUri = AUTH_API + '/register';

  private loginCallbacks: loginCallback[] = [];

  constructor(private http: HttpClient) { }

  login(credentials: Credentials): Observable<LoginResponse> {
    const source = this.http.post<LoginResponse>(this.loginUri, credentials, httpOptions).pipe(share());
    if (this.loginCallbacks.length > 0) {
      source.subscribe(data => {
        this.loginCallbacks.forEach(cb => cb.onLogin(data));
      });
    }
    return source;

  }

  register(request: RegisterRequest): Observable<User> {
    return this.http.post<User>(this.registerUri, request, httpOptions);
  }


  onLogin(loginCallback: loginCallback) {
    this.loginCallbacks.push(loginCallback);
  }

}
