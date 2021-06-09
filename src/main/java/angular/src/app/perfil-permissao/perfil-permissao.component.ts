import { Component, OnInit } from '@angular/core';
import { Perfil } from '../entidades/perfil';

@Component({
  selector: 'app-perfil-permissao',
  templateUrl: './perfil-permissao.component.html',
  styleUrls: ['./perfil-permissao.component.css']
})
export class PerfilPermissaoComponent implements OnInit {

  perfis: Perfil[] = [

    { id: 2, nome: 'ADMIN', permissoes: [ { id: 3, nome: 'tudão' } ]},
    { id: 3, nome: 'VENDEDOR', permissoes: [ { id: 5, nome: 'vendas' } ]},
    { id: 4, nome: 'GERENTE', permissoes: [ { id: 5, nome: 'vendas' }, { id: 6, nome: 'consultas' } ]},
    
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
