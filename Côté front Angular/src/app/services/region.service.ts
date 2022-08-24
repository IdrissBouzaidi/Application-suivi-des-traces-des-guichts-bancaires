import { HttpClient } from '@angular/common/http';
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
    return this._httpClient.get<Region[]>(this.getUrl).pipe(
      map(response => response)
    )
  }
}
