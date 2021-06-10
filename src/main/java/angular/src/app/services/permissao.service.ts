import { Injectable } from '@angular/core';
import { Permissao } from 'app/entidades/permissao';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PermissaoService {

  private permissaoUrl = 'localhost:8080/permissao/listar';

  constructor(private http: HttpClient) { }

  getPermissao(): Observable<Permissao[]> {
    return this.http.get<Permissao[]>(this.permissaoUrl)

}
}
