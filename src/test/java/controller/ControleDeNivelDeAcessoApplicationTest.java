package controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.jandex.Main;
import org.junit.jupiter.api.Test;

class ControleDeNivelDeAcessoApplicationTest {

	ControleDeNivelDeAcessoApplication classe = new ControleDeNivelDeAcessoApplication();
	
	@Test
	public void testClass() {
		assertNotNull(classe);
	}
}
