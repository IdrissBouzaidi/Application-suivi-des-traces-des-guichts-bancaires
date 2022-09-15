import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AgencesNbrelignes } from '../models/agences-nbrelignes';

@Injectable({
  providedIn: 'root'
})
export class AgenceService {

  private urlAgences: string = "http://localhost:8080/api/v1/agences";
  private urlAjout: string = "http://localhost:8080/api/v1/ajouterAgence";

  constructor(private _httpClient: HttpClient) { }
  
  getAgences(codReg: string, lignesRecuperees: number, recherche: any, elementDeTri: any): Observable<AgencesNbrelignes>{
    return this._httpClient.get<AgencesNbrelignes>(`${this.urlAgences}/${codReg}&${lignesRecuperees}&${recherche}&${elementDeTri}`).pipe(
      map(response => response)
    )
  }
  ajouterAgence(codeBurpo: any, libelleBurpo: any, codReg: any, etatModification:any) : Observable<number>{
    return this._httpClient.get<number>(`${this.urlAjout}/${codeBurpo}&${libelleBurpo}&${codReg}&${etatModification}`).pipe(
      map(response => response)
    )
  }
}
