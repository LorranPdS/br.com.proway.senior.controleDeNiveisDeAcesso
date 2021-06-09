import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PermissaoEditarComponent } from './permissao-editar.component';

describe('PermissaoEditarComponent', () => {
  let component: PermissaoEditarComponent;
  let fixture: ComponentFixture<PermissaoEditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PermissaoEditarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PermissaoEditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
