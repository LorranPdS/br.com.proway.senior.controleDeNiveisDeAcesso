package model.acesso;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioPerfilId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "id_usuario")
	private Integer idUsuario;

	@Column(name = "id_perfil")
	private Integer idPerfil;

	public UsuarioPerfilId() {}

	public UsuarioPerfilId(int idUsuario, int idPerfil) {
		this.idUsuario = idUsuario;
		this.idPerfil = idPerfil;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	@Override
	public String toString() {
		return "UsuarioPerfilId [idUsuario=" + idUsuario + ", idPerfil=" + idPerfil + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPerfil;
		result = prime * result + idUsuario;
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
		UsuarioPerfilId other = (UsuarioPerfilId) obj;
		if (idPerfil != other.idPerfil)
			return false;
		if (idUsuario != other.idUsuario)
			return false;
		return true;
	}

}
