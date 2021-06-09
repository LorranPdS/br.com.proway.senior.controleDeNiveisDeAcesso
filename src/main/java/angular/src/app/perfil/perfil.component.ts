import { Component, OnInit } from '@angular/core';
import { Perfil } from 'app/entidades/perfil';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {
  perfis: Perfil[] = [
    {id: 2, nome: 'ADMIN', permissoes: []},
    {id: 2, nome: 'ADMIN', permissoes: []},
    {id: 2, nome: 'ADMIN', permissoes: [{id: 3, nome: 'Geral'}]}
  ];

  constructor() { }

  ngOnInit(): void {
  }
  }


