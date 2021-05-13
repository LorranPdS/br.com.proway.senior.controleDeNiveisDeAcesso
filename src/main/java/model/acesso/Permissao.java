package model.acesso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Classe Permissao
 * 
 * Recebe os atributos necessarios para instanciar uma permissao, que sera
 * vinculada a um perfil.
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
@Table(name = "permissao")
public class Permissao {

	@Id
	@Column(name = "id_permissao")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idPermissao;

	@Column(name = "nome_permissao")
	private String nomePermissao;

	@ManyToMany(mappedBy = "permissoes")
	private Set<Perfil> perfis = new HashSet<>();

	public Permissao() {

	}

	public Permissao(String nomePermissao) {
		super();
		this.nomePermissao = nomePermissao;
	}

	public Integer getIdPermissao() {
		return idPermissao;
	}

	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	public String getNomePermissao() {
		return nomePermissao;
	}

	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public String toString() {
		return "Permissao [idPermissao=" + idPermissao + ", nomePermissao=" + nomePermissao + ", perfis=" + perfis
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPermissao == null) ? 0 : idPermissao.hashCode());
		result = prime * result + ((nomePermissao == null) ? 0 : nomePermissao.hashCode());
		result = prime * result + ((perfis == null) ? 0 : perfis.hashCode());
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
		Permissao other = (Permissao) obj;
		if (idPermissao == null) {
			if (other.idPermissao != null)
				return false;
		} else if (!idPermissao.equals(other.idPermissao))
			return false;
		if (nomePermissao == null) {
			if (other.nomePermissao != null)
				return false;
		} else if (!nomePermissao.equals(other.nomePermissao))
			return false;
		if (perfis == null) {
			if (other.perfis != null)
				return false;
		} else if (!perfis.equals(other.perfis))
			return false;
		return true;
	}

}
