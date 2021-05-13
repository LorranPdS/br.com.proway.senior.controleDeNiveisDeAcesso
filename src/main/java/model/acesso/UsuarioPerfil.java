package model.acesso;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_perfil")
public class UsuarioPerfil {

	@EmbeddedId
	private UsuarioPerfilId id;

	@ManyToOne
	@JoinColumn(name = "id_usuario",insertable = false, updatable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_perfil",insertable = false, updatable = false)
	private Perfil perfil;

	@Column(name = "data_expiracao")
	private LocalDate dataExpiracao;

	public UsuarioPerfil() {

	}

	public UsuarioPerfil(UsuarioPerfilId id, Usuario usuario, Perfil perfil, LocalDate dataExpiracao) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.perfil = perfil;
		this.dataExpiracao = dataExpiracao;
	}

	@Override
	public String toString() {
		return "UsuarioPerfil [id=" + id + ", usuario=" + usuario + ", perfil=" + perfil + ", dataExpiracao="
				+ dataExpiracao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataExpiracao == null) ? 0 : dataExpiracao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		UsuarioPerfil other = (UsuarioPerfil) obj;
		if (dataExpiracao == null) {
			if (other.dataExpiracao != null)
				return false;
		} else if (!dataExpiracao.equals(other.dataExpiracao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
