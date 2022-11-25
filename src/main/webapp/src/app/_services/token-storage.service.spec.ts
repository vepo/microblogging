import { TestBed } from '@angular/core/testing';
import { User } from '../_model/user.model';

import { TokenStorageService } from './token-storage.service';

describe('TokenStorageService', () => {
  let service: TokenStorageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TokenStorageService);
    service.signOut();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should store tokens', () => {
    expect(service.getToken()).toBeNull();
    service.saveToken("TOKEN")
    expect(service.getToken()).toBe("TOKEN");
  });

  it('should store users', () => {
    expect(service.getUser()).toBeNull();
    const user: User = {
      username: "admin",
      roles: ['ADMIN', 'USER']
    };
    service.saveUser(user);
    expect(service.getUser()).toEqual(user);
  });
});
