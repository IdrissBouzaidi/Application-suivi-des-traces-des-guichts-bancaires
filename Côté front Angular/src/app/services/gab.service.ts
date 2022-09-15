import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { GabsNbrelignes } from '../models/gabs-nbrelignes';

@Injectable({
  providedIn: 'root'
})
export class GabService {

  private urlGABs: string = "http://localhost:8080/api/v1/GABs";
  private urlAjout: string = "http://localhost:8080/api/v1/ajouterGAB";

  constructor(private _httpClient: HttpClient) { }
  
  getGABs(codeBurpo: string, lignesRecuperees: number, recherche: any, elementDeTri: any): Observable<GabsNbrelignes>{
    return this._httpClient.get<GabsNbrelignes>(`${this.urlGABs}/${codeBurpo}&${lignesRecuperees}&${recherche}&${elementDeTri}`).pipe(
      map(response => response)
    )
  }
  ajouterGAB(codeGAB: any, libGAB: any, urlGAB: any, urlGABArchive: any, codeBurpo: any, etatModification:any) : Observable<number>{
    for(let i = 0; i<Math.max(urlGAB.length, urlGABArchive.length); i++){
      urlGABArchive = urlGABArchive.replace('\\', 'µ');
      urlGAB = urlGAB.replace('\\', 'µ');
    }
    return this._httpClient.get<number>(`${this.urlAjout}/${codeGAB}&${libGAB}&${urlGAB}&${urlGABArchive}&${codeBurpo}&${etatModification}`).pipe(
      map(response => response)
    )
  }
}
