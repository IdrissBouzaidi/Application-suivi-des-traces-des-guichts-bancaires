import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthentificationComponent } from './components/authentification/authentification.component';
import { ObjetsorigineComponent } from './components/objetsorigine/objetsorigine.component';
import { AuthGaurdService } from './services/auth-gaurd.service';

const routes: Routes = [
  {path: 'regions', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'agences', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'agences/:id', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'GABs', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'GABs/:id', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'traces', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'traces/:id', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'profils', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'profils/:id', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: '', component: ObjetsorigineComponent,canActivate:[AuthGaurdService]},
  {path: 'authentification', component: AuthentificationComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
