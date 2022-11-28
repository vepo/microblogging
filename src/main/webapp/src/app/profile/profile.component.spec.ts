import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TokenStorageService } from '../_services/token-storage.service';

import { ProfileComponent } from './profile.component';

describe('ProfileComponent', () => {
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ProfileComponent);
    const tokenStorage = fixture.debugElement.injector.get(TokenStorageService);
    tokenStorage.saveToken('AAAAAAA');
    tokenStorage.saveUser({
      id: 1,
      handle: 'user',
      roles: []
    });
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
