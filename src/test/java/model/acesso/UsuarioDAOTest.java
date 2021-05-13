package model.acesso;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDAOTest {

	@Test
	public void testACriarUsuarioEConsultarPorId() {
		Usuario usuarioASerCriado = new Usuario("loginTest", "senhaTest");
		UsuarioDAO.getInstance().criar(usuarioASerCriado);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuarioASerCriado.getIdUsuario());
		assertEquals(usuarioASerCriado, usuarioEncontrado);
	}

}
