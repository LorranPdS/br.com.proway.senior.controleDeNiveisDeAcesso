package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import model.acesso.Perfil;
import model.acesso.PerfilDAO;
import model.acesso.Permissao;
import model.acesso.PermissaoDAO;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import model.acesso.UsuarioPerfil;
import model.acesso.UsuarioPerfilId;
import utils.Email;
import utils.HashSenha;

public class Controller {

	static Controller instance;
	
	private Controller() {
	}
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(senha); // Criptografa a senha antes de comparar a do
																		// banco.

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		String senhaBanco = usuario.getHashSenha();

		if (senhaCriptografada.equals(senhaBanco)) {
			return true;
		}
		return false;
	}

	// TODO
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
		//UsuarioDAO.getInstance().deletar(id);
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

	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil, LocalDate dataExp) {
		UsuarioPerfilId userPerfilId = new UsuarioPerfilId(usuario.getIdUsuario(), perfil.getIdPerfil());
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil(userPerfilId, usuario, perfil, dataExp);
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(usuarioPerfil);
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
		return null;
	}

	public ArrayList<Perfil> listarTodosOsPerfils() {
		return null;
	}

	public void listarPermissoesDeUmPerfil(Perfil perfil) {

	}

	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(permissao.getIdPermissao(), perfil);	
	}		

	// DAO - Permissao

	public void criarPermissao(String nomePermissao) {
//		Permissao permissao = new Permissao(nomePermissao);
//		PermissaoDAO.getInstance().criar(permissao);
	}

	public void alterarPermissao(Integer idPermissao, Permissao permissao) {

	}

	public void deletarPermissao(Integer idPermissao) {

	}

	public Permissao consultarPermissao(Integer idPermissao) {
		return null;
	}

	public Permissao consultarPermissao(String nome) {
		return null;
	}

	public ArrayList<Permissao> listarTodasAsPermissoes() {
		return null;
	}

	// ...

	// MISC

	/**
	 * Envia um e-mail
	 * 
	 * Envia o e-mail para o usu�rio com o c�digo aleat�rio gerado para a
	 * confirma��o.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   C�digo aleat�rio gerado pelo sistema
	 * @throws Exception
	 */
	public void enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {
		Email email = new Email(emailDoDestinario, "Grupo 3", "2FA Niveis de Acesso",
				"O seu c�digo �: " + gerarCodigo().toString());

		email.enviarEmail();
	}

	/**
	 * Gera um c�digo aleat�rio
	 * 
	 * Gera o c�gigo random para a verifica��o de usu�rio
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
