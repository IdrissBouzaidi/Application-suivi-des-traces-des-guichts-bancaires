import { TestBed } from '@angular/core/testing';

import { AjouttraceService } from './ajouttrace.service';

describe('AjouttraceService', () => {
  let service: AjouttraceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AjouttraceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
