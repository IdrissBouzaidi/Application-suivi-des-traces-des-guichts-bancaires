import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { Ajouttrace } from '../models/ajouttrace';

@Injectable({
  providedIn: 'root'
})
export class AjouttraceService {

  private getUrl: string = "http://localhost:8080/api/v1/ajouterTrace";

  constructor(private _httpClient: HttpClient) { }

  ajouterTrace(ajout: Ajouttrace): Observable<Ajouttrace>{
    console.log(ajout);
    return this._httpClient.post<Ajouttrace>(this.getUrl, ajout)
                        .pipe(catchError(this.gererErreur))
  }
  gererErreur(error: { message: any; }){
    return throwError(error.message || "Server Error");
    
  }
}
