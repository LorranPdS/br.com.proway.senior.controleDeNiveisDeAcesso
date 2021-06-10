import { Injectable } from '@angular/core';
import { Permissao } from 'app/entidades/permissao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissaoService {

  private permissaoUrl = 'http://localhost:8080/permissao';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getPermissao(): Observable<Permissao[]> {
    return this.http.get<Permissao[]>(this.permissaoUrl + '/listar')
  }

  cadastrar(permissao: Permissao) : Observable<Permissao>{
    return this.http.post<Permissao>(this.permissaoUrl + '/criar', permissao, this.httpOptions)
  }

  deletar(id : number): Observable<Permissao>{
    const url = `${this.permissaoUrl}/${id}`;
    return this.http.delete<Permissao>(url, this.httpOptions) 
  }

  buscarPorNome(nomePermissao: string): Observable<Permissao>{
    const url = `${this.permissaoUrl}/${nomePermissao}`;
    return this.http.get<Permissao>(url, this.httpOptions)
  }
}
