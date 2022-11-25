import { HttpHandler, HttpRequest, HTTP_INTERCEPTORS, HttpEventType } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { PostService } from '../_services/post.service';
import { TokenStorageService } from '../_services/token-storage.service';

import { AuthInterceptor } from './auth.interceptor';

describe('AuthInterceptor', () => {
  let interceptor: AuthInterceptor;
  let tokeService: TokenStorageService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PostService, AuthInterceptor]
    });
    interceptor = TestBed.inject(AuthInterceptor);
    tokeService = TestBed.inject(TokenStorageService);
    tokeService.signOut();
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  it('should do not when no user logged', () => {
    const requestMock = new HttpRequest('GET', '/test');
    interceptor.intercept(requestMock, {
      handle(req) {
        expect(req.headers.keys()).toEqual([]);
        return of({ type: HttpEventType.Sent })
      },
    });
  });

  it('should add Authorization when user logged', () => {
    tokeService.saveToken('ABCDEF');
    const requestMock = new HttpRequest('GET', '/test');
    interceptor.intercept(requestMock, {
      handle(req) {
        expect(req.headers.keys()).toEqual(['Authorization']);
        expect(req.headers.get('Authorization')).toEqual('Bearer ABCDEF');
        return of({ type: HttpEventType.Sent })
      },
    });
  });
});
