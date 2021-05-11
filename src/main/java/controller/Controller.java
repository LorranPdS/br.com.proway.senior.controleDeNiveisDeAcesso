package controller;

import java.util.Random;

import model.acesso.Perfil;
import model.acesso.Permissao;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;

public class Controller {

	
	public boolean logar(String login, String senha) {
		String senhaCriptografada = ValidarDados.senhaDoUsuario(senha);
		
		// senhaBanco = Consultar se a senha criptografada = banco
		if (senhaCriptografada == senhaBanco) {
			return true;
		}
		return false;
	}	
	
	//TODO
	public boolean requererPermissao(Usuario usuario, Permissao permissao) {
		return false;
	}
	
	// DAO

	public void criarUsuario(String login, String senha) {
		//
	}
	
	public void deletarUsuario(Integer id) {
		UsuarioDAO.deletarUsuario(id);
	}
	
	public void atualizarUsuario(Integer idUsuario, Usuario usuario) {
		
	}
	
	public Usuario buscarUsuario(Integer idUsuario) {
	}
	
	public void listarPermissoesDeUmUsuario(Usuario usuario) {
		
	}
	
	public void listarPermissoesDeUmPerfil(Perfil perfil) {
		
	}
	
	//...
	
	
	
	// MISC
	
	/**
	 * Envia um e-mail
	 * 
	 * Envia o e-mail para o usuário com o código aleatório gerado para a
	 * confirmação.
	 * 
	 * @param email        Email do usuário
	 * @param codigoGerado Código aleatório gerado pelo sistema
	 */
	public String enviarEmail(String loginDoUsuario) {
		
		String codigo = "" + this.gerarCodigo();
		if (this.validarEmail(loginDoUsuario)) {
			// Faz conexão com BD e envia e-mail para usuário
			return codigo;
		}
		return codigo;
	}	
	
	/**
	 * Gera um código aleatório
	 * 
	 * Gera o cógigo random para a verificação de usuário
	 * 
	 * @return codigo de 5 digitos
	 */
	public boolean gerarCodigo() {

		Random random = new Random();
		int codigo = random.nextInt(99999);
		if (codigo <= 10000) {
			codigo += 10000;
		}
		return true;
	}
	
	// ROTINAS AUTOMATICAS
	
	public void expirarTodasAsPermissoesDoSistema(){
		
	}
	
	public void expirarTodasAsSenhaDoSistema() {
	
	}
	
}
