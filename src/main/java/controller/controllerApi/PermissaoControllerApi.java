package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PermissaoController;
import javassist.NotFoundException;
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
	@PostMapping("/permissao/criar")
	public boolean criarPermissao(@RequestBody Permissao permissao) {
		return controller.criarPermissao(permissao.getNomePermissao());
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
	@PutMapping("/permissao/alterar/id/{id}")
	public boolean alterarPermissao(@PathVariable("id") Integer id, @RequestBody Permissao permissao) {
		return controller.alterarPermissao(id, permissao.getNomePermissao());
	}

	/**
	 * Remocao de uma {@link Permissao} pelo id.
	 * 
	 * <p>
	 * Responsavel por deletar uma {@link Permissao} pelo seu id.
	 * 
	 * @param id Integer Id da permissao a ser deletada.
	 */
	@DeleteMapping("/permissao/deletar/id/{id}")
	public boolean deletarPermissao(@PathVariable("id") Integer id) {
		return controller.deletarPermissao(id);
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
	 * @throws NotFoundException
	 */
	@GetMapping("/permissao/consultar/id/{id}")
	public ResponseEntity<PermissaoDTO> consultarPermissaoPorId(@PathVariable("id") Integer id)
			throws NotFoundException {
		Permissao permissao = controller.consultarPermissaoPorId(id);
		if (permissao == null) {
			return new ResponseEntity<PermissaoDTO>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new PermissaoDTO(permissao));
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
	@GetMapping("/permissao/consultar/nome/{nome}")
	public ResponseEntity<PermissaoDTO> consultarPermissaoPorNomeExato(@PathVariable("nome") String nome) {
		Permissao permissao = controller.consultarPermissaoPorNome(nome);
		if (permissao == null) {
			return new ResponseEntity<PermissaoDTO>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new PermissaoDTO(permissao));
	}

	/**
	 * Retorna todas os registros de {@link Permissao}.
	 * 
	 * <p>
	 * Consulta todos os registros e retorna uma lista.
	 * 
	 * @return List<Permissao>
	 */
	@GetMapping("/permissao/listar")
	public List<PermissaoDTO> listarTodasAsPermissoes() {
		List<Permissao> listaModel = controller.listarTodasAsPermissoes();
		List<PermissaoDTO> listaDTO = new ArrayList<PermissaoDTO>();
		for (Permissao permissao : listaModel) {
			listaDTO.add(new PermissaoDTO(permissao));
		}
		return listaDTO;
	}
}
