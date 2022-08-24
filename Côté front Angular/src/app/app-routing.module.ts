import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AgencesComponent } from './components/agences/agences.component';
import { AjouttracesComponent } from './components/ajouttraces/ajouttraces.component';
import { CheminComponent } from './components/chemin/chemin.component';
import { GABsComponent } from './components/gabs/gabs.component';
import { RegionsComponent } from './components/regions/regions.component';
import { TracesComponent } from './components/traces/traces.component';

const routes: Routes = [
  {path: 'regions', component: RegionsComponent},
  {path: 'agences', component: AgencesComponent},
  {path: 'agences/:id', component: AgencesComponent},
  {path: 'GABs', component: GABsComponent},
  {path: 'GABs/:id', component: GABsComponent},
  {path: 'Traces', component: TracesComponent},
  {path: 'Traces/:id', component: TracesComponent},
  {path: 'Chemins', component: CheminComponent},
  {path: 'Chemins/:id', component: CheminComponent},
  {path: 'ajoutertraces', component: AjouttracesComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
