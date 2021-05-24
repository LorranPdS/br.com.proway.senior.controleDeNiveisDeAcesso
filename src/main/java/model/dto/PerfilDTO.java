package model.dto;

import model.entidades.Perfil;

public class PerfilDTO {

	private Integer idPerfil;
	private String nomePerfil;

	public PerfilDTO(Perfil perfil) {
		this.idPerfil = perfil.getIdPerfil();
		this.nomePerfil = perfil.getNomePerfil();
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

}
