package controller;

import java.util.ArrayList;
import java.util.Random;

import model.acesso.Perfil;
import model.acesso.Permissao;
import model.acesso.PermissaoDAO;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import utils.Email;
import utils.HashSenha;

/**
 * Classe Controller
 * 
 * Classe respons�vel por intermediar os dados da View e Model
 * 
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Lucas Grij�, rksgrijo@gmail.com
 * @author Thiago, thiagoluizbarbieri@gmail.com
 */
public class Controller {

	static Controller instance;

	private Controller() {
	}

	/**
	 * 
	 * @return Controller
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(login, senha);

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		String senhaBanco = usuario.getHashSenha();

		if (senhaCriptografada.equals(senhaBanco)) {
			return true;
		}
		return false;
	}

	public boolean verificarPermissao(Usuario usuario, Permissao permissao) {
		return false;
	}

	public boolean verificarPermissao(Perfil perfil, Permissao permissao) {
		return false;
	}

	// DAO - Usuario

	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	public void deletarUsuario(Integer id) {
		// UsuarioDAO.getInstance().deletar(id);
	}

	public void alterarUsuario(Integer idUsuario, Usuario usuario) {

	}

	public Usuario consultarUsuario(Integer idUsuario) {
		return null;
	}

	public Usuario consultarUsuario(String login) {
		return UsuarioDAO.getInstance().consultarPorLogin(login);
	}

	public ArrayList<Usuario> listarTodosOsUsuarios() {
		return null;
	}

	public void listarPermissoesDeUmUsuario(Usuario usuario) {

	}

	public void listarPerfisDeUmUsuario(Usuario usuario) {

	}

	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil) {

	}

	// DAO - Perfil

	public void criarPerfil(String nomePerfil) {

	}

	public void alterarPerfil(Integer idPerfil, Perfil perfil) {

	}

	public void deletarPerfil(Integer idPerfil) {

	}

	public Perfil consultarPerfil(Integer idPerfil) {
		return null;
	}

	public Perfil consultarPerfil(String nome) {
		// TODO fazer esse neg�cio
		Perfil perfil = new Perfil();
		return perfil;
	}

	public ArrayList<Perfil> listarTodosOsPerfils() {
		return null;
	}

	public void listarPermissoesDeUmPerfil(Perfil perfil) {

	}

	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {

	}

	// DAO - Permissao

	public void criarPermissao(String nomePermissao) {
		Permissao permissao = new Permissao(nomePermissao);
		PermissaoDAO.getInstance().criar(permissao);
	}

	public void alterarPermissao(Integer idPermissao, Permissao permissao) {

	}

	public void deletarPermissao(Integer idPermissao) {

	}

	public Permissao consultarPermissao(Integer idPermissao) {
		return null;
	}

	/**
	 * Consultar� no banco de dados a permiss�o
	 * 
	 * @param nomePermissao
	 * @return Permissao
	 */
	public Permissao consultarPermissao(String nomePermissao) {
		return PermissaoDAO.getInstance().consultarPorNome(nomePermissao);
	}

	public ArrayList<Permissao> listarTodasAsPermissoes() {
		return null;
	}

	// ...

	// MISC

	/**
	 * Envia um e-mail
	 * 
	 * Envia o e-mail para o usuario com codigo aleatorio gerado para a
	 * confirmacao.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado Codigo aleatorio gerado pelo sistema
	 * @throws Exception
	 */
	public boolean enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {
		Email email = new Email(emailDoDestinario, "Grupo 3", "2FA Niveis de Acesso",
				"O seu c�digo �: " + gerarCodigo().toString());

		// ABSTRAIR MAIS ESSA L�GICA (usar condicional ternaria)
		boolean resultadoEnvio = email.enviarEmail();
		if (resultadoEnvio == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gera um codigo aleatorio
	 * 
	 * Gera o codigo random para a verificacao de usuario
	 * 
	 * @return codigo de 5 digitos
	 */
	private Integer gerarCodigo() {

		Random random = new Random();
		int codigo = random.nextInt(99999);
		if (codigo <= 10000) {
			codigo += 10000;
		}
		return codigo;
	}

	// ROTINAS AUTOMATICAS

	public void expirarTodasAsPermissoesDoSistema() {

	}

	public void expirarTodasAsSenhaDoSistema() {

	}

}
