package controller;

import java.util.ArrayList;
import java.util.Random;

import model.acesso.Perfil;
import model.acesso.Permissao;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import utils.Email;
import utils.HashSenha;

public class Controller {

	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(senha); // Criptografa a senha antes de comparar a do
																		// banco.

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		String senhaBanco = usuario.getHash_senha();

		if (senhaCriptografada.equals(senhaBanco)) {
			return true;
		}
		return false;
	}

	// TODO
	public boolean requererPermissao(Usuario usuario, Permissao permissao) {
		return false;
	}

	// DAO - Usuario

	public void criarUsuario(String login, String senha) {
		//
	}

	public void deletarUsuario(Integer id) {
		UsuarioDAO.getInstance().deletar(id);
	}

	public void alterarUsuario(Integer idUsuario, Usuario usuario) {

	}

	public Usuario consultarUsuario(Integer idUsuario) {
		return null;
	}

	public Usuario consultarUsuario(String login) {
		return null;
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
		return null;
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
	 * Envia o e-mail para o usuï¿½rio com o cï¿½digo aleatï¿½rio gerado para a
	 * confirmaï¿½ï¿½o.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   Cï¿½digo aleatï¿½rio gerado pelo sistema
	 * @throws Exception 
	 */
	public void enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {		
		Email email = new Email(
				emailDoDestinario,
				"Grupo 3",
				"2FA Niveis de Acesso",
				"O seu código é: " + gerarCodigo().toString());
			
			email.enviarEmail();
	}

	/**
	 * Gera um cï¿½digo aleatï¿½rio
	 * 
	 * Gera o cï¿½gigo random para a verificaï¿½ï¿½o de usuï¿½rio
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
