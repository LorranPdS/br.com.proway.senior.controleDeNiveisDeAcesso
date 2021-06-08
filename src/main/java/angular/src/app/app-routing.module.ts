import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MenuComponent } from './menu/menu.component';

import { PerfilComponent } from './perfil/perfil.component';

import { AcessoComponent } from './acesso/acesso.component';

import { PermissaoComponent } from './permissao/permissao.component';

import { UsuarioComponent } from './usuario/usuario.component';
import { UsuarioConsultaComponent } from './usuario-consulta/usuario-consulta.component';


const routes: Routes = [
  { path: 'menu', component: MenuComponent },
  { path: 'perfil', component: PerfilComponent },
  { path: 'acesso', component: AcessoComponent },
  { path: 'permissao', component: PermissaoComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'usuario-consulta', component: UsuarioConsultaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
