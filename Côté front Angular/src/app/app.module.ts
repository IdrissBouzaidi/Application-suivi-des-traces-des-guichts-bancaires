import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from "@angular/common/http";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegionsComponent } from './components/regions/regions.component';
import { AgencesComponent } from './components/agences/agences.component';
import { GABsComponent } from './components/gabs/gabs.component';
import { TracesComponent } from './components/traces/traces.component';
import { CheminComponent } from './components/chemin/chemin.component';
import { AjouttracesComponent } from './components/ajouttraces/ajouttraces.component';
import { FormsModule } from '@angular/forms';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { DeconnexionComponent } from './components/deconnexion/deconnexion.component';


@NgModule({
  declarations: [
    AppComponent,
    RegionsComponent,
    AgencesComponent,
    GABsComponent,
    TracesComponent,
    CheminComponent,
    AjouttracesComponent,
    AuthentificationComponent,
    DeconnexionComponent
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
