import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should allow register an user', () => {
    service.register({
      username: "user",
      email: "user@microblogging.com",
      password: "123456"
    }).subscribe({
      next: res => expect(res).toBeTruthy(),
      error: fail
    });
    const req = httpTestingController.expectOne(service.registerUri);
    expect(req.request.method).toEqual('POST');
  });

  it('should allow create a JWT Token', () => {
    service.login({
      username: "user",
      password: "123456"
    }).subscribe({
      next: res => expect(res).toBeTruthy(),
      error: fail
    });
    const req = httpTestingController.expectOne(service.loginUri);
    expect(req.request.method).toEqual('POST');
  });
});
