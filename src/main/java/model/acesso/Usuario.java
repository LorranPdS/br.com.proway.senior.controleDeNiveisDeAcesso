package model.acesso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utils.HashSenha;
import utils.ValidarDados;

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

@Entity
@Table(name = "Usuario")
public class Usuario {

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id_usuario;
	@Column(name = "login")
	private String login;
	@Column(name = "hash_senha")
	private String hash_senha;

	public Usuario() {
		
	}
	
	/**
	 * Modelo para o banco de dados na tabela usuariostabela
	 * 
	 * @param id_usuario
	 * @param loginDoUsuario
	 * @param hashSenhaDoUsuario
	 */
	public Usuario(String login, String senha) {
		this.login = login;
		setHash_senha(senha);
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

	public void setHash_senha(String senha) {
		if(ValidarDados.validarSenha(senha)) {
			this.hash_senha = HashSenha.criptografarSenha(senha);
		}
	}

	@Override
	public String toString() {
		return "Usuario [id_usuario=" + id_usuario + ", login=" + login + ", hash_senha=" + hash_senha + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash_senha == null) ? 0 : hash_senha.hashCode());
		result = prime * result + id_usuario;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (hash_senha == null) {
			if (other.hash_senha != null)
				return false;
		} else if (!hash_senha.equals(other.hash_senha))
			return false;
		if (id_usuario != other.id_usuario)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	

	
}
