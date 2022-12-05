import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from '../_model/user.model';

const PROFILE_API = 'http://localhost:8080/api/profile';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient: HttpClient) { }

  findProfileByHandle(handle: string): Observable<Profile> {
    return this.httpClient.get<Profile>(`${PROFILE_API}/${handle}`, httpOptions);
  }
}
