	package model.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import utils.HashSenha;
import utils.ValidarDados;

/**
 * Classe Usuario
 * 
 * Recebe os atributos necessarios para instanciar um usuario
 * 
 * @author Simon gabrielsimon775@gmail.com
 * @author Jonata Caetano jonatacaetano88@gmail.com
 * @author Lucas Grijó rksgrijo@gmail.com
 * @author Lorran lorransantospereira@yahoo.com.br
 * @author Thiago thiagoluizbarbieri@gmail.com
 * @since Sprint 4&5
 */
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idUsuario;

	@Column(name = "login")
	private String login;

	@Column(name = "hash_senha")
	private String hashSenha;

	@Column(name = "ultima_alteracao_senha")
	private LocalDate ultimaAlteracaoSenha;

	/**
	 * Codigo de verificacao de duas etapas.
	 */
	@Column(name = "ultimo_codigo_2fa")
	private Integer ultimoCodigo2FA;

	public Usuario() {}

	/**
	 * Cria um novo Usuario.
	 * 
	 * O Usuario e criado a partir de um Login e Senha, e 
	 * a senha � criptografada.
	 * 
	 * @param login
	 * @param senha
	 */
	public Usuario(String login, String senha) {
		setLogin(login);
		this.hashSenha = HashSenha.criptografarSenha(login, senha);
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	/**
	 * Define o Login do Usuario.
	 * 
	 * Verifica se a String tem o '@'.
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		if(ValidarDados.validarEmail(login))
			this.login = login;
	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}

	public LocalDate getUltimaAlteracaoSenha() {
		return ultimaAlteracaoSenha;
	}

	public void setUltimaAlteracaoSenha(LocalDate ultimaAlteracaoSenha) {
		this.ultimaAlteracaoSenha = ultimaAlteracaoSenha;
	}

	/**
	 * Retorna o ultimo codigo de verificacao de duas etapas.
	 * 
	 * @return ultimoCodigo2FA
	 */
	public Integer getUltimoCodigo2FA() {
		return ultimoCodigo2FA;
	}
	
	/**
	 * Define o ultimo valor do codigo de verificacao de duas etapas.
	 * @param ultimoCodigo2FA
	 */
	public void setUltimoCodigo2FA(Integer ultimoCodigo2FA) {
		this.ultimoCodigo2FA = ultimoCodigo2FA;
	}

}