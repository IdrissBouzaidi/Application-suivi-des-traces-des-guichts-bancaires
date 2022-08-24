import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Chemin } from '../models/chemin';
import { Trace } from '../models/trace';

@Injectable({
  providedIn: 'root'
})
export class CheminService {

  private getUrl: string = "http://localhost:8080/api/v1/traces";

  constructor(private _httpClient: HttpClient) { }
  
  getChemin(idTrace: number): Observable<Chemin>{
    return this._httpClient.get<Chemin>(`${this.getUrl}/${idTrace}`).pipe(
      map(response => response)
    )
  }
}
