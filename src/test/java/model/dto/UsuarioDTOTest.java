package model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.entidades.Usuario;

class UsuarioDTOTest {

	@Test
	void testGetLogin() {
		Usuario usuario = new Usuario("login@gmail.com", "admin123");
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
		assertEquals(usuario.getLogin(), usuarioDTO.getLogin());
	}

}
