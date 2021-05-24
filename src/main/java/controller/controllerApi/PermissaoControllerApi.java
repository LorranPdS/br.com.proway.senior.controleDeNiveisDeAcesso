package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;

import controller.PermissaoController;
import model.dao.PermissaoDAO;
import model.dto.PermissaoDTO;
import model.entidades.Permissao;

public class PermissaoControllerApi {
	PermissaoController controller = new PermissaoController();

	/**
	 * Cadastra uma {@link Permissao} no banco de dados.
	 * 
	 * <p>
	 * Responsavel por criar um objeto do tipo {@link Permissao}. O objeto
	 * {@link Permissao} e enviado ao {@link PermissaoController} para ser
	 * persistido no banco de dados.
	 * 
	 * @param nomePermissao String Nome da permissao.
	 */
	public void criarPermissao(String nomePermissao) {
		controller.criarPermissao(nomePermissao);
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
	 * @param idPermissao   - Integer Identificador da permissao a ser alterada.
	 * @param nomePermissao - String Novo nome da permissao
	 */
	public void alterarPermissao(Integer idPermissao, String nomePermissao) {
		controller.alterarPermissao(idPermissao, nomePermissao);
	}

	/**
	 * Remocao de uma {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Responsavel por deletar uma {@link Permissao} pelo seu id.
	 * 
	 * @param idPermissao Integer Id da permissao a ser deletada.
	 */
	public void deletarPermissao(Integer idPermissao) {
		controller.deletarPermissao(idPermissao);
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
	public PermissaoDTO consultarPermissaoPorId(Integer idPermissao) {
		return new PermissaoDTO(controller.consultarPermissaoPorId(idPermissao));
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
	public PermissaoDTO consultarPermissaoPorNome(String nomePermissao) {
		return new PermissaoDTO(controller.consultarPermissaoPorNome(nomePermissao));
	}

	/**
	 * Retorna todas os registros de {@link Permissao}.
	 * 
	 * <p>
	 * Consulta todos os registros e retorna uma lista.
	 * 
	 * @return List<Permissao>
	 */
	public List<PermissaoDTO> listarTodasAsPermissoes() {
		List<Permissao> listaModel = controller.listarTodasAsPermissoes();
		List<PermissaoDTO> listaDTO = new ArrayList<PermissaoDTO>();
		for (Permissao permissao : listaModel) {
			listaDTO.add(new PermissaoDTO(permissao));
		}
		return listaDTO;
	}

	/**
	 * Deleta todos os registros da tabela {@link Permissao}.
	 */
	public void deletarTodos() {
		controller.deletarTodos();
	}
}
