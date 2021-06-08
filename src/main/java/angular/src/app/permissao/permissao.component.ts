import { Component, OnInit } from '@angular/core';
import { Permissao } from '../entidades/permissao';

@Component({
  selector: 'app-permissao',
  templateUrl: './permissao.component.html',
  styleUrls: ['./permissao.component.css']
})
export class PermissaoComponent implements OnInit {
  permissoes: Permissao[] = [
    { id: 11, nome: 'Imprimir relatório de compras' },
    { id: 12, nome: 'Pesquisar pessoas por nome' },
    { id: 13, nome: 'Adicionar novos usuários' },
    { id: 14, nome: 'Conceder férias totais' },
    { id: 15, nome: 'Abrir requerimento' },
    { id: 16, nome: 'Cadastrar cargo de usuário' },
    { id: 17, nome: 'Criar novo produto' },
    { id: 18, nome: 'Alterar quantidade de estoques' },
    { id: 19, nome: 'Cadastrar nova rota no setor PPCP' },
    { id: 11, nome: 'Imprimir relatório de compras' },
    { id: 12, nome: 'Pesquisar pessoas por nome' },
    { id: 13, nome: 'Adicionar novos usuários' },
    { id: 14, nome: 'Conceder férias totais' },
    { id: 15, nome: 'Abrir requerimento' },
    { id: 16, nome: 'Cadastrar cargo de usuário' },
    { id: 17, nome: 'Criar novo produto' },
    { id: 18, nome: 'Alterar quantidade de estoques' },
    { id: 19, nome: 'Cadastrar nova rota no setor PPCP' },
    { id: 11, nome: 'Imprimir relatório de compras' },
    { id: 12, nome: 'Pesquisar pessoas por nome' },
    { id: 13, nome: 'Adicionar novos usuários' },
    { id: 14, nome: 'Conceder férias totais' },
    { id: 15, nome: 'Abrir requerimento' },
    { id: 16, nome: 'Cadastrar cargo de usuário' },
    { id: 17, nome: 'Criar novo produto' },
    { id: 18, nome: 'Alterar quantidade de estoques' },
    { id: 19, nome: 'Cadastrar nova rota no setor PPCP' },
  ];
  constructor() { }

  ngOnInit(): void {
  }

  

}
