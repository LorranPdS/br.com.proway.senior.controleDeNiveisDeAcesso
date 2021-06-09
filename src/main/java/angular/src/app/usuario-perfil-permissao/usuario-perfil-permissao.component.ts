import { Component, OnInit } from '@angular/core';
import { Perfil } from 'app/entidades/perfil';
import { Permissao } from 'app/entidades/permissao';

@Component({
  selector: 'app-usuario-perfil-permissao',
  templateUrl: './usuario-perfil-permissao.component.html',
  styleUrls: ['./usuario-perfil-permissao.component.css']
})
export class UsuarioPerfilPermissaoComponent implements OnInit {
  perfis: Perfil[] = [
    { id: 1, nome: 'Auxiliar administrativo', permissoes: [] },
    { id: 1, nome: 'Assistente de TI', permissoes: [] },
    { id: 1, nome: 'Estagiário de Desenvolvimento', permissoes: [] },
    { id: 1, nome: 'Representante de Vendas', permissoes: [] },
    { id: 1, nome: 'Coordenador de RH', permissoes: [] },
    { id: 1, nome: 'Desenvolvedor Senior', permissoes: [] },
    { id: 1, nome: 'Desenvolvedor Junior', permissoes: [] },
    { id: 1, nome: 'Coordenador de Desenvolvimento', permissoes: [] },
    { id: 1, nome: 'Desenvolvedor Pleno', permissoes: [] },
  ]
  permissoes: Permissao[] = [
    { id: 11, nome: 'Imprimir relatório de compras do setor de viagens aéreas' },
    { id: 12, nome: 'Pesquisar pessoas por nome' },
    { id: 13, nome: 'Adicionar novos usuários' },
    { id: 14, nome: 'Conceder férias totais' },
    { id: 15, nome: 'Abrir requerimento' },
    { id: 16, nome: 'Cadastrar cargo de usuário' },
    { id: 17, nome: 'Criar novo produto' },
    { id: 18, nome: 'Alterar quantidade de estoques' },
    { id: 19, nome: 'Cadastrar nova rota no setor PPCP' },
    { id: 11, nome: 'Imprimir relatório de compras do setor de viagens aéreas' },
    { id: 12, nome: 'Pesquisar pessoas por nome' },
    { id: 13, nome: 'Adicionar novos usuários' },
    { id: 14, nome: 'Conceder férias totais' },
    { id: 15, nome: 'Abrir requerimento' },
    { id: 16, nome: 'Cadastrar cargo de usuário' },
    { id: 17, nome: 'Criar novo produto' },
    { id: 18, nome: 'Alterar quantidade de estoques' },
    { id: 19, nome: 'Cadastrar nova rota no setor PPCP' },
    { id: 11, nome: 'Imprimir relatório de compras do setor de viagens aéreas' },
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
