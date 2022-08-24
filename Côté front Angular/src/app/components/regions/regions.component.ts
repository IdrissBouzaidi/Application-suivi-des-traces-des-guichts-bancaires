import { Component, OnInit } from '@angular/core';
import { Region } from 'src/app/models/region';
import { RegionService } from 'src/app/services/region.service';

@Component({
  selector: 'app-regions',
  templateUrl: './regions.component.html',
  styleUrls: ['./regions.component.css']
})
export class RegionsComponent implements OnInit {

  regions: Region[] = [];

  constructor(private _regionService: RegionService) { }

  ngOnInit(): void {
    this._regionService.getRegions().subscribe(
      data => this.regions = data
    )
  }

}
