import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { MbEditorComponent } from './mb-editor/mb-editor.component';
import { MbTimelineComponent } from './mb-timeline/mb-timeline.component';
import { MbViewerComponent } from './mb-viewer/mb-viewer.component';

@NgModule({
  declarations: [
    AppComponent,
    MbEditorComponent,
    MbTimelineComponent,
    MbViewerComponent,
    HomeScreenComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    { provide: Window, useValue: window }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
