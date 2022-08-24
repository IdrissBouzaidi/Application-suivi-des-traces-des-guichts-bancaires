import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjouttracesComponent } from './ajouttraces.component';

describe('AjouttracesComponent', () => {
  let component: AjouttracesComponent;
  let fixture: ComponentFixture<AjouttracesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AjouttracesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AjouttracesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
