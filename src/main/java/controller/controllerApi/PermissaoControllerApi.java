package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PermissaoController;
import model.dao.PermissaoDAO;
import model.dto.PermissaoDTO;
import model.entidades.Permissao;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
@RestController
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
	@PostMapping("/criar")
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
	 * @param id            - Integer Identificador da permissao a ser alterada.
	 * @param nomePermissao - String Novo nome da permissao
	 */
	@PutMapping("/alterar/{id}")
	public void alterarPermissao(@PathVariable Integer id, String nomePermissao) {
		controller.alterarPermissao(id, nomePermissao);
	}

	/**
	 * Remocao de uma {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Responsavel por deletar uma {@link Permissao} pelo seu id.
	 * 
	 * @param id Integer Id da permissao a ser deletada.
	 */
	@DeleteMapping("/deletar/{id}")
	public void deletarPermissao(@PathVariable Integer id) {
		controller.deletarPermissao(id);
	}

	/**
	 * Consulta um {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Consulta uma {@link Permissao} no banco de dados atraves de seu id e retorna
	 * o objeto completo.
	 * 
	 * @param id Integer Id do objeto a ser consultado.
	 * @return Permissao
	 */
	@GetMapping("/consultarPermissaoPorId/{id}")
	public PermissaoDTO consultarPermissaoPorId(@PathVariable Integer id) {
		return new PermissaoDTO(controller.consultarPermissaoPorId(id));
	}

	/**
	 * Consulta de {@link Permissao} pelo nome.
	 * 
	 * <p>
	 * Consulta uma {@link Permissao} no banco de dados atraves de seu nome e
	 * retorna o objeto completo.
	 * 
	 * @param nome String Nome do objeto a ser consultado.
	 * @return Permissao
	 */
	@GetMapping("/consultarPermissaoPorNome/{nome}")
	public PermissaoDTO consultarPermissaoPorNome(@PathVariable String nome) {
		return new PermissaoDTO(controller.consultarPermissaoPorNome(nome));
	}

	/**
	 * Retorna todas os registros de {@link Permissao}.
	 * 
	 * <p>
	 * Consulta todos os registros e retorna uma lista.
	 * 
	 * @return List<Permissao>
	 */
	@GetMapping("/listarTodasAsPermissoes")
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
	@DeleteMapping("/deletarTodos")
	public void deletarTodos() {
		controller.deletarTodos();
	}
}
