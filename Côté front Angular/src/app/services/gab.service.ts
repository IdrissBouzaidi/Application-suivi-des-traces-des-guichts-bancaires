import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Gab } from '../models/gab';

@Injectable({
  providedIn: 'root'
})
export class GabService {

  private getUrl: string = "http://localhost:8080/api/v1/agences";

  constructor(private _httpClient: HttpClient) { }
  
  getGABs(codeBurpo: string): Observable<Gab[]>{
    return this._httpClient.get<Gab[]>(`${this.getUrl}/${codeBurpo}`).pipe(
      map(response => response)
    )
  }
}
