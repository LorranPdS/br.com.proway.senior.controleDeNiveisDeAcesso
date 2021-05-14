package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.acesso.Perfil;
import model.acesso.PerfilDAO;
import model.acesso.Permissao;
import model.acesso.PermissaoDAO;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import model.acesso.UsuarioPerfil;
import model.acesso.UsuarioPerfilId;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

	@Ignore // Verificar se há uma necessidade de criar retornos no email para fazermos o
			// assertEquals
	public void testEmail() throws Exception {
		Controller.getInstance().enviarEmailDeConfirmacaoDeLogin("abc@gmail.com");
	}

	@Test
	public void testLogar() {
		fail("Not yet implemented");
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
		System.out.println(usuarioEncontrado.toString());
		assertEquals(login, usuarioEncontrado.getLogin());
	}

	@Test
	public void testAtribuirPerfilAUmUsuario() {
		Usuario usuario = new Usuario("Gabriel@gmail.com", "hashSenha123");
		UsuarioDAO.getInstance().criar(usuario);
		Perfil perfil = new Perfil("RH");
		PerfilDAO.getInstance().criar(perfil);
		LocalDate data = LocalDate.of(2021, 02, 04);

		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil, data);
		// TODO Fazer o teste
	}

	@Test
	public void testCriarPerfil() {
		fail("Not yet implemented");
	}

	@Test
	public void testBAtribuirPermissaoAUmPerfil() {
		Perfil perfilCriado = new Perfil("perfil Teste");
		PerfilDAO.getInstance().criar(perfilCriado);
		Permissao permissaoCriado = new Permissao("permissao1");
		PermissaoDAO.getInstance().criar(permissaoCriado);
		Perfil perfil = PerfilDAO.getInstance().consultarPorNome("perfil Teste");
		Permissao permissao = PermissaoDAO.getInstance().consultarPorNome("permissao1");
		Controller.getInstance().atribuirPermissaoAUmPerfil(permissao, perfil);

		// TODO Fazer o teste
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
					.createSQLQuery("TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
					.executeUpdate();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE perfil CASCADE; ALTER SEQUENCE gerador_id_perfil RESTART 1;")
					.executeUpdate();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_permissao RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
