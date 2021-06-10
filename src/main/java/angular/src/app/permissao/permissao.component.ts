import { Component, OnInit } from '@angular/core';
import { Permissao } from '../entidades/permissao';
import {PermissaoService} from '../services/permissao.service'

@Component({
  selector: 'app-permissao',
  templateUrl: './permissao.component.html',
  styleUrls: ['./permissao.component.css']
})
export class PermissaoComponent implements OnInit {
  permissoes: Permissao[] = [];
  constructor(private permissaoService: PermissaoService) { }

  ngOnInit(): void {
    this.getPermissao();
  }

  getPermissao(): void {
    this.permissaoService.getPermissao().subscribe(permissoes => this.permissoes = permissoes);
  }
  
 
}
