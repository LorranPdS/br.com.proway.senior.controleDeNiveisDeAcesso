package controller;

import java.util.List;

import model.dao.PermissaoDAO;
import model.entidades.Permissao;

public class PermissaoController {

	private static PermissaoController instance;

	private PermissaoController() {
	}

	public static PermissaoController getInstance() {
		if (instance == null) {
			instance = new PermissaoController();
		}
		return instance;
	}

	/**
	 * Criacao de um {@link Permissao} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Permissao}. O objeto
	 * {@link Permissao} e enviado ao {@link PermissaoDAO} para ser persistido no
	 * banco de dados.
	 * 
	 * @param String
	 */
	public void criarPermissao(String nomePermissao) {
		Permissao permissao = new Permissao(nomePermissao);
		PermissaoDAO.getInstance().criar(permissao);
	}

	/**
	 * Alteracao de uma {@link Permissao}.
	 * 
	 * Sera feita uma consulta da {@link Permissao} no banco de dados atraves do id
	 * e nome da permissao, o qual retornara o objeto completo. Feito isso, o novo
	 * nome da {@link Permissao} sera setado ao objeto e enviado ao
	 * {@link PermissaoDAO} para ser atualizado no banco de dados.
	 * 
	 * @param idPermissao   - Interger
	 * @param nomePermissao - String
	 */
	public void alterarPermissao(Integer idPermissao, String nomePermissao) {
		Permissao p = consultarPermissao(idPermissao);
		p.setNomePermissao(nomePermissao);
		PermissaoDAO.getInstance().alterar(p);
	}

	/**
	 * Remocao de uma {@link Permissao} pelo id.
	 * 
	 * Responsavel por consultar uma {@link Permissao} pelo seu id no banco de
	 * dados, retornando o objeto com os dados de uma {@link Permissao} preenchidos
	 * e, posteriormente, enviando ao {@link PermissaoDAO} para ser removido do
	 * banco de dados.
	 * 
	 * @param idPermissao Integer
	 */
	public void deletarPermissao(Integer idPermissao) {
		Permissao p = consultarPermissao(idPermissao);
		PermissaoDAO.getInstance().deletar(p);
	}

	/**
	 * Consulta de {@link Permissao} pelo idPermissao
	 * 
	 * Sera feita uma consulta da {@link Permissao} no banco de dados atraves de seu
	 * idPermissao, o qual retornara o objeto completo.
	 * 
	 * @param idPermissao Integer
	 * @return Permissao
	 */
	public Permissao consultarPermissao(Integer idPermissao) {
		return PermissaoDAO.getInstance().consultarPorId(idPermissao);
	}

	/**
	 * Consulta de {@link Permissao} pelo nomePermissao.
	 * 
	 * Sera feita uma consulta da {@link Permissao} no banco de dados atraves de seu
	 * nomePermissao, o qual retornara o objeto completo.
	 * 
	 * @param nomePermissao String
	 * @return Permissao
	 */
	public Permissao consultarPermissao(String nomePermissao) {
		return PermissaoDAO.getInstance().consultarPorNome(nomePermissao);
	}

	/**
	 * Lista todas as {@link Permissao}.
	 * 
	 * Sera feita uma consulta de todas os {@link Permissao} registrados no banco de
	 * dados. Caso haja {@link Permissao} registradas no banco de dados, eles serao
	 * retornados.
	 * 
	 * @return List<Permissao>
	 */
	public List<Permissao> listarTodasAsPermissoes() {
		return PermissaoDAO.getInstance().listar();
	}

	/**
	 * Deleta todos os registros da tabela {@link Permissao}.
	 */
	public void deletarTodos() {
		PermissaoDAO.getInstance().deletarTodos();
	}

}
