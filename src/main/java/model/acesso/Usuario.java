package model.acesso;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "usuario")
public class Usuario {

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_id_usuario")
	private int idUsuario;

	@Column(name = "login")
	private String login;

	@Column(name = "hash_senha")
	private String hashSenha;

	@Column(name = "ultima_alteracao_senha")
	private LocalDate ultimaAlteracaoSenha;

	@Column(name = "ultimo_codigo_2fa")
	private Integer ultimoCodigo2FA;

	@OneToMany(targetEntity = UsuarioPerfil.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "usuario")
	private Set<UsuarioPerfil> perfis = new HashSet<UsuarioPerfil>();

	public Usuario() {

	}

	public Usuario(String login, String hashSenha) {
		this.login = login;
		this.hashSenha = hashSenha;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
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

	public Integer getUltimoCodigo2FA() {
		return ultimoCodigo2FA;
	}

	public void setUltimoCodigo2FA(Integer ultimoCodigo2FA) {
		this.ultimoCodigo2FA = ultimoCodigo2FA;
	}

	public Set<UsuarioPerfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<UsuarioPerfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", login=" + login + ", hashSenha=" + hashSenha
				+ ", ultimaAlteracaoSenha=" + ultimaAlteracaoSenha + ", ultimoCodigo2FA=" + ultimoCodigo2FA
				+ ", perfis=" + perfis + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashSenha == null) ? 0 : hashSenha.hashCode());
		result = prime * result + idUsuario;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((perfis == null) ? 0 : perfis.hashCode());
		result = prime * result + ((ultimaAlteracaoSenha == null) ? 0 : ultimaAlteracaoSenha.hashCode());
		result = prime * result + ((ultimoCodigo2FA == null) ? 0 : ultimoCodigo2FA.hashCode());
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
		if (hashSenha == null) {
			if (other.hashSenha != null)
				return false;
		} else if (!hashSenha.equals(other.hashSenha))
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (perfis == null) {
			if (other.perfis != null)
				return false;
		} else if (!perfis.equals(other.perfis))
			return false;
		if (ultimaAlteracaoSenha == null) {
			if (other.ultimaAlteracaoSenha != null)
				return false;
		} else if (!ultimaAlteracaoSenha.equals(other.ultimaAlteracaoSenha))
			return false;
		if (ultimoCodigo2FA == null) {
			if (other.ultimoCodigo2FA != null)
				return false;
		} else if (!ultimoCodigo2FA.equals(other.ultimoCodigo2FA))
			return false;
		return true;
	}

}
