import { Component, OnInit } from '@angular/core';
import { Perfil } from 'app/entidades/perfil';
import { Permissao } from 'app/entidades/permissao';
import { Usuario } from 'app/entidades/usuario';

@Component({
  selector: 'app-acesso-novo',
  templateUrl: './acesso-novo.component.html',
  styleUrls: ['./acesso-novo.component.css']
})
export class AcessoNovoComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
