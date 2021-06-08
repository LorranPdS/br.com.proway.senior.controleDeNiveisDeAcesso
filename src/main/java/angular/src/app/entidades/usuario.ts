export interface Usuario {
    id: number,
    login: string,
    hashSenha: string,
    ultimaAlteracaoSenha: Date,
    ultimoCodigo2FA: number
}