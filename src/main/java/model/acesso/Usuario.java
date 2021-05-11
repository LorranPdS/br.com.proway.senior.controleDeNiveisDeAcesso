package model.acesso;

import controller.ValidarDados;

/**
 * Classe Usuario
 * 
 * Recebe os atributos necessarios para instanciar um usuario
 * 
 * @author Sprint 3
 * @author David Willian, david.oliveira@senior.com.br
 * @author Leonardo Pereira, leonardo.pereira@senior.com.br
 * @author Vitor Peres, vitor.peres@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */
public class Usuario {

	private int id_usuario;
	private String login;
	private String hash_senha;

	/**
	 * Modelo para o banco de dados na tabela usuariostabela
	 * 
	 * @param idUsuario
	 * @param loginDoUsuario
	 * @param hashSenhaDoUsuario
	 */
	public Usuario(int id_usuario, String login, String hash_senha) {
		this.id_usuario = id_usuario;
		this.login = login;
		this.hash_senha = hash_senha;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		ValidarDados.validarEmail(login);
		this.login = login;
	}

	public String getHash_senha() {
		return hash_senha;
	}

	public void setHash_senha(String hash_senha) {
		this.hash_senha = hash_senha;
	}

	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", login=" + login + ", hash_senha=" + hash_senha + "]";
	}

}
