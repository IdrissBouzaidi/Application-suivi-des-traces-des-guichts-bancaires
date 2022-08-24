import { TestBed } from '@angular/core/testing';

import { CheminService } from './chemin.service';

describe('CheminService', () => {
  let service: CheminService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CheminService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
