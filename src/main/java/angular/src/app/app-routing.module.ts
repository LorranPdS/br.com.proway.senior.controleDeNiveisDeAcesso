import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AcessoComponent } from './acesso/acesso.component';

import { MenuComponent } from './menu/menu.component';

import { PerfilComponent } from './perfil/perfil.component';

import { PerfilCadastroComponent} from './perfil-cadastro/perfil-cadastro.component'

import { PerfilPermissaoComponent } from './perfil-permissao/perfil-permissao.component';


import { PermissaoComponent } from './permissao/permissao.component';
import { PermissaoEditarComponent } from './permissao-editar/permissao-editar.component';

import { UsuarioComponent } from './usuario/usuario.component';
import { UsuarioConsultaComponent } from './usuario-consulta/usuario-consulta.component';


const routes: Routes = [
  { path: 'acesso', component: AcessoComponent },

  { path: 'menu', component: MenuComponent },

  { path: 'perfil', component: PerfilComponent },

  { path: 'perfil-cadastro', component: PerfilCadastroComponent},

  { path: 'perfil-permissao', component: PerfilPermissaoComponent },


  { path: 'permissao', component: PermissaoComponent },
  { path: 'permissao-editar', component: PermissaoEditarComponent },
  
  { path: 'usuario', component: UsuarioComponent },
  { path: 'usuario-consulta', component: UsuarioConsultaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
