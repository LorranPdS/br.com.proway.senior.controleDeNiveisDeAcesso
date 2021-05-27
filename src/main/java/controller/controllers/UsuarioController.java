package controller.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;
import utils.Email;
import utils.HashSenha;

public class UsuarioController {

	static UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	static PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	private static UsuarioController instance;

	public UsuarioController() {
	}

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
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

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		if (usuario == null) {
			return false;
		} else {
			if (senha.equals(usuario.getHashSenha()) && usuario.getLogin().equals(login)) {
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
	public void criarUsuario(Usuario usuario) {
		UsuarioDAO.getInstance().criar(usuario);
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
	 * @param Usuario usuarioNovo
	 */
	@SuppressWarnings("static-access")
	public boolean alterarUsuario(Integer id, String login, String senha) {
		if (usuarioDAO.getInstance().consultarPorId(id) == null) {
			return false;
		}
		Usuario usuario = usuarioDAO.getInstance().consultarPorId(id);
		usuario.setLogin(login);
		if (usuarioDAO.getInstance().consultarPorId(id).getHashSenha().equals(senha)) {
			UsuarioDAO.getInstance().alterar(usuario);
		} else {
			usuario.setHashSenha(HashSenha.criptografarSenha(login, senha));
			usuario.setUltimaAlteracaoSenha(LocalDate.now());
			UsuarioDAO.getInstance().alterar(usuario);
		}
		UsuarioDAO.getInstance().alterar(usuario);
		return true;
	}

	/**
	 * Consulta de {@link Usuario} pelo id
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu
	 * id, o qual retornara o objeto completo.
	 * 
	 * @param Integer - idUsuario
	 * @throws NullPointerException - Caso nao exista o usuario no banco de dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(Integer idUsuario) {
		try {
			return UsuarioDAO.getInstance().consultarPorId(idUsuario);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Consulta de {@link Usuario} pelo login.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu
	 * nome, o qual retornara o objeto completo.
	 * 
	 * @param String
	 * @throws NullPointerException caso nao exista o {@link Usuario} no banco de
	 *                              dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(String login) {
		try {
			return UsuarioDAO.getInstance().consultarPorLogin(login);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Lista todos os {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todos os {@link Usuario} registrados no banco de
	 * dados. Caso haja {@link Usuario} registrados no banco de dados, eles serao
	 * retornados, caso contrario, sera retornado null.
	 * 
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listarTodosOsUsuarios() {
		ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) UsuarioDAO.getInstance().listar();
		ArrayList<Usuario> resultado = !usuariosEncontrados.isEmpty() ? usuariosEncontrados : null;
		return resultado;
	}

	// provavel alteracao
	/**
	 * Lista todos os {@link Perfil} do {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todos os {@link Perfil} registrados no banco de
	 * dados. Caso haja {@link Perfil} registrados no banco de dados, eles serÃ£o
	 * retornados, caso contrario, sera retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Perfil>
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		List<Perfil> listaPerfis = controller.listarPerfisDeUmUsuario(idUsuario);
		List<Perfil> resultado = !listaPerfis.isEmpty() ? listaPerfis : null;
		return resultado;
	}

	// provavel alteracao
	/**
	 * Vai ser feita uma consulta no banco de dados pelo ID do usuario para saber se
	 * o {@link Usuario} tem permissoes. Acessando o metodo da classe
	 * {@link PerfilDeUsuarioController}.
	 * 
	 * @param idUsuario   int
	 * @param idPermissao int
	 * @return boolean
	 */
	public boolean possuiPermissoes(int idUsuario, int idPermissao) {
		return controller.usuarioPossuiPermissaoPara(idUsuario, idPermissao);
	}

	/**
	 * Lista todas as {@link Permissao} do {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todas as {@link Permissao} registradas no banco de
	 * dados. Caso haja {@link Permissao} registradas no banco de dados, elas serao
	 * retornadas, caso contrario, sera retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Permissao> resultado = new ArrayList<Permissao>();
		List<Permissao> listaPermissao = new ArrayList<Permissao>();
		for (Permissao permissoes : controller.listarPermissoesDeUmUsuario(idUsuario)) {
			listaPermissao.add(permissoes);
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

	/**
	 * Retorna todas as {@link Perfil} ativos de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario} que possua ativo igual a true.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	public List<Perfil> listarPerfisAtivosDeUmUsuario(int idUsuario) {
		return controller.listarPerfisAtivosDeUmUsuario(idUsuario);
	}
}
