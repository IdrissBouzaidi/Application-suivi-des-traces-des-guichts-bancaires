import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { ObjetsorigineComponent } from './components/objetsorigine/objetsorigine.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthentificationComponent,
    ObjetsorigineComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
