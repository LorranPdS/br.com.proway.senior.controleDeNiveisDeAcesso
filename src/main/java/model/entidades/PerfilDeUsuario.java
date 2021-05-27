package model.entidades;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Classe PerfilDeUsuario
 * 
 * Classe representando a tabela de ligacao entre {@link Usuario} e
 * {@link Perfil). Perfis atrelados a um usuario podem expirar com o tempo
 * através do campo data_expiracao.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 */
@Entity
@Table(name = "perfildeusuario")
public class PerfilDeUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne(cascade = CascadeType.REFRESH)
	private Usuario usuario;

	@OneToOne(cascade = CascadeType.REFRESH)
	private Perfil perfil;

	@Column(name = "data_expiracao")
	private LocalDate dataExpiracao;

	@Column(name = "ativo")
	private Boolean ativo;

	public PerfilDeUsuario() {
	}

	public PerfilDeUsuario(Usuario usuario, Perfil perfil, LocalDate dataExpiracao, Boolean ativo) {
		setUsuario(usuario);
		setPerfil(perfil);
		setDataExpiracao(dataExpiracao);
		setAtivo(ativo);
	}

	public PerfilDeUsuario(Usuario usuario, Perfil perfil, LocalDate dataExpiracao) {
		setUsuario(usuario);
		setPerfil(perfil);
		setDataExpiracao(dataExpiracao);
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDate dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
