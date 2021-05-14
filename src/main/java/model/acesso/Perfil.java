package model.acesso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classe Perfil
 * 
 * Define os atributos necessarios para instanciar um perfil, o que e
 * constitui�do por permissoes.
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */
@Entity
@Table(name = "perfil")
public class Perfil {

	@Id
	@Column(name = "id_perfil")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gerador_id_perfil")
	private Integer idPerfil;

	@Column(name = "nome_perfil")
	private String nomePerfil;

	@OneToMany(targetEntity = UsuarioPerfil.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "perfil")
	private Set<UsuarioPerfil> usuarios = new HashSet<UsuarioPerfil>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "perfil_permissao", joinColumns = { @JoinColumn(name = "id_perfil") }, inverseJoinColumns = {
			@JoinColumn(name = "id_permissao") })
	private Set<Permissao> permissoes = new HashSet<>();

	public Perfil() {

	}

	public Perfil(String nomePerfil) {
		super();
		this.nomePerfil = nomePerfil;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public Set<UsuarioPerfil> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<UsuarioPerfil> usuarios) {
		this.usuarios = usuarios;
	}

	public Set<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Set<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public String toString() {
		return "Perfil [idPerfil=" + idPerfil + ", nomePerfil=" + nomePerfil + ", usuarios=" + usuarios
				+ ", permissoes=" + permissoes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPerfil == null) ? 0 : idPerfil.hashCode());
		result = prime * result + ((nomePerfil == null) ? 0 : nomePerfil.hashCode());
		result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
		result = prime * result + ((usuarios == null) ? 0 : usuarios.hashCode());
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
		Perfil other = (Perfil) obj;
		if (idPerfil == null) {
			if (other.idPerfil != null)
				return false;
		} else if (!idPerfil.equals(other.idPerfil))
			return false;
		if (nomePerfil == null) {
			if (other.nomePerfil != null)
				return false;
		} else if (!nomePerfil.equals(other.nomePerfil))
			return false;
		if (permissoes == null) {
			if (other.permissoes != null)
				return false;
		} else if (!permissoes.equals(other.permissoes))
			return false;
		if (usuarios == null) {
			if (other.usuarios != null)
				return false;
		} else if (!usuarios.equals(other.usuarios))
			return false;
		return true;
	}

}
