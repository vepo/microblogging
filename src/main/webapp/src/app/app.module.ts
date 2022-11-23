import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MbEditorComponent } from './mb-editor/mb-editor.component';
import { MbTimelineComponent } from './mb-timeline/mb-timeline.component';
import { MbViewerComponent } from './mb-viewer/mb-viewer.component';
import { HomeScreenComponent } from './home-screen/home-screen.component';
import { FormsModule } from '@angular/forms';

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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
