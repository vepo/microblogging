import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { TokenStorageService } from './_services/token-storage.service';

let windowMock = {
  location: { reload: () => { } },
};

describe('AppComponent', () => {
  let tokeService: TokenStorageService;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ], providers: [
        { provide: Window, useFactory: (() => { return windowMock; }) }
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
      username: 'john-doe',
      roles: []
    });
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
    expect(app.title).toEqual('MicroBlogging');

    spyOn(windowMock.location, 'reload').and.callFake(() => { });

    const compiled = fixture.nativeElement as HTMLElement;
    (compiled.querySelector('a[logout]') as HTMLElement)?.click();

  });
});
