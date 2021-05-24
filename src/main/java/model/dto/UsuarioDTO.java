package model.dto;

import model.entidades.Usuario;

public class UsuarioDTO {

	private Integer idUsuario;
	private String login;

	public UsuarioDTO(Usuario usuario) {
		this.idUsuario = usuario.getIdUsuario();
		this.login = usuario.getLogin();
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public String getLogin() {
		return login;
	}

}
