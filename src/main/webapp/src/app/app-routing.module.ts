import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { MbViewerComponent } from './mb-viewer/mb-viewer.component';

const routes: Routes = [{
  path: '',
  title: 'Home',
  component: HomeScreenComponent
}, {
  path: 'post/:id/view',
  component: MbViewerComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
