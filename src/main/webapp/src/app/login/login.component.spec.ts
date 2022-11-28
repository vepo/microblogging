import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { AuthService } from '../_services/auth.service';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = fixture.debugElement.injector.get(AuthService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call login service', async () => {
    var loginButton = fixture.nativeElement.querySelector('.btn-primary') as HTMLButtonElement;
    expect(loginButton.disabled).toBeTruthy();

    var handleInput = fixture.nativeElement.querySelector('#handle') as HTMLInputElement;
    handleInput.value = 'user';
    handleInput.dispatchEvent(new Event('input'));
    var passwordInput = fixture.nativeElement.querySelector('#password') as HTMLInputElement;
    passwordInput.value = '123456'; //NOSONAR
    passwordInput.dispatchEvent(new Event('input'));
    fixture.autoDetectChanges()
    await fixture.whenStable();
    expect(loginButton.disabled).toBeFalsy();
    spyOn(authService, 'login').and.returnValue(of({
      id: 1,
      handle: 'user',
      email: 'user@microblogging.com',
      accessToken: 'AAAAAA'
    }));
    loginButton.click();
    fixture.autoDetectChanges();
    await fixture.whenStable();
  });
});
