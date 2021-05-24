package model.dto;

import model.entidades.Permissao;

public class PermissaoDTO {

	private Integer idPermissao;
	private String nomePermissao;

	public PermissaoDTO(Permissao permissao) {
		this.idPermissao = permissao.getIdPermissao();
		this.nomePermissao = permissao.getNomePermissao();
	}

	public Integer getIdPermissao() {
		return idPermissao;
	}

	public String getNomePermissao() {
		return nomePermissao;
	}

}
