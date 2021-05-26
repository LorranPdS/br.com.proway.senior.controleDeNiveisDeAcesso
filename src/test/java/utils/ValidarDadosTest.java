package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidarDadosTest {

	ValidarDados classe = new ValidarDados();
	
	@Test
	void testValidarSenha() {
		assertTrue(classe.validarSenha("admin123"));
	}
	
	@Test
	void testValidarSenhaInvalida() {
		assertFalse(classe.validarSenha("admin 123"));
	}
	
	@Test
	public void testClass() {
		assertNotNull(classe);
	}

}
