import { Component, OnInit } from '@angular/core';
import { PerfilDeUsuario } from '../entidades/perfildeusuario';
import { Usuario } from '../entidades/usuario';
import { PerfilComponent } from '../perfil/perfil.component';

@Component({
  selector: 'app-acesso',
  templateUrl: './acesso.component.html',
  styleUrls: ['./acesso.component.css']
})
export class AcessoComponent implements OnInit {

  acessos: PerfilDeUsuario[] = []
  constructor() { }

  ngOnInit(): void {
  }

}
