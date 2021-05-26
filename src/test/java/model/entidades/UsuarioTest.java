package model.entidades;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class UsuarioTest {

	@Test
	void testGetsESets() {
		Usuario usuario = new Usuario("login@gmail.com", "admin123");
		usuario.setUltimaAlteracaoSenha(LocalDate.now());
		assertEquals(LocalDate.now(), usuario.getUltimaAlteracaoSenha());
		
		
	}

}
