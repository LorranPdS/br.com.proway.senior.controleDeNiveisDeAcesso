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
 * Recebe os atributos necessarios para instanciar uma permissao que sera
 * vinculada a um perfil.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
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

	public Permissao() {
	}

	public Permissao(String nomePermissao) {
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

	@Override
	public String toString() {
		return "Permissao [idPermissao=" + idPermissao + ", nomePermissao=" + nomePermissao + "]";
	}

}
