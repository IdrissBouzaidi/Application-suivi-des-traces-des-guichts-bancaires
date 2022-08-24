import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Ajouttrace } from 'src/app/models/ajouttrace';
import { Trace } from 'src/app/models/trace';
import { AjouttraceService } from 'src/app/services/ajouttrace.service';
import { TracesComponent } from '../traces/traces.component';

@Component({
  selector: 'app-ajouttraces',
  templateUrl: './ajouttraces.component.html',
  styleUrls: ['./ajouttraces.component.css']
})
export class AjouttracesComponent implements OnInit {

  ajout: Ajouttrace = new Ajouttrace();

  constructor(private _ajouttraceService: AjouttraceService,
              private _router: Router,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const code = this._activatedRoute.snapshot.paramMap.get('id');
    if(code){
      this.ajout.codeGAB = code;
    }
  }

  ajouterTrace(){
    this._ajouttraceService.ajouterTrace(this.ajout).subscribe(
      data => {
        console.log(data);
        this._router.navigateByUrl("/ajoutertraces");
      }
    )
  }

}
