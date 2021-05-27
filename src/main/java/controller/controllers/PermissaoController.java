package controller.controllers;

import java.util.List;

import model.dao.PermissaoDAO;
import model.entidades.Permissao;

/**
 * Classe que faz contato com {@link PermissaoDAO}.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 */
public class PermissaoController {

	PermissaoDAO permissaoDAO = PermissaoDAO.getInstance();

	/**
	 * Cadastro de uma {@link Permissao} no banco de dados.
	 * 
	 * <p>
	 * Responsavel por criar um objeto do tipo {@link Permissao}. Verifica se já
	 * existe uma permissao com o nome informado. Se não existir, o objeto
	 * {@link Permissao} eh enviado ao {@link PermissaoDAO} para ser persistido no
	 * banco de dados e retorna true. Se existir, retorna false.
	 * 
	 * @param nomePermissao String Nome da permissao.
	 * @return boolean Se ja existir uma {@link Permissao} com o nome informado,
	 *         retorna false. Se nao existir, retorna true.
	 */
	public boolean criarPermissao(String nomePermissao) {
		if (permissaoDAO.consultarPorNomeExato(nomePermissao) != null)
			return false;

		Permissao permissao = new Permissao(nomePermissao);
		permissaoDAO.criar(permissao);
		return true;
	}

	/**
	 * Alteracao de uma {@link Permissao}.
	 * 
	 * <p>
	 * Verifica se existe uma {@link Permissao} no banco de dados com o id
	 * informado, se existir, seta os novos valores e altera no banco de dados
	 * retornando true. Se nao existir, retorna false e nao altera.
	 * 
	 * @param idPermissao   - Integer
	 * @param nomePermissao - String
	 * @return boolean Retorna true caso exista uma {@link Permissao} com o id
	 *         recebido. Se nao existir, retorna false.
	 */
	public boolean alterarPermissao(Integer idPermissao, String nomePermissao) {
		if (permissaoDAO.consultarPorId(idPermissao) == null)
			return false;

		Permissao permissao = consultarPermissaoPorId(idPermissao);
		permissao.setNomePermissao(nomePermissao);
		permissaoDAO.alterar(permissao);
		return true;
	}

	/**
	 * Deleta uma {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Verifica se existe uma {@link Permissao} no banco de dados com o id recebido,
	 * se existir, deleta o registro e retorna true. Se nao existir, retorna false.
	 * 
	 * @param idPermissao Integer
	 * @return boolean Retorna true caso exista uma {@link Permissao} com o id
	 *         recebido. Se nao existir, retorna false.
	 */
	public boolean deletarPermissao(Integer idPermissao) {
		if (permissaoDAO.consultarPorId(idPermissao) == null)
			return false;

		Permissao p = consultarPermissaoPorId(idPermissao);
		permissaoDAO.deletar(p);
		return true;
	}

	/**
	 * Consulta um {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Consulta uma {@link Permissao} no banco de dados atraves de seu id e retorna
	 * o objeto completo.
	 * 
	 * @param idPermissao Integer Id do objeto a ser consultado.
	 * @return Permissao
	 */
	public Permissao consultarPermissaoPorId(Integer idPermissao) {
		return permissaoDAO.consultarPorId(idPermissao);
	}

	/**
	 * Consulta de {@link Permissao} pelo nome.
	 * 
	 * <p>
	 * Consulta uma {@link Permissao} no banco de dados atraves de seu nome e
	 * retorna o objeto completo.
	 * 
	 * @param nomePermissao String Nome do objeto a ser consultado.
	 * @return Permissao
	 */
	public Permissao consultarPermissaoPorNomeExato(String nomePermissao) {
		return permissaoDAO.consultarPorNomeExato(nomePermissao);
	}

	/**
	 * Retorna todas os registros de {@link Permissao}.
	 * 
	 * <p>
	 * Consulta todos os registros e retorna uma lista.
	 * 
	 * @return List<Permissao>
	 */
	public List<Permissao> listarTodasAsPermissoes() {
		return permissaoDAO.listar();
	}

	/**
	 * Deleta todos os registros da tabela {@link Permissao}.
	 */
	public void deletarTodos() {
		permissaoDAO.deletarTodos();
	}

}
