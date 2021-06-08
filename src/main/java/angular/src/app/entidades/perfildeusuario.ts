import { Perfil } from "./perfil";
import { Usuario } from "./usuario";

export interface PerfilDeUsuario {
    id: number,
    usuario: Usuario,
    perfil: Perfil,
    dataExpiracao: Date,
    ativo: boolean
}