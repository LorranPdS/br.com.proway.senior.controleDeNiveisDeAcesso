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
 * @author Sprint 5
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lucas Grijó, rksgrijo@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Thiago, thiagoluizbarbieri@gmail.com

**/

@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	@Column(name = "id_permissao")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_id_permissao")
	private Integer idPermissao;

	@Column(name = "nome_permissao")
	private String nomePermissao;
	
	/** 
	 * Entidade relacionameno entre as tabelas.
	 * 
	 * Perfil é uma lista de perfis.
	 *
	 * @since sprint5 
	 * @author TODO
	 *   
	**/

	@ManyToMany(mappedBy = "permissoes")
	private Set<Perfil> perfis = new HashSet<>();
	
	public Permissao() {}
	
	/**
	 * Construtor de permissão.
	 * 
	 * @param nomePermissao
	 */
	public Permissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

	/**
	 * Retorna o ID da permissão.
	 * 
	 * @return IdPermissao
	 */
	public Integer getIdPermissao() {
		return idPermissao;
	}

	/**
	 * Define um ID de permissão.
	 * 
	 * @param idPermissao
	 */
	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	/**
	 * Retorna o nome da permissão.
	 * 
	 * @return nomePermissao
	 */
	public String getNomePermissao() {
		return nomePermissao;
	}

	/**
	 * Define o nome da permissão.
	 *  
	 * @param nomePermissao
	 */
	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

	/**
	 * Retorna uma lista de perfis.
	 * 
	 * @return Set<Perfil>
	 */
	public Set<Perfil> getPerfis() {
		return perfis;
	}

	/**
	 * Define uma lista de perfis
	 * 
	 * @param perfis
	 */
	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public String toString() {
		return "Permissao [idPermissao=" + idPermissao + ", nomePermissao=" + nomePermissao + "]";
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
