import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Messages } from '../models/messages';
import { TracesNbrelignes } from '../models/traces-nbrelignes';

@Injectable({
  providedIn: 'root'
})
export class TraceService {

  private urlTraces: string = "http://localhost:8080/api/v1/traces";
  private urlRecupeation: string = "http://localhost:8080/api/v1/recupererTraces";

  constructor(private _httpClient: HttpClient) { }
  
  getTraces(codeGAB: string, lignesRecuperees: number, recherche: any, elementDeTri: any): Observable<TracesNbrelignes>{
    return this._httpClient.get<TracesNbrelignes>(`${this.urlTraces}/${codeGAB}&${lignesRecuperees}&${recherche}&${elementDeTri}`).pipe(
      map(response => response)
    )
  }

  recupererTraces(codeGAB: any, dateDebut: any, dateFin: any): Observable<Messages>{
    return this._httpClient.get<Messages>(`${this.urlRecupeation}/${codeGAB}&${dateDebut}&${dateFin}`).pipe(
      map(response => response)
    )
  }
}
