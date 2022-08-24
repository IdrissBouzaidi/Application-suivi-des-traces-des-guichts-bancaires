import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Chemin } from 'src/app/models/chemin';
import { CheminService } from 'src/app/services/chemin.service';

@Component({
  selector: 'app-chemin',
  templateUrl: './chemin.component.html',
  styleUrls: ['./chemin.component.css']
})
export class CheminComponent implements OnInit {

  chemin: Chemin = new Chemin();

  constructor(private _traceService: CheminService,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const idTrace = this._activatedRoute.snapshot.paramMap.get('id');
    if(idTrace){
      console.log(idTrace);
      this._traceService.getChemin(+idTrace).subscribe(
        data => this.chemin = data
      )
    }
  }

}
