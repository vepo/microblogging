import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


const USER_API = 'http://localhost:8080/api/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  uploadAvatar(handle: string, imageAsBase64: string): Observable<any> {
    return this.httpClient.post<any>(`${USER_API}/avatar`, {
      data: imageAsBase64
    });
  }
}
