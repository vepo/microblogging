import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { User } from '../_model/user.model';
import { AuthService } from '../_services/auth.service';

import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [RegisterComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should disable Register Button when invalid form', async () => {
    await fixture.whenStable();
    const compile = fixture.nativeElement as HTMLElement;
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();

    (compile.querySelector('#handle') as HTMLInputElement).value = 'user-1';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#password') as HTMLInputElement).value = '123456';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#email') as HTMLInputElement).value = 'user@microblogging.com';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeFalsy();
  })

  it('should not accept invalid handle', async () => {
    await fixture.whenStable();
    const compile = fixture.nativeElement as HTMLElement;
    // valid values
    (compile.querySelector('#password') as HTMLInputElement).value = '123456';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#email') as HTMLInputElement).value = 'user@microblogging.com';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'));

    // invalid values
    (compile.querySelector('#handle') as HTMLInputElement).value = '';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'))
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Handle is required');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();

    (compile.querySelector('#handle') as HTMLInputElement).value = 'usr';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Handle must be at least 3 characters');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();

    (compile.querySelector('#handle') as HTMLInputElement).value = 'handle-21-chars-aaaaa';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Handle must be at most 20 characters');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();
  });

  it('should not accept invalid email', async () => {
    await fixture.whenStable();
    const compile = fixture.nativeElement as HTMLElement;
    // valid values
    (compile.querySelector('#password') as HTMLInputElement).value = '123456';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#handle') as HTMLInputElement).value = 'user-1';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'));

    // invalid values
    (compile.querySelector('#email') as HTMLInputElement).value = '';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'))
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Email is required');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();

    (compile.querySelector('#email') as HTMLInputElement).value = 'invalid-email';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Email must be a valid email address');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();
  });

  it('should not accept invalid password', async () => {
    await fixture.whenStable();
    const compile = fixture.nativeElement as HTMLElement;
    // valid values
    (compile.querySelector('#password') as HTMLInputElement).value = '123456';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#email') as HTMLInputElement).value = 'user@microblogging.com';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'));

    // invalid values
    (compile.querySelector('#password') as HTMLInputElement).value = '';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'))
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Password is required');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();

    (compile.querySelector('#password') as HTMLInputElement).value = 'pwd';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    fixture.detectChanges();
    await fixture.whenStable();
    expect(compile.querySelector('.alert-danger')?.textContent?.trim()).toBe('Password must be at least 6 characters');
    expect((compile.querySelector('button.btn-primary') as HTMLButtonElement).disabled).toBeTruthy();
  });

  it('should call submit', async () => {
    await fixture.whenStable();
    const compile = fixture.nativeElement as HTMLElement;


    let authService = fixture.debugElement.injector.get(AuthService);
    let registeredUser: User = {
      id: 1,
      handle: "user",
      roles: []
    };
    spyOn(authService, 'register').withArgs({ handle: 'user', email: 'user@microblogging.com', password: '123456' }).and.returnValue(of(registeredUser));

    (compile.querySelector('#password') as HTMLInputElement).value = '123456';
    (compile.querySelector('#password') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#email') as HTMLInputElement).value = 'user@microblogging.com';
    (compile.querySelector('#email') as HTMLInputElement).dispatchEvent(new Event('input'));
    (compile.querySelector('#handle') as HTMLInputElement).value = 'user';
    (compile.querySelector('#handle') as HTMLInputElement).dispatchEvent(new Event('input'))

    fixture.detectChanges();
    await fixture.whenStable();
    (compile.querySelector('button.btn-primary') as HTMLButtonElement).click();
    expect(authService.register).toHaveBeenCalled();
  });
});
