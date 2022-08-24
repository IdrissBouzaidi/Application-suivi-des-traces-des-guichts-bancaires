import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Trace } from '../models/trace';

@Injectable({
  providedIn: 'root'
})
export class TraceService {

  private getUrl: string = "http://localhost:8080/api/v1/GABs";

  constructor(private _httpClient: HttpClient) { }
  
  getTraces(codeGAB: string): Observable<Trace[]>{
    return this._httpClient.get<Trace[]>(`${this.getUrl}/${codeGAB}`).pipe(
      map(response => response)
    )
  }
}
