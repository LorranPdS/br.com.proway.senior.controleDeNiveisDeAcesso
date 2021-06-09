import { getLocaleTimeFormat } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Usuario } from '../entidades/usuario';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})

export class UsuarioComponent implements OnInit {
  usuarios: Usuario[] = [
  { id: 11, login: 'email1@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 12, login: 'email2@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 13, login: 'email3@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },  
  { id: 14, login: 'email4@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 15, login: 'email5@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 16, login: 'email6@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 17, login: 'email7@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 18, login: 'email8@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  { id: 19, login: 'email9@gmail.com', hashSenha: '2345', ultimaAlteracaoSenha: new Date, ultimoCodigo2FA: 8415 },
  ];
  constructor() { }

  ngOnInit(): void {
  }

}
