package model.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe Permissao
 * 
 * Recebe os atributos necessarios para instanciar uma permissao, que sera
 * vinculada a um perfil.
 * 
 **/

@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idPermissao;

	@Column(name = "nome")
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

	@Override
	public String toString() {
		return "Permissao [idPermissao=" + idPermissao + ", nomePermissao=" + nomePermissao + "]";
	}

}
