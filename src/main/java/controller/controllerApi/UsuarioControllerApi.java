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

import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.UsuarioController;
import model.dao.UsuarioDAO;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioControllerApi {

	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	UsuarioController UController = new UsuarioController();

	public UsuarioControllerApi() {
	}

	/**
	 * Criacao de um {@link Usuario} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Usuario} com os atributos
	 * login e senha. O objeto {@link Usuario} e enviado ao {@link UsuarioDAO} para
	 * ser persistido no banco de dados. Chamando o metodo respectivo da classe
	 * {@link UsuarioController}.
	 * 
	 * @param login
	 * @param senha
	 */
	@PostMapping("/criar")
	public boolean criarUsuario(@RequestBody Usuario usuario) {
		if (usuario != null) {
		Usuario usuarioHash = new Usuario(usuario.getLogin(), usuario.getHashSenha());
		UsuarioController.getInstance().criarUsuario(usuarioHash);
		return true;
		}
		return false;
	}

	/**
	 * Remocao de um {@link Usuario} pelo id.
	 * 
	 * Responsavel por consultar um {@link Usuario} pelo seu id no banco de dados,
	 * retornando o objeto com os dados do {@link Usuario} preenchidos e,
	 * posteriormente, enviando ao {@link UsuarioDAO} para ser removido do banco de
	 * dados. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param Integer - id
	 */
	@DeleteMapping("/deletar/id/{id}")
	public boolean deletarUsuario(@PathVariable("id") Integer id) {
		Usuario usuario = UController.consultarUsuario(id);
		if (usuario != null) {
			UsuarioController.getInstance().deletarUsuario(id);
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Alteracao de um {@link Usuario}.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves do id, o
	 * qual retornara o objeto completo. Feito isso, o login e a senha sera setados
	 * ao objeto e enviado ao {@link UsuarioDAO} para ser atualizado no banco de
	 * dados. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param Integer - idUsuario
	 * @param String  - login
	 * @param String  - senha
	 */
	@PutMapping("/alterar/id/{id}")
	public boolean alterarUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
			return UsuarioController.getInstance().alterarUsuario(id, usuario.getLogin(), usuario.getHashSenha());
	}

	/**
	 * Consulta de {@link UsuarioDTO} pelo id
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu
	 * id, o qual retornara o objeto filtrado pelo {@link UsuarioDTO}.
	 * 
	 * @param Integer - idUsuario
	 * @return UsuarioDTO
	 */
	@GetMapping("/consulta/id/{id}")
	public ResponseEntity<UsuarioDTO> consultarUsuarioPorId(@PathVariable("id") Integer id) {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(id);
		if (usuario == null) {
			return new ResponseEntity<UsuarioDTO>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new UsuarioDTO(UsuarioDAO.getInstance().consultarPorId(id)));
	
	}

	/**
	 * Consulta de {@link UsuarioDTO} pelo login.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu
	 * nome, o qual retornara o objeto filtrado pelo {@link UsuarioDTO}.
	 * 
	 * @param String
	 * @return UsuarioDTo
	 */
	@GetMapping("/login/{login}")
	public ResponseEntity<UsuarioDTO> consultarUsuario(@PathVariable("login") String login) {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		if (usuario == null) {
			return new ResponseEntity<UsuarioDTO>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(new UsuarioDTO(UsuarioDAO.getInstance().consultarPorLogin(login)));
	}

	/**
	 * Lista todos os {@link UsuarioDTO}.
	 * 
	 * Sera feita uma consulta de todos os {@link Usuario} registrados no banco de
	 * dados. Caso haja {@link Usuario} registrados no banco de dados, eles serao
	 * retornados ja filtrado pela classe {@link UsuarioDTO}, caso contrario, sera
	 * retornado null.
	 * 
	 * @return ArrayList<UsuarioDTO>
	 */
	@GetMapping("/lista")
	public ArrayList<UsuarioDTO> listarTodosOsUsuarios() {
		ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) UsuarioDAO.getInstance().listar();
		ArrayList<UsuarioDTO> resultado = new ArrayList<>();

		for (Usuario usuario : usuariosEncontrados) {
			resultado.add(new UsuarioDTO(usuario));
		}
		if (!resultado.isEmpty()) {
			return resultado;
		} else {
			resultado = null;
		}
		return resultado;
	}

	// provavel alteracao
	/**
	 * Lista todos os {@link PerfilDTO} do {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todos os {@link Perfil} registrados no banco de
	 * dados. Caso haja {@link Perfil} registrados no banco de dados, eles serao
	 * retornadosn filtrados pela classe {@link PerfilDTO}, caso contrario, sera
	 * retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<PerfilDTO>
	 */
	@GetMapping("/listar/perfil/usuario/id/{id}")
	public List<PerfilDTO> listarPerfisDeUmUsuario(@PathVariable("id") int id) {
		List<Perfil> listaPerfis = controller.listarPerfisDeUmUsuario(id);
		ArrayList<PerfilDTO> resultado = new ArrayList<>();

		for (Perfil usuario : listaPerfis) {
			resultado.add(new PerfilDTO(usuario));
		}

		if (!resultado.isEmpty()) {
			return resultado;
		} else {
			resultado = null;
		}
		return resultado;
	}

	// provavel alteracao
	/**
	 * Vai ser feita uma consulta no banco de dados pelo ID do usuario para saber se
	 * o {@link Usuario} tem permissoes. Chamando o metodo respectivo da classe
	 * {@link UsuarioController}.
	 * 
	 * @param idUsuario   int
	 * @param idPermissao int
	 * @return ArrayList<Permissao>
	 * @throws Exception 
	 */
	@GetMapping("/{idUsuario}/permissao/{idPermissao}")
	public boolean possuiPermissoes(@PathVariable("idUsuario") int idUsuario,
			@PathVariable("idPermissao") int idPermissao) throws Exception {
		return controller.usuarioPossuiPermissaoPara(idUsuario, idPermissao);
	}

	/**
	 * Lista todas as {@link PermissaoDTO} do {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todas as {@link Permissao} registradas no banco de
	 * dados. Caso haja {@link Permissao} registradas no banco de dados, elas serao
	 * retornadas ja filtradas pela classe {@link PermissaoDTO}, caso contrario,
	 * sera retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<PermissaoDTO>
	 */
	@GetMapping("/listar/permissao/usuario/id/{id}")
	public List<PermissaoDTO> listarPermissoesDeUmUsuario(@PathVariable("id") Integer id) {
		List<PermissaoDTO> resultado = new ArrayList<>();
		List<PermissaoDTO> listaPermissao = new ArrayList<>();
		for (Permissao permissoes : controller.listarPermissoesDeUmUsuario(id)) {
			listaPermissao.add(new PermissaoDTO(permissoes));
		}

		if (!listaPermissao.isEmpty()) {
			resultado = listaPermissao;
		} else {
			resultado = null;
		}
		return resultado;
	}

}
