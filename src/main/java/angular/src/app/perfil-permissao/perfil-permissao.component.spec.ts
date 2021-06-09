import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilPermissaoComponent } from './perfil-permissao.component';

describe('PerfilPermissaoComponent', () => {
  let component: PerfilPermissaoComponent;
  let fixture: ComponentFixture<PerfilPermissaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfilPermissaoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilPermissaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
