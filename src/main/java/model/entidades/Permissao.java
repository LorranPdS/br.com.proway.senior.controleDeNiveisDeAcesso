package model.entidades;

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
 * 
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
	 * Perfil e uma lista de perfis.
	 *
	 * @since sprint5
	 * @author TODO
	 * 
	 **/

	@ManyToMany(mappedBy = "permissoes")
	private Set<Perfil> perfis = new HashSet<Perfil>();

	public Permissao() {
	}

	/**
	 * Construtor de permissao.
	 * 
	 * @param nomePermissao
	 */
	public Permissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

	/**
	 * Retorna o ID da permissao.
	 * 
	 * @return IdPermissao
	 */
	public Integer getIdPermissao() {
		return idPermissao;
	}

	/**
	 * Define um ID de permissao.
	 * 
	 * @param idPermissao
	 */
	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	/**
	 * Retorna o nome da permissao.
	 * 
	 * @return nomePermissao
	 */
	public String getNomePermissao() {
		return nomePermissao;
	}

	/**
	 * Define o nome da permissao.
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
	 * Define uma lista de perfis.
	 * 
	 * @param perfis
	 */
	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}

}
