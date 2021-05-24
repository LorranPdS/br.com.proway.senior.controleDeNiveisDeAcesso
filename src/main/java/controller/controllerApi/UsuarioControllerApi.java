package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;

import controller.UsuarioController;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.UsuarioDAO;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class UsuarioControllerApi {

	private static UsuarioControllerApi instance;

	UsuarioControllerApi() {
	}

	public static UsuarioControllerApi getInstance() {
		if (instance == null) {
			instance = new UsuarioControllerApi();
		}
		return instance;
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
	public void alterarUsuario(Integer idUsuario, String login, String senha) {
		UsuarioController.getInstance().alterarUsuario(idUsuario, login, senha);
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
	public UsuarioDTO consultarUsuarioPorId(Integer idUsuario) {
		try {
			return new UsuarioDTO(UsuarioDAO.getInstance().consultarPorId(idUsuario));
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
	public List<PerfilDTO> listarPerfisDeUmUsuario(int idUsuario) {
		List<Perfil> listaPerfis = PerfilDeUsuarioDAO.getInstance().listarPerfisDeUmUsuario(idUsuario);
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
	public List<PermissaoDTO> listarPermissoesDeUmUsuario(Integer idUsuario) {
		List<PermissaoDTO> resultado = new ArrayList<>();
		List<PermissaoDTO> listaPermissao = new ArrayList<>();
		for (Permissao permissoes : PerfilDeUsuarioDAO.getInstance().listarPermissoesDeUmUsuario(idUsuario)) {
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
	public void deletarTodos() {
		UsuarioDAO.getInstance().deletarTodos();
	}

}
