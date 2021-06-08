import { Permissao } from "./permissao";

export interface Perfil {
    id: number,
    nome: string,
    permissoes: Permissao[]
}