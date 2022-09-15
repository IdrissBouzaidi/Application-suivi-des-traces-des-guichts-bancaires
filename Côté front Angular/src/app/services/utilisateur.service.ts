import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UtilisateursNbreLignes } from '../models/utilisateurs-nbre-lignes';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private urProfils: string = "http://localhost:8080/api/v1/profils";
  private urlAjout: string = "http://localhost:8080/api/v1/ajouterProfil";

  constructor(private _httpClient: HttpClient) { }
  
  getProfils(profil: any, lignesRecuperees: number, recherche: any, elementDeTri: any): Observable<UtilisateursNbreLignes>{
    return this._httpClient.get<UtilisateursNbreLignes>(`${this.urProfils}/${profil}&${lignesRecuperees}&${recherche}&${elementDeTri}`).pipe(
      map(response => response)
    )
  }
  ajouterProfil(codeU: any, nom: any, prenom: any, email: any, motDePasse: any, profil: any, etatModification:any) : Observable<number>{
    return this._httpClient.get<number>(`${this.urlAjout}/${codeU}&${nom}&${prenom}&${email}&${motDePasse}&${profil}&${etatModification}`).pipe(
      map(response => response)
    )
  }
}