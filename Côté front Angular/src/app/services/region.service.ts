import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { RegionsNbrelignes } from '../models/regions-nbrelignes';

@Injectable({
  providedIn: 'root'
})
export class RegionService {
  private urlRegions: string = "http://localhost:8080/api/v1/regions";
  private urlAjout: string = "http://localhost:8080/api/v1/ajouterRegion";

  constructor(private _httpClient: HttpClient) { }

  getRegions(lignesRecuperees: number, recherche: any, elementDeTri: any): Observable<RegionsNbrelignes>{
    return this._httpClient.get<RegionsNbrelignes>(`${this.urlRegions}/${lignesRecuperees}&${recherche}&${elementDeTri}`).pipe(
      map(response => response)
    )
  }
  ajouterRegion(codReg: any, libReg: any, etatModification:any) : Observable<number>{
    return this._httpClient.get<number>(`${this.urlAjout}/${codReg}&${libReg}&${etatModification}`).pipe(
      map(response => response)
    )
  }
}
