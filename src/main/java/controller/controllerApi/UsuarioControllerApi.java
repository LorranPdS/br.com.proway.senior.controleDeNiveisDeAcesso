package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.UsuarioController;
import model.dao.UsuarioDAO;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

@RestController
public class UsuarioControllerApi {

	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();

	public UsuarioControllerApi() {
	}


	/**
	 * Autentica uma tentativa de login de um usuario do sistema.
	 * 
	 * Um usuario entra com um login e senha. A senha deve ser criptografada antes
	 * de comparada com a salva no banco. Esse metodo NAO envia confirmacao de login
	 * 2FA. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param login
	 * @param senha
	 */
	@GetMapping("/logar")
	public boolean logar(String login, String senha) {
		return UsuarioController.getInstance().logar(login, senha);

	}

	/**
	 * Envia um e-mail
	 * 
	 * Envia um e-mail para o usuario com um codigo aleatorio gerado para a
	 * confirmacao de um login. O ultimo codigo enviado e salvo no banco de dados
	 * para futura confirmacao. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   Codigo aleatorio gerado pelo sistema
	 * @throws Exception
	 */
	@PostMapping("/enviarEmailDeConfirmacaoDeLogin/")
	public boolean enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {
		return UsuarioController.getInstance().enviarEmailDeConfirmacaoDeLogin(emailDoDestinario);
	}

	/**
	 * Confirma codigo 2FA.
	 * 
	 * Um usuario entra com um login e codigo de confirmacao (previamente recebido
	 * em seu email). O sistema verifica se o codigo bate com o salvo no banco de
	 * dados. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param login.
	 * @param codigoDeConfirmacao
	 */
	@GetMapping("/confirmarCodigoDeConfirmacao/{login}")
	public boolean confirmarCodigoDeConfirmacao(String login, Integer codigoDeConfirmacao) {
		return UsuarioController.getInstance().confirmarCodigoDeConfirmacao(login, codigoDeConfirmacao);
	}

	/**
	 * Criacao de um {@link Usuario} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Usuario} com os atributos
	 * login e senha. O objeto {@link Usuario} e enviado ao {@link UsuarioDAO} para
	 * ser persistido no banco de dados. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param login
	 * @param senha
	 */
	@PostMapping("/criarUsuario")
	public void criarUsuario(String login, String senha) {
		UsuarioController.getInstance().criarUsuario(login, senha);
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
	@DeleteMapping("/deletarUsuario/{id}")
	public void deletarUsuario(Integer id) {
		UsuarioController.getInstance().deletarUsuario(id);
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
	@PutMapping("alterarUsuario/{id}")
	public void alterarUsuario(Integer id, String login, String senha) {
		UsuarioController.getInstance().alterarUsuario(id, login, senha);
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
	@GetMapping("/consultarUsuarioPorId/{id}")
	public UsuarioDTO consultarUsuarioPorId(Integer id) {
		try {
			return new UsuarioDTO(UsuarioDAO.getInstance().consultarPorId(id));
		} catch (NullPointerException e) {
			return null;
		}
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
	@GetMapping("/consultarUsuario/{login}")
	public UsuarioDTO consultarUsuario(String login) {
		try {
			return new UsuarioDTO(UsuarioDAO.getInstance().consultarPorLogin(login));
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
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
	@GetMapping("/listarTodosOsUsuario")
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
	@GetMapping("/listarPerfisDeUmUsuario/{id}")
	public List<PerfilDTO> listarPerfisDeUmUsuario(int id) {
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
	 * o {@link Usuario} tem permissoes. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param usuario   - Usuario
	 * @param permissao - Permissao
	 * @return ArrayList<Permissao>
	 */
	@GetMapping("/possuiPermissoes")
	public boolean possuiPermissoes(Usuario usuario, Permissao permissao) {
		return UsuarioController.getInstance().possuiPermissoes(usuario, permissao);
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
	@GetMapping("/listarPermissoesDeUmUsuario/{id}")
	public List<PermissaoDTO> listarPermissoesDeUmUsuario(Integer id) {
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

	/**
	 * Deleta todos os registros da tabela {@link Usuario}.
	 */
	@DeleteMapping("deletarTodos")
	public void deletarTodos() {
		UsuarioDAO.getInstance().deletarTodos();
	}
	
	/**
	 * Valida a ligacao verificando se a mesma esta ativa e possui data de expiracao
	 * posterior a data atual.
	 * 
	 * @param ligacao PerfilDeUsuario Ligacao entre usuario e perfil a ser validada.
	 * @return True caso a ligacao esteja ativa e com data posterior a data atual.
	 */
	@GetMapping("/permissaoAtiva")
	public boolean permissaoAtiva(PerfilDeUsuario ligacao) {
		return controller.permissaoAtiva(ligacao);
	}
	
	/**
	 * Retorna todas as {@link Perfil} ativos de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario} que possua ativo igual a true.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	@GetMapping("/listarPerfisAtivosDeUmUsuario/{id}")
	public List<PerfilDTO> listarPerfisAtivosDeUmUsuario(int id) {
		List<Perfil> listaModel = controller.listarPerfisDeUmUsuario(id);
		ArrayList<PerfilDTO> listaDTO = new ArrayList<PerfilDTO>();
		for (Perfil perfil : listaModel) {
			listaDTO.add(new PerfilDTO(perfil));
		}
		return listaDTO;
	}
	
}
