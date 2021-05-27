package model.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PerfilTest {

	@Test
	void testSetIdPerfil() {
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(4);
		assertEquals(4, perfil.getIdPerfil());
	}

}
