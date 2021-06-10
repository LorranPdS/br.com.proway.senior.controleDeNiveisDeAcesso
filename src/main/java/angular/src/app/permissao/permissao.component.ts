import { NgForOf, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Permissao } from '../entidades/permissao';
import { PermissaoService } from '../services/permissao.service'

@Component({
  selector: 'app-permissao',
  templateUrl: './permissao.component.html',
  styleUrls: ['./permissao.component.css']
})
export class PermissaoComponent implements OnInit {
  permissoes: Permissao[] = [];
  permissao!: Permissao;
  constructor(private permissaoService: PermissaoService) { }

  ngOnInit(): void {
    this.listarTodos();
  }

  listarTodos(): void {
    this.permissaoService.listarTodos().subscribe(permissoes => this.permissoes = permissoes);
  }

  cadastrar(nomePermissao: string): void {
    nomePermissao = nomePermissao.trim();
    if (!nomePermissao) { return; }
    this.permissaoService.cadastrar({ nomePermissao } as Permissao).subscribe( (_) =>  this.listarTodos())
  }

  deletar(permissao: Permissao): void {
    this.permissaoService.deletar(permissao.idPermissao as number).subscribe(
      (_) =>  this.listarTodos()
    )
   
  }

  buscarPorNome(nomePermissao: string): void {
    this.permissoes = [];
    this.permissaoService.buscarPorNome(nomePermissao).subscribe(permissao => this.permissoes.push(permissao));
  }
}
