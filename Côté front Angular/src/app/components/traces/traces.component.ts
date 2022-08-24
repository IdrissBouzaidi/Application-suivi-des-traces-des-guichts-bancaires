import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Trace } from 'src/app/models/trace';
import { TraceService } from 'src/app/services/trace.service';

@Component({
  selector: 'app-traces',
  templateUrl: './traces.component.html',
  styleUrls: ['./traces.component.css']
})
export class TracesComponent implements OnInit {

  traces: Trace[] = [];
  codeGAB = this._activatedRoute.snapshot.paramMap.get('id');

  constructor(private _traceService: TraceService,
              private _activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    if(this.codeGAB){
      console.log(this.codeGAB);
      this._traceService.getTraces(this.codeGAB).subscribe(
        data => this.traces = data
      )
    }
  }

}
