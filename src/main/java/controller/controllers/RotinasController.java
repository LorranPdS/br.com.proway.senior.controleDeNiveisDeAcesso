package controller.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import model.entidades.PerfilDeUsuario;

public class RotinasController {

	RotinasController() {
	}

	PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

	/**
	 * 
	 * Desativa todas as permissoes expiradas.
	 * 
	 * Lista todos os registros da tabela {@link PerfilDeUsuario} e seta 'ativo'
	 * como 'false' aqueles que possuem a data de expiracao anterior a data atual.
	 * 
	 * @return Integer Quantidade de permissoes expiradas deletadas.
	 */
	public int desativarTodasPermissoesExpiradas() {
		ArrayList<PerfilDeUsuario> ligacoesAntes = (ArrayList<PerfilDeUsuario>) perfilDeUsuarioController.listar();

		for (PerfilDeUsuario ligacao : ligacoesAntes) {
			if (ligacao.getDataExpiracao() != null) {
				if (ligacao.getDataExpiracao().isBefore(LocalDate.now())) {
					PerfilDeUsuario ligacaoExpirada = ligacao;
					ligacaoExpirada.setAtivo(false);
					perfilDeUsuarioController.alterar(ligacaoExpirada);
				}
			}
		}
		ArrayList<PerfilDeUsuario> ligacoesDepois = (ArrayList<PerfilDeUsuario>) perfilDeUsuarioController.listarTodasLigacoesAtivas();
		return (ligacoesAntes.size() - ligacoesDepois.size());
	}

//	/**
//	 * NAO IMPLEMENTADO PELA SPRINT5.
//	 * 
//	 * Expira senhas que passaram da validade.
//	 * 
//	 * Esse metodo deve verificar o campo ultima_alteracao_senha do banco de todos
//	 * os usuarios do sistema e verificar se elas passaram da data de validade
//	 * (regra de negocio a ser definida). Apos isso ele deve bloquear o acesso dos
//	 * usuarios expirados ate que eles troquem a senha atraves de uma mensagem
//	 * enviada automaticamente em seu email/login.
//	 */
//	public void expirarTodasAsSenhaDoSistema() {
//
//	}

}
