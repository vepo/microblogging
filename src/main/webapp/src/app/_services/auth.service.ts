import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Credentials, RegisterRequest, User } from '../_model/user.model';

const AUTH_API = 'http://localhost:8080/api/auth';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginUri = AUTH_API + '/sign-in';
  registerUri = AUTH_API + '/sign-up';

  constructor(private http: HttpClient) { }

  login(credentials: Credentials): Observable<any> {
    return this.http.post(this.loginUri, credentials, httpOptions);
  }

  register(request: RegisterRequest): Observable<User> {
    return this.http.post<User>(this.registerUri, request, httpOptions);
  }

}
