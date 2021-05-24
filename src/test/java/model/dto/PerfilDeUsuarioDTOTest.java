package model.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import model.entidades.PerfilDeUsuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6 *
 */
public class PerfilDeUsuarioDTOTest {

	@Test
	public void testGetId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setId(10);
		ligacao.setAtivo(true);
		PerfilDeUsuarioDTO ligacaoDTO = new PerfilDeUsuarioDTO(ligacao);
		assertEquals(10, (int) ligacaoDTO.getId());
		assertEquals(true, ligacaoDTO.getAtivo());
		assertEquals(null, ligacaoDTO.getUsuario());
	}

}
