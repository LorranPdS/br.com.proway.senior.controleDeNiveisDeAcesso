package model.dto;

import model.entidades.Permissao;

/**
 * Classe que informa o que a Api pode consumir.
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
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
