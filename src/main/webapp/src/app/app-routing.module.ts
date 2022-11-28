import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { LoginComponent } from './login/login.component';
import { MbViewerComponent } from './mb-viewer/mb-viewer.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [{
  path: '',
  title: 'Home',
  component: HomeScreenComponent
}, {
  path: 'login',
  title: 'Login',
  component: LoginComponent
}, {
  path: 'register',
  title: 'Register',
  component: RegisterComponent
}, {
  path: 'post/:id/view',
  component: MbViewerComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
