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
	 */
	public boolean criarPermissao(String nomePermissao) {
		if(permissaoDAO.consultarPorNome(nomePermissao) != null)
			return false;
		
		Permissao permissao = new Permissao(nomePermissao);
		permissaoDAO.criar(permissao);
		return true;
	}

	/**
	 * Alteracao de uma {@link Permissao}.
	 * 
	 * <p>
	 * Sera feita uma consulta da {@link Permissao} no banco de dados atraves do id
	 * e nome da permissao, o qual retornara o objeto completo. Feito isso, o novo
	 * nome da {@link Permissao} sera setado ao objeto e enviado ao
	 * {@link PermissaoDAO} para ser atualizado no banco de dados.
	 * 
	 * @param idPermissao   - Integer
	 * @param nomePermissao - String
	 */
	public void alterarPermissao(Integer idPermissao, String nomePermissao) {
		Permissao p = consultarPermissaoPorId(idPermissao);
		p.setNomePermissao(nomePermissao);
		permissaoDAO.alterar(p);
	}

	/**
	 * Remocao de uma {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Responsavel por consultar uma {@link Permissao} pelo seu id no banco de
	 * dados, retornando o objeto com os dados de uma {@link Permissao} preenchidos
	 * e, posteriormente, enviando ao {@link PermissaoDAO} para ser removido do
	 * banco de dados.
	 * 
	 * @param idPermissao Integer
	 */
	public void deletarPermissao(Integer idPermissao) {
		Permissao p = consultarPermissaoPorId(idPermissao);
		permissaoDAO.deletar(p);
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
	public Permissao consultarPermissaoPorNome(String nomePermissao) {
		return permissaoDAO.consultarPorNome(nomePermissao);
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
