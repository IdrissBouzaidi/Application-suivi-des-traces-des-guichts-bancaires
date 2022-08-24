import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Agence } from '../models/agence';

@Injectable({
  providedIn: 'root'
})
export class AgenceService {

  private getUrl: string = "http://localhost:8080/api/v1/regions";

  constructor(private _httpClient: HttpClient) { }
  
  getAgences(libRegion: string): Observable<Agence[]>{
    return this._httpClient.get<Agence[]>(`${this.getUrl}/${libRegion}`).pipe(
      map(response => response)
    )
  }
}
