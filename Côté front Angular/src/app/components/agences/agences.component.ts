import { Component, OnInit } from '@angular/core';
import { Agence } from 'src/app/models/agence';
import { AgenceService } from 'src/app/services/agence.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-agences',
  templateUrl: './agences.component.html',
  styleUrls: ['./agences.component.css']
})
export class AgencesComponent implements OnInit {

  agences: Agence[] = [];

  constructor(private _agenceService: AgenceService,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const libRegion = this._activatedRoute.snapshot.paramMap.get('id');
    if(libRegion){
      console.log(libRegion);
      this._agenceService.getAgences(libRegion).subscribe(
        data => this.agences = data
      )
    }
  }

}
