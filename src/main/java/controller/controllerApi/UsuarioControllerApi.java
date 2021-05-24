package controller.controllerApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.dao.PerfilDeUsuarioDAO;
import model.dao.UsuarioDAO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;
import utils.Email;
import utils.HashSenha;

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

	private boolean verificarPermissao() {
		return true;
	}

	/**
	 * Autentica uma tentativa de login de um usuario do sistema.
	 * 
	 * Um usuario entra com um login e senha. A senha deve ser criptografada antes
	 * de comparada com a salva no banco. Esse metodo NAO envia confirmacao de login
	 * 2FA.
	 * 
	 * @param login
	 * @param senha
	 */
	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(login, senha);

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		if (usuario == null) {
			return false;
		} else {

			String senhaBanco = usuario.getHashSenha();

			if (senhaCriptografada.equals(senhaBanco) && usuario.getLogin().equals(login)) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * Envia um e-mail
	 * 
	 * Envia um e-mail para o usuario com um codigo aleatorio gerado para a
	 * confirmacao de um login. O ultimo codigo enviado e salvo no banco de dados
	 * para futura confirmacao.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   Codigo aleatorio gerado pelo sistema
	 * @throws Exception
	 */
	public boolean enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {
		Usuario u = UsuarioDAO.getInstance().consultarPorLogin(emailDoDestinario);
		Integer codigoDeConfirmacao = gerarCodigoDeConfirmacao();
		u.setUltimoCodigo2FA(codigoDeConfirmacao);
		UsuarioDAO.getInstance().alterar(u);

		String nomeDoRemetente = "Grupo 3";
		String assuntoDoEmail = "2FA Niveis de Acesso";
		String corpoDoEmail = "Seu codigo de confirmacao: " + codigoDeConfirmacao.toString();

		Email email = new Email(emailDoDestinario, nomeDoRemetente, assuntoDoEmail, corpoDoEmail);

		return (email.enviarEmail()) ? true : false;
	}

	/**
	 * Gera um codigo aleatorio
	 * 
	 * Gera o codigo random para a autenticacao 2FA de um login de usuario
	 * 
	 * @return codigo de 5 digitos
	 */
	private Integer gerarCodigoDeConfirmacao() {
		Random random = new Random();
		int codigo = random.nextInt(99999);
		if (codigo <= 10000) {
			codigo += 10000;
		}
		return codigo;
	}

	/**
	 * Confirma codigo 2FA.
	 * 
	 * Um usuario entra com um login e codigo de confirmacao (previamente recebido
	 * em seu email). O sistema verifica se o codigo bate com o salvo no banco de
	 * dados.
	 * 
	 * @param login.
	 * @param codigoDeConfirmacao
	 */
	public boolean confirmarCodigoDeConfirmacao(String login, Integer codigoDeConfirmacao) {
		if (UsuarioDAO.getInstance().verificarCodigoDeConfirmacao(login, codigoDeConfirmacao) != null)
			return true;
		else
			return false;
	}

	/**
	 * Criacao de um {@link Usuario} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Usuario} com os atributos
	 * login e senha. O objeto {@link Usuario} e enviado ao {@link UsuarioDAO} para
	 * ser persistido no banco de dados.
	 * 
	 * @param login
	 * @param senha
	 */
	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	/**
	 * Remocao de um {@link Usuario} pelo id.
	 * 
	 * Responsavel por consultar um {@link Usuario} pelo seu id no banco de dados,
	 * retornando o objeto com os dados do {@link Usuario} preenchidos e,
	 * posteriormente, enviando ao {@link UsuarioDAO} para ser removido do banco de
	 * dados.
	 * 
	 * @param Integer - id
	 */
	public void deletarUsuario(Integer id) {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(id);
		UsuarioDAO.getInstance().deletar(usuario);
	}

	/**
	 * Alteracao de um {@link Usuario}.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves do id, o
	 * qual retornara o objeto completo. Feito isso, o login e a senha sera setados
	 * ao objeto e enviado ao {@link UsuarioDAO} para ser atualizado no banco de
	 * dados.
	 * 
	 * @param Integer - idUsuario
	 * @param String  - login
	 * @param String  - senha
	 */
	public void alterarUsuario(Integer idUsuario, String login, String senha) {
		Usuario usuario = consultarUsuario(idUsuario);
		usuario.setLogin(login);
		usuario.setHashSenha(HashSenha.criptografarSenha(login, senha));
		UsuarioDAO.getInstance().alterar(usuario);
	}

	/**
	 * Consulta de {@link Usuario} pelo id
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu
	 * id, o qual retornara o objeto completo.
	 * 
	 * @param Integer - idUsuario
	 * @return Usuario
	 */
	private Usuario consultarUsuario(Integer idUsuario) {
		try {
			return UsuarioDAO.getInstance().consultarPorId(idUsuario);
		} catch (NullPointerException e) {
			return null;
		}
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
	 * retornados ja filtrado pela classe {@link UsuarioDTO}, caso contrario, sera retornado null.
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
	 * retornadosn filtrados pela classe {@link PerfilDTO}, caso contrario, sera retornado null.
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
	 * o {@link Usuario} tem permissoes.
	 * 
	 * @param usuario   - Usuario
	 * @param permissao - Permissao
	 * @return ArrayList<Permissao>
	 */
	public boolean possuiPermissoes(Usuario usuario, Permissao permissao) {
		ArrayList<Permissao> listaDePermissoesDesseUsuario = new ArrayList<>();
		for (Permissao permissoes : listarPermissoesDeUmUsuario(usuario.getIdUsuario())) {
			listaDePermissoesDesseUsuario.add(permissoes);
		}
		if (listaDePermissoesDesseUsuario.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Lista todas as {@link PermissaoDTO} do {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todas as {@link Permissao} registradas no banco de
	 * dados. Caso haja {@link Permissao} registradas no banco de dados, elas serao
	 * retornadas ja filtradas pela classe {@link PermissaoDTO}, caso contrario, sera retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<PermissaoDTO>
	 */
	public List<PermissaoDTO> listarPermissoesDeUmUsuario(Integer idUsuario) {
		List<PermissaoDTO> resultado = new ArrayList<Permissao>();
		List<PermissaoDTO> listaPermissao = new ArrayList<Permissao>();
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
