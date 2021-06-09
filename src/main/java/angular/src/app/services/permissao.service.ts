import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Permissao } from '../entidades/permissao';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PermissaoService {
  private permissaoUrl = 'localhost:8080/permissao';

  constructor( private http: HttpClient) { }

  listarTodos(): Observable<Permissao[]> {
    return this.http.get<Permissao[]>(this.permissaoUrl);
  }
}
