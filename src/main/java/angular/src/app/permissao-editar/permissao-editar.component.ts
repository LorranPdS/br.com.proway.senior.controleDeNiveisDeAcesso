import { Component, OnInit } from '@angular/core';
import { Permissao } from 'app/entidades/permissao';
import { PermissaoComponent } from 'app/permissao/permissao.component';
import { PermissaoService } from 'app/services/permissao.service';

@Component({
  selector: 'app-permissao-editar',
  templateUrl: './permissao-editar.component.html',
  styleUrls: ['./permissao-editar.component.css']
})
export class PermissaoEditarComponent implements OnInit {
  permissao!: Permissao;

  constructor(private permissaoService: PermissaoService) { }

  ngOnInit(): void {
    this.permissao = this.permissaoService.buscarPermissaoAEditar();
  }

  editar(nome: string) {
    this.permissao.nomePermissao = nome;
    this.permissaoService.editar(this.permissao.idPermissao as number, this.permissao).subscribe();
  }
}
