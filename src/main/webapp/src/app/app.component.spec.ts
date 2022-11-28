import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { TokenStorageService } from './_services/token-storage.service';

describe('AppComponent', () => {
  let tokeService: TokenStorageService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule, HttpClientTestingModule
      ],
      declarations: [
        AppComponent
      ]
    }).compileComponents();
    tokeService = TestBed.inject(TokenStorageService);
    tokeService.signOut();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'MicroBlogging'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('MicroBlogging');
  });



  it(`should have as title 'MicroBlogging'`, async () => {
    tokeService.saveToken('ABCDEF');
    tokeService.saveUser({
      id: 1,
      handle: 'john-doe',
      roles: []
    });
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
    expect(app.title).toEqual('MicroBlogging');

    const compiled = fixture.nativeElement as HTMLElement;
    (compiled.querySelector('a[logout]') as HTMLElement)?.click();
  });

  it('should switch from login/logout', async () => {
    tokeService.saveToken('ABCDEF');
    tokeService.saveUser({
      id: 1,
      handle: 'john-doe',
      roles: []
    });
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
    expect(fixture.nativeElement.querySelector('[href="/register"]')).toBeNull();
    expect(fixture.nativeElement.querySelector('[href="/login"]')).toBeNull();
    expect(fixture.nativeElement.querySelector('[href="/profile"]')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('[href="/user"]')).toBeTruthy();

    fixture.componentInstance.onLogin(null);
    fixture.detectChanges();
    await fixture.whenStable();
    expect(fixture.nativeElement.querySelector('[href="/register"]')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('[href="/login"]')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('[href="/profile"]')).toBeNull();
    expect(fixture.nativeElement.querySelector('[href="/user"]')).toBeNull();

    fixture.componentInstance.onLogin({ id: 1, handle: 'user', email: 'user@microblogging.com', accessToken: 'AAAA' });
    fixture.detectChanges();
    await fixture.whenStable();
    expect(fixture.nativeElement.querySelector('[href="/register"]')).toBeNull();
    expect(fixture.nativeElement.querySelector('[href="/login"]')).toBeNull();
    expect(fixture.nativeElement.querySelector('[href="/profile"]')).toBeTruthy();
    expect(fixture.nativeElement.querySelector('[href="/user"]')).toBeTruthy();

  });
});
