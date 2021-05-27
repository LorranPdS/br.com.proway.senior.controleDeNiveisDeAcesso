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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PerfilController;
import controller.controllers.PermissaoController;
import javassist.NotFoundException;
import model.dao.PerfilDAO;
import model.dto.PerfilDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;

/**
 * Classe responsavel por filtrar o acesso do usuario em relacao as informacoes
 * que o mesmo poderá ter acesso. Faz a filtragem atraves do {@link PerfilDTO}.
 * 
 * @author Bruna Carvalho <sh4323202@gmail.com>
 * @author Daniella Lira <dev.danilira@gmail.com>
 * @author Janaina Mai <janainamai@hotmail.com>
 * @author Lorran Santos <lorransantospereira@yahoo.com.br>
 * @author Marcelo schaefer <marceloschaeferfilho@gmail.com>
 */

@RestController
@RequestMapping("/perfil")
public class PerfilControllerApi {

	PerfilController controllerPerfil = new PerfilController();
	PermissaoController controllerPermissao = new PermissaoController();

	/**
	 * Criacao de um {@link Perfil}.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Perfil} com o atributo
	 * nomePerfil. O objeto {@link Perfil} e enviado ao {@link PerfilDAO} para ser
	 * persistido no banco de dados.
	 * 
	 * @param String - nomePerfil
	 */

	@PostMapping("/criar")
	public boolean criarPerfil(@RequestBody Perfil perfil) {
		controllerPerfil.criarPerfil(perfil.getNomePerfil());
		return true;
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
	@PutMapping("/alterarPerfil/id/{idPerfil}")
		public boolean alterarPerfil(@PathVariable("idPerfil") Integer idPerfil, @RequestBody Perfil perfil) {
			 controllerPerfil.alterarPerfil(idPerfil, perfil.getNomePerfil());
			 return true;
	}

	/**
	 * Remocao de um {@link Perfil} pelo id.
	 *
	 * Responsavel por deletar um objeto do tipo {@link Perfil} com o atributo
	 * idPerfil. O objeto {@link Perfil} eh enviado ao {@link PerfilDAO} para ser
	 * removido no banco de dados.
	 * 
	 * @param idPerfil - Integer
	 */
	@DeleteMapping("/deletar/id/{idPerfil}")
	public boolean deletarPerfil(@PathVariable("idPerfil") Integer idPerfil) {
		controllerPerfil.deletarPerfil(idPerfil);
		return true;
	}

	/**
	 * Consulta de {@link Perfil} pelo id.
	 * 
	 * Responsavel por consultar um {@link Perfil} existente com o 
	 * idPerfil. A busca e realizada pelo idPerfil por intermedio do
	 * {@link PerfilDAO}.
	 * 
	 * @param Integer 
	 * @throws NullPointerException Caso nao exista o {@link Perfil} no banco de dados.
	 * @return Perfil
	 */
	@GetMapping("/consultarPorId/{idPerfil}")
	public ResponseEntity<PerfilDTO> consultarPerfilPorId(@PathVariable("idPerfil") Integer idPerfil) 
		throws NotFoundException  {
		
			Perfil perfil = controllerPerfil.consultarPerfil(idPerfil);
			if (perfil == null) {
				return new ResponseEntity<PerfilDTO>(HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(new PerfilDTO(perfil));
		}
	

	/**
	 * Consulta um {@link Perfil} pelo nome.
	 * 
	 * Tem a funcao de consultar por nome um objeto do tipo {@link Perfil} com o
	 * atributo nomePerfil. O objeto {@link Perfil} vai ser consultado no banco de
	 * dados pelo nome.
	 * 
	 * @param nome.
	 * @exception NullPointerException (Retorna caso o resultado ser nulo).
	 * @return {@link PerfilDTO}.
	 */
	@GetMapping("/consultarPorNome/{nome}")
	public PerfilDTO consultarPerfil(@PathVariable("nome") String nome) {
		try {
			return new PerfilDTO(controllerPerfil.consultarPerfil(nome));
		} catch (NullPointerException e) {
			return null;
		}

	}

	/**
	 * Consulta todos {@link Perfil} no banco de dados.
	 * 
	 * Recebe uma lista com todos os {@link Perfil} existentes no banco, atraves do
	 * {@link PerfilDAO}. Atribui esta lista a uma outra lista do tipo
	 * {@link PerfilDTO} e em seguida popula esta lista com os perfis encontrados.
	 * Caso não haja nenhum perfil no banco, será retornado null.
	 * 
	 * @return resultado - ArrayList<Perfil>
	 */
	@GetMapping("/listarTodosOsPerfis")
	public ArrayList<PerfilDTO> listarTodosOsPerfis() {
		ArrayList<Perfil> perfisEncontrados = (ArrayList<Perfil>) PerfilDAO.getInstance().listar();

		if (perfisEncontrados == null) {
			return null;
		}

		ArrayList<PerfilDTO> listaDto = new ArrayList<PerfilDTO>();

		for (Perfil perfis : perfisEncontrados)
			listaDto.add(new PerfilDTO(perfis));

		return listaDto;

	}

	/**
	 * Verifica se um {@link Perfil} possui uma {@link Permissao}.
	 * 
	 * Vai ser feita uma consulta no banco de dados pelo ID do perfil para saber se
	 * o {@link Perfil} tem {@link Permissao}.
	 * 
	 * @param perfil.
	 * @param permissao.
	 * @return boolean.
	 */
	@GetMapping("/consultarPermissoes")
	public boolean possuiPermissoes(@RequestBody Perfil perfil, @RequestBody Permissao permissao) {
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
	 * O metodo ira pesquisar todas as {@link Permissao} que o {@link Perfil}
	 * possui, fazendo uma busca pelo idPerfil, o qual sera enviado ao
	 * {@link PerfilDAO}. O retorno sera todas as {@link Permissao} que um
	 * {@link Perfil} possui.
	 * 
	 * @param idPerfil
	 * @return List<Permissao>
	 */
	@GetMapping("/listaDePermissoes/{idPerfil}")
	public List<Permissao> listarPermissoesDeUmPerfil(@PathVariable("idPerfil") int idPerfil) {
		return PerfilDAO.getInstance().listarPermissoesDeUmPerfil(idPerfil);
	}

	/**
	 * Atribuir {@link Permissao} a um {@link Perfil}.
	 * 
	 * Eh atribuida uma {@link Permissao} a um {@link Perfil} quando eh passada uma
	 * {@link Permissao} para aquele {@link Perfil}.
	 * 
	 * @param permissao Permissao
	 * @param perfil    Perfil
	 */
	@PostMapping("/atribuirPermissaoAUmPerfil/{idPerfil}/{idPermissao}")
	public boolean atribuirPermissaoAUmPerfil(@PathVariable("idPerfil") Integer idPerfil, @PathVariable("idPermissao") Integer idPermissao ) {
		
		Perfil perfil = controllerPerfil.consultarPerfil(idPerfil);
		Permissao permissao = controllerPermissao.consultarPermissaoPorId(idPermissao);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	return true;
	}

	/**
	 * Deleta todos os registros da tabela {@link Perfil}.
	 */
	@GetMapping("/deletaTodosOsPerfis")
	public void deletarTodos() {
		PerfilDAO.getInstance().deletarTodos();
	}

}
