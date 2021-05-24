package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.entidades.PerfilDeUsuario;

public class RotinasController {

	private static RotinasController instance;

	private RotinasController() {
	}

	public static RotinasController getInstance() {
		if (instance == null) {
			instance = new RotinasController();
		}
		return instance;
	}

	PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

	/**
	 * Remove todas as permissoes expiradas.
	 * 
	 * Lista todos os registros da tabela {@link PerfilDeUsuario} e exclui aqueles
	 * que possuem a data de expiracao anterior a data atual.
	 * 
	 * @return Integer Quantidade de permissoes expiradas deletadas.
	 */
	public Integer removerTodasPermissoesExpiradas() {
		ArrayList<PerfilDeUsuario> ligacoesAntes = (ArrayList<PerfilDeUsuario>) perfilDeUsuarioController.listar();

		for (PerfilDeUsuario ligacao : ligacoesAntes) {
			if (ligacao.getDataExpiracao() != null) {
				if (ligacao.getDataExpiracao().isBefore(LocalDate.now())) {
					perfilDeUsuarioController.deletar(ligacao);
				}
			}
		}
		ArrayList<PerfilDeUsuario> ligacoesDepois = (ArrayList<PerfilDeUsuario>) perfilDeUsuarioController.listar();
		return (ligacoesAntes.size() - ligacoesDepois.size());
	}

	/**
	 * NAO IMPLEMENTADO PELA SPRINT5.
	 * 
	 * Expira senhas que passaram da validade.
	 * 
	 * Esse metodo deve verificar o campo ultima_alteracao_senha do banco de todos
	 * os usuarios do sistema e verificar se elas passaram da data de validade
	 * (regra de negocio a ser definida). Apos isso ele deve bloquear o acesso dos
	 * usuarios expirados ate que eles troquem a senha atraves de uma mensagem
	 * enviada automaticamente em seu email/login.
	 */
	public void expirarTodasAsSenhaDoSistema() {

	}

}
