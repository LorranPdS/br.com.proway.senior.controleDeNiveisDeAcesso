package model.acesso;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import utils.HashSenha;

public class UsuarioModelHashTest {

	@Test
	public void testUsuarioModelHash() {
		int idUsuario = 1;
		String login = "vitor@gmail.com";
		String senha = "a";
		String valorCodificado = "1f40fc92da241694750979ee6cf582f2d5d7d28e1833"
				+ "5de05abc54d0560e0f5302860c652bf08d560252aa5e74210546f369fbbbce"
				+ "8c12cfc7957b2652fe9a75";
		HashSenha hasher = new HashSenha();
		System.out.println(hasher.senhaDoUsuario(senha));
		assertEquals(valorCodificado,hasher.senhaDoUsuario(senha));
	}

}
