import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Gab } from 'src/app/models/gab';
import { GabService } from 'src/app/services/gab.service';

@Component({
  selector: 'app-gabs',
  templateUrl: './gabs.component.html',
  styleUrls: ['./gabs.component.css']
})
export class GABsComponent implements OnInit {

  gabs: Gab[] = [];

  constructor(private _gabService: GabService,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    const codeBurpo = this._activatedRoute.snapshot.paramMap.get('id');
    if(codeBurpo){
      console.log(codeBurpo);
      this._gabService.getGABs(codeBurpo).subscribe(
        data => this.gabs = data
      )
    }
  }

}
