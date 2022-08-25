import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ajouttrace } from 'src/app/models/ajouttrace';
import { AjouttraceService } from 'src/app/services/ajouttrace.service';

@Component({
  selector: 'app-ajouttraces',
  templateUrl: './ajouttraces.component.html',
  styleUrls: ['./ajouttraces.component.css']
})
export class AjouttracesComponent implements OnInit {

  ajout: Ajouttrace = new Ajouttrace();
  messageErreur: string;

  constructor(private _ajouttraceService: AjouttraceService,
              private _router: Router,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const code = this._activatedRoute.snapshot.paramMap.get('id');
    
    console.log("hh : ");
    console.log(this._activatedRoute.snapshot.params);
    if(code){
      this.ajout.codeGAB = code;
    }


    var msg = this.messageErreur;
    if(this.messageErreur != null){
      alert("Le fichier n'a pas été ajouté");
      alert(msg);
    }
  }

  ajouterTrace(){
    this._ajouttraceService.ajouterTrace(this.ajout).subscribe(
      data => {
        this._router.navigateByUrl("/ajoutertraces");
      },
      (error) => {
        console.log("C'est une erreur : ")
        console.log(error)
        this.messageErreur = error
        this.ngOnInit()
      }
    )
  }

}
