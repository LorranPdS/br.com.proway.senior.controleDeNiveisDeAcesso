package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.acesso.Perfil;
import model.acesso.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

	@Ignore // Verificar se h� uma necessidade de criar retornos no email para fazermos o
			// assertEquals
	public void testEmail() throws Exception {
		Controller.getInstance().enviarEmailDeConfirmacaoDeLogin("abc@gmail.com");
	}

	@Test
	public void testDLogar() {
		String login = "Grijo";
		String senha= "234";
		Controller.getInstance().criarUsuario(login, senha);
		boolean logar = Controller.getInstance().logar(login, senha);
		System.out.println(logar);
		
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
	public void testACriarUsuario() {
		String login = "jonata";
		String hashSenha = "123";
		Controller.getInstance().criarUsuario(login, hashSenha);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(login);
		assertEquals(login, usuarioEncontrado.getLogin());
	}

	@Test
	public void testAtribuirPerfilAUmUsuario() {
		
	}

	@Test
	public void testBCriarEConsultarPerfil() {
		String  perfil = "Administrativo";
		Controller.getInstance().criarPerfil(perfil);
		Perfil perfilEncontrado =  Controller.getInstance().consultarPerfil(perfil);
		assertEquals(perfil, perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testAtribuirPermissaoAUmPerfil() {
		fail("Not yet implemented");
	}

	@Test
	public void testCriarPermissao() {
		String permissao = "ADMIN";
		Controller.getInstance().criarPermissao(permissao);
	}

	@Test
	public void testXlimparBanco() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE usuario,perfil CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
