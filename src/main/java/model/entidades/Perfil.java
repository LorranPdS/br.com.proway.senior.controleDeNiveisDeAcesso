package model.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Classe Perfil
 * 
 * Define os atributos necessarios para instanciar um perfil, o que e
 * constituido por permissoes.
 * 
 * @author Simon gabrielsimon775@gmail.com
 * @author Jonata Caetano jonatacaetano88@gmail.com
 * @author Lucas Grijo rksgrijo@gmail.com
 * @author Lorran lorransantospereira@yahoo.com.br
 * @author Thiago thiagoluizbarbieri@gmail.com
 * @since Sprint 4&5
 */
@Entity
@Table(name = "perfil")
public class Perfil {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idPerfil;

	@Column(name = "nome")
	private String nomePerfil;

	@ManyToMany(cascade = CascadeType.REFRESH)
	private List<Permissao> permissoes = new ArrayList<Permissao>();

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

	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Permissao permissao) {
		this.permissoes.add(permissao);
	}

	@Override
	public String toString() {
		return "Perfil [idPerfil=" + idPerfil + ", nomePerfil=" + nomePerfil + ", permissoes=" + permissoes + "]";
	}
}
