import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgencesComponent } from './components/agences/agences.component';
import { AjouttracesComponent } from './components/ajouttraces/ajouttraces.component';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { CheminComponent } from './components/chemin/chemin.component';
import { DeconnexionComponent } from './components/deconnexion/deconnexion.component';
import { GABsComponent } from './components/gabs/gabs.component';
import { RegionsComponent } from './components/regions/regions.component';
import { TracesComponent } from './components/traces/traces.component';
import { AuthGaurdService } from './services/auth-gaurd.service';

const routes: Routes = [
  {path: 'regions', component: RegionsComponent,canActivate:[AuthGaurdService]},
  {path: 'agences', component: AgencesComponent,canActivate:[AuthGaurdService]},
  {path: 'agences/:id', component: AgencesComponent},
  {path: 'GABs', component: GABsComponent,canActivate:[AuthGaurdService]},
  {path: 'GABs/:id', component: GABsComponent},
  {path: 'Traces', component: TracesComponent,canActivate:[AuthGaurdService]},
  {path: 'Traces/:id', component: TracesComponent},
  {path: 'Chemins', component: CheminComponent},
  {path: 'Chemins/:id', component: CheminComponent},
  {path: 'ajoutertraces', component: AjouttracesComponent},
  {path: 'authentification', component: AuthentificationComponent},
  { path: 'deconnexion', component: DeconnexionComponent,canActivate:[AuthGaurdService] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
