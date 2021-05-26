package model.entidades;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PermissaoTest {

	@Test
	void testSetIdPermissao() {
		Permissao permissao = new Permissao();
		permissao.setIdPermissao(4);
		assertEquals(4, permissao.getIdPermissao());
	}

}
