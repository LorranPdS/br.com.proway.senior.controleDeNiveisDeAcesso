import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { UsuarioConsultaComponent } from './usuario-consulta/usuario-consulta.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { AppRoutingModule } from './app-routing.module';
import { PerfilComponent } from './perfil/perfil.component';
import { PermissaoComponent } from './permissao/permissao.component';
import { AcessoComponent } from './acesso/acesso.component';
import { LoginComponent } from './login/login.component';
import { PerfilCadastroComponent } from './perfil-cadastro/perfil-cadastro.component';
import { PerfilPermissaoComponent } from './perfil-permissao/perfil-permissao.component';
import { PermissaoEditarComponent } from './permissao-editar/permissao-editar.component';
import { AcessoNovoComponent } from './acesso-novo/acesso-novo.component';
import { UsuarioPerfilPermissaoComponent } from './usuario-perfil-permissao/usuario-perfil-permissao.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    UsuarioConsultaComponent,
    UsuarioComponent,
    PerfilComponent,
    PermissaoComponent,
    AcessoComponent,
    LoginComponent,
    PerfilCadastroComponent,
    PerfilPermissaoComponent,
    PermissaoEditarComponent,
    AcessoNovoComponent,
    UsuarioPerfilPermissaoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }