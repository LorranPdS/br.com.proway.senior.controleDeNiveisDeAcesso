package model.dto;

import java.time.LocalDate;

import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Usuario;

/**
 * Classe que informa o que a Api pode consumir.
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PerfilDeUsuarioDTO {

	private Integer id;
	private Usuario usuario;
	private Perfil perfil;
	private LocalDate dataExpiracao;
	private Boolean ativo;
	
	public PerfilDeUsuarioDTO(PerfilDeUsuario perfilDeUsuario) {
		this.id = perfilDeUsuario.getId();
		this.usuario = perfilDeUsuario.getUsuario();
		this.perfil = perfilDeUsuario.getPerfil();
		this.dataExpiracao = perfilDeUsuario.getDataExpiracao();
		this.ativo = perfilDeUsuario.getAtivo();
	}

	public Integer getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public LocalDate getDataExpiracao() {
		return dataExpiracao;
	}

	public Boolean getAtivo() {
		return ativo;
	}
}
