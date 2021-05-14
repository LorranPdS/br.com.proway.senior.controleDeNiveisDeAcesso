package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.acesso.Permissao;
import model.acesso.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

	@Test
	public void testEmail() throws Exception {
		boolean resultadoEnvioEmail = Controller.getInstance().enviarEmailDeConfirmacaoDeLogin("NOMEFICTICIO@gmail.com");
		assertTrue(resultadoEnvioEmail);
	}

	@Test
	public void testLogar() {
		String login = "jonataD";
		String senha = "123";
		Controller.getInstance().criarUsuario(login, senha);
		assertEquals(true, Controller.getInstance().logar(login, senha));
	}

	@Test
	public void testVerificarPermissaoUsuario() {
		fail("Not yet implemented");
	}

	@Test
	public void testVerificarPermissaoPerfil() {
		fail("Not yet implemented");
	}

	@Test
	public void testACriarEConsultarUsuario() {
		String login = "jonata";
		String hashSenha = "123";
		Controller.getInstance().criarUsuario(login, hashSenha);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(login);
		System.out.println(usuarioEncontrado.toString());
		assertEquals(login, usuarioEncontrado.getLogin());
	}

	@Test
	public void testAtribuirPerfilAUmUsuario() {
		// TODO está sendo implementado
	}

	@Test
	public void testCriarPerfil() {
		fail("Not yet implemented");
	}

	@Test
	public void testAtribuirPermissaoAUmPerfil() {
		fail("Not yet implemented");
	}

	@Test
	public void testCriarEConsultarPermissao() {
		String permissao = "ADMIN";
		Controller.getInstance().criarPermissao(permissao);
		Permissao retornoPermissao = Controller.getInstance().consultarPermissao(permissao);
		assertEquals(permissao, retornoPermissao.getNomePermissao());
	}

	@Test
	public void testXlimparBanco() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
