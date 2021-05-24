package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import model.dao.PerfilDAO;
import model.dto.PerfilDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilController {

	private static PerfilController instance;

	public PerfilController() {
	}

	public static PerfilController getInstance() {
		if (instance == null) {
			instance = new PerfilController();
		}
		return instance;
	}

	/**
	 * Criacao de um {@link Perfil}.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Perfil} com o atributo
	 * nomePerfil. O objeto {@link Perfil} e enviado ao {@link PerfilDAO} para ser
	 * persistido no banco de dados.
	 * 
	 * @param String - nomePerfil
	 */
	public void criarPerfil(String nomePerfil) {
		Perfil perfil = new Perfil(nomePerfil);
		PerfilDAO.getInstance().criar(perfil);
	}

	/**
	 * Alteracao de um {@link Perfil}.
	 * 
	 * Responsavel por alterar um {@link Perfil} pre existente com os atributos
	 * idPerfil e nomePerfil. A busca eh realizada pelo idPerfil por intermedio do
	 * PerfilDAO,o nome do perfil e adicionado ao objeto e alterado no banco de
	 * dados.
	 * 
	 * @param Integer idPerfil
	 * @param String  nomePerfil
	 */
	public void alterarPerfil(Integer idPerfil, String nomePerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		perfil.setNomePerfil(nomePerfil);
		PerfilDAO.getInstance().alterar(perfil);
	}

	/**
	  * Remocao de um {@link Perfil} pelo id.
     *
	 * Responsavel por deletar um objeto do tipo {@link Perfil} com o atributo idPerfil.
	 * O objeto {@link Perfil} eh enviado ao {@link PerfilDAO} para ser removido no banco 
	 * de dados.
	 * 
	 * @param idPerfil - Integer
	 */
	public void deletarPerfil(Integer idPerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		PerfilDAO.getInstance().deletar(perfil);
	}

	/**
	 * Consulta de {@link Perfil} pelo id.
	 * 
	 * Responsavel por consultar um {@link Perfil} existente com o 
	 * idPerfil. A busca e realizada pelo idPerfil por intermedio do
	 * {@link PerfilDAO}.
	 * 
	 * @param Integer.
	 * @return Perfil.
	 */
	public Perfil consultarPerfil(Integer idPerfil) {
		return PerfilDAO.getInstance().consultarPorId(idPerfil);
	}

	/**
	 * Consulta um {@link Perfil} pelo nome.
	 *  
	 * Tem a funcao de consultar por nome um objeto do tipo {@link Perfil} com o atributo
	 * nomePerfil. O objeto {@link Perfil} vai ser consultado no banco de dados pelo nome.
	 * 
	 * @param nome.
	 * @return {@link PerfilDTO}. 
	 */
	public Perfil consultarPerfil(String nome) {
		try {
			return PerfilDAO.getInstance().consultarPorNome(nome);
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * Consulta todos {@link Perfil} no banco de dados.
	 * 
	 * Recebe uma lista com todos os {@link Perfil} existentes no banco atraves do {@link PerfilDAO}.
	 * Caso não haja nenhum perfil no banco, será retornado null.
	 * 
	 * @return resultado ArrayList<Perfil>
	 */
	public ArrayList<Perfil> listarTodosOsPerfis() {
		ArrayList<Perfil> perfisEncontrados = (ArrayList<Perfil>) PerfilDAO.getInstance().listar();
		ArrayList<Perfil> resultado = !perfisEncontrados.isEmpty() ? perfisEncontrados : null;
		return resultado;
	}

	/**
	 * Verifica se um {@link Perfil} possui uma {@link Permissao}.
	 * 
	 * Vai ser feita uma consulta no banco de dados pelo ID do perfil para saber se o
	 * {@link Perfil} tem {@link Permissao}.
	 * 
	 * @param perfil.
	 * @param permissao.
	 * @return boolean.
	 */
	public boolean possuiPermissoes(Perfil perfil, Permissao permissao) {
		List<Permissao> listaDePermissoesDessePerfil = listarPermissoesDeUmPerfil(perfil.getIdPerfil());
		if (listaDePermissoesDessePerfil.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Lista todas as {@link Permissao} de um {@link Perfil}.
	 * 
	 * O metodo ira pesquisar todas as {@link Permissao} que o {@link Perfil} possui,
	 * fazendo uma busca pelo idPerfil, o qual sera enviado ao {@link PerfilDAO}.
	 * O retorno sera todas as {@link Permissao} que um {@link Perfil} possui.
	 * 
	 * @param idPerfil
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoesDeUmPerfil(int idPerfil) {
		return PerfilDAO.getInstance().listarPermissoesDeUmPerfil(idPerfil);
	}

	/**
	 * Atribui uma {@link Permissao} a um {@link Perfil}.
	 * 
	 * Eh atribuida uma {@link Permissao} a um {@link Perfil} quando eh passada
	 * uma {@link Permissao} para aquele {@link Perfil}.
	 * 
	 * @param permissao Permissao
	 * @param perfil Perfil
	 */
	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	/**
	 * Deleta todos os registros da tabela {@link Perfil}.
	 */
	public void deletarTodos() {
		PerfilDAO.getInstance().deletarTodos();
	}

}
