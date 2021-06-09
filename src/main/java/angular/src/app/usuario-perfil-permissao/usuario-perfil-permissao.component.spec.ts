import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsuarioPerfilPermissaoComponent } from './usuario-perfil-permissao.component';

describe('UsuarioPerfilPermissaoComponent', () => {
  let component: UsuarioPerfilPermissaoComponent;
  let fixture: ComponentFixture<UsuarioPerfilPermissaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsuarioPerfilPermissaoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioPerfilPermissaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
