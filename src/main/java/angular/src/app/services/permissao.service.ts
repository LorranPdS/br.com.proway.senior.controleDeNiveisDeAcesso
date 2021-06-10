import { Injectable } from '@angular/core';
import { Permissao } from 'app/entidades/permissao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissaoService {

  private permissaoUrlListar = 'localhost:8080/permissao/listar';
  private permissaoUrl = 'localhost:8080/permissao';

  constructor(private http: HttpClient) { }

  getPermissao(): Observable<Permissao[]> {
    return this.http.get<Permissao[]>(this.permissaoUrl)
  }
}
