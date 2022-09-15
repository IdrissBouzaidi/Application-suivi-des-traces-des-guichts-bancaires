import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjetsorigineComponent } from './objetsorigine.component';

describe('ObjetsorigineComponent', () => {
  let component: ObjetsorigineComponent;
  let fixture: ComponentFixture<ObjetsorigineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ObjetsorigineComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ObjetsorigineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
