import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Utilisateur } from '../models/utilisateur';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  private urlAuth: string = "http://localhost:8080/api/v1/authentification";

  constructor(private _httpClient: HttpClient) { }

  validerInformations(utilisateur: Utilisateur): Observable<Utilisateur>{
    return this._httpClient.get<Utilisateur>(`${this.urlAuth}/${utilisateur.email}&${utilisateur.motDePasse}`).pipe(
      map(response => response)
    )
  }

  isUserLoggedIn() {
    let email = sessionStorage.getItem('email')
    return !(email === null)
  }

  logOut() {
    sessionStorage.removeItem('email')
  }
}