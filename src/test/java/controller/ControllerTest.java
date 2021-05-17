package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {
	
	@Ignore
	public void testEmail() throws Exception {
		boolean resultadoEnvioEmail = Controller.getInstance().enviarEmailDeConfirmacaoDeLogin("NOMEFICTICIO@gmail.com");
		assertTrue(resultadoEnvioEmail);
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
	public void testDeletarPerfil() {
		String novoPerfil = "GERENCIA";
		Controller.getInstance().criarPerfil(novoPerfil);
		Perfil perfilCadastrado = Controller.getInstance().consultarPerfil(novoPerfil);
		assertEquals(novoPerfil, perfilCadastrado.getNomePerfil());
		
		Controller.getInstance().deletarPerfil(perfilCadastrado);
		
		perfilCadastrado = Controller.getInstance().consultarPerfil(perfilCadastrado.getIdPerfil());
		assertNull(perfilCadastrado);
	}

	@Test
	public void testACriarEConsultarUsuario() {
		String login = "jonata";
		String hashSenha = "123";
		Controller.getInstance().criarUsuario(login, hashSenha);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(login);
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
	}

	@Test
	public void testBCriarEConsultarPerfil() {
		String  perfil = "ADMIN";
		Controller.getInstance().criarPerfil(perfil);
		Perfil perfilEncontrado =  Controller.getInstance().consultarPerfil(perfil);
		assertEquals(perfil, perfilEncontrado.getNomePerfil());
	}
	
	@Test
	public void testAlterarPerfil() {
		String nomePerfil = "Engenharia";
		Controller.getInstance().criarPerfil("Logistica");
		int idPerfilLogistica = Controller.getInstance().consultarPerfil("Logistica").getIdPerfil();
		Controller.getInstance().alterarPerfil(idPerfilLogistica, nomePerfil);
		Perfil perfilEncontrado = Controller.getInstance().consultarPerfil(idPerfilLogistica);
		assertEquals(nomePerfil, perfilEncontrado.getNomePerfil());
	}
	
	@Test
	public void testAlterarPerfilNome() {
		Perfil perfilAlterar = new Perfil("Motorista");
		PerfilDAO.getInstance().criar(perfilAlterar);
		perfilAlterar.setNomePerfil("Logistica");
		PerfilDAO.getInstance().alterar(perfilAlterar);
		Perfil perfilAlteradoEncontrado = PerfilDAO.getInstance().consultarPorNome(perfilAlterar.getNomePerfil());
		assertNotEquals(perfilAlterar.getNomePerfil(), perfilAlteradoEncontrado.getNomePerfil());
	}
	
	public void testBConsultarPerfilPorId() {
		String  perfil = "ST";
		Controller.getInstance().criarPerfil(perfil);
		int idPerfil = PerfilDAO.getInstance().consultarPorNome(perfil).getIdPerfil();
		Perfil perfilEncontrado =  Controller.getInstance().consultarPerfil(idPerfil);
		assertEquals(perfil, perfilEncontrado.getNomePerfil());
	}
	
	@Test
	public void testAlterarDadosUsuario() {
		String loginUsuario = "roberto";
		String senhaUsuario = "jaqueta";
		
		Controller.getInstance().criarUsuario(loginUsuario, senhaUsuario);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(loginUsuario);
		assertEquals(loginUsuario, usuarioEncontrado.getLogin());
		
		loginUsuario = "mauricio";
		usuarioEncontrado.setLogin(loginUsuario);
		Controller.getInstance().alterarUsuario(usuarioEncontrado.getIdUsuario(), usuarioEncontrado);
		usuarioEncontrado = Controller.getInstance().consultarUsuario(loginUsuario);
		assertEquals(loginUsuario, usuarioEncontrado.getLogin());
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
	}

	@Test
	public void testCriarEConsultarPermissao() {
		String permissao = "ADMIN";
		Controller.getInstance().criarPermissao(permissao);
		Permissao retornoPermissao = Controller.getInstance().consultarPermissao(permissao);
		assertEquals(permissao, retornoPermissao.getNomePermissao());
	}
	
	@Test
	public void testListarTodosPerfis() {
		ArrayList<Perfil> listaPerfis = Controller.getInstance().listarTodosOsPerfis();
		assertNotNull(listaPerfis);
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
