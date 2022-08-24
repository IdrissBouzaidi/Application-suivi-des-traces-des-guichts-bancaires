import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GABsComponent } from './gabs.component';

describe('GABsComponent', () => {
  let component: GABsComponent;
  let fixture: ComponentFixture<GABsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GABsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GABsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
