import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Agence } from '../models/agence';
import { Region } from '../models/region';

@Injectable({
  providedIn: 'root'
})
export class RegionService {
  private getUrl: string = "http://localhost:8080/api/v1/regions";

  constructor(private _httpClient: HttpClient) { }

  getRegions(): Observable<Region[]>{

    let username='javainuse'
    let password='password'
    const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });

    return this._httpClient.get<Region[]>(this.getUrl, {headers}).pipe(
      map(response => response)
    )
  }
}
