import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcessoNovoComponent } from './acesso-novo.component';

describe('AcessoNovoComponent', () => {
  let component: AcessoNovoComponent;
  let fixture: ComponentFixture<AcessoNovoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcessoNovoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AcessoNovoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
