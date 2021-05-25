package controller.controllerApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.PerfilDeUsuarioController;
import controller.PermissaoController;
import controller.UsuarioController;
import controller.controllerApi.UsuarioControllerApi;
import db.DBConnection;
import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class UsuarioControllerApiTest {
	
	UsuarioControllerApi usuarioApi = new UsuarioControllerApi();

	@Before
	public void deletarTudo() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		usuarioApi.deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@AfterClass
	public static void limparTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

	}

	UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	PerfilDAO perfilDAO = PerfilDAO.getInstance();
	static Perfil perfil;
	static Permissao permissao;
	static PermissaoController permissaoController = new PermissaoController();

	@Test
	public void testJCriarEConsultarUsuario() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);
		Usuario usuarioEncontradoPorNome = UsuarioController.getInstance().consultarUsuario(loginDoUsuario);
		Usuario usuarioEncontradoPorId = UsuarioController.getInstance()
				.consultarUsuario(usuarioEncontradoPorNome.getIdUsuario());
		assertEquals(loginDoUsuario, usuarioEncontradoPorNome.getLogin(), usuarioEncontradoPorId.getLogin());
	}

	@Test
	public void testListarTodosUsuarios() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(loginDoUsuario2, senhaDoUsuario2);
		assertEquals(2, usuarioApi.listarTodosOsUsuarios().size());
	}

	@Test
	public void testVerificarEListarPermissaoDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		usuarioApi.criarUsuario(loginUsuario, senhaUsuario);

		perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		permissaoController.criarPermissao("Relatório de compras.");
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras.");
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);

		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginUsuario);
		PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();
		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		List<PermissaoDTO> lista = new ArrayList<>();
		for (PermissaoDTO permissao : usuarioApi.listarPermissoesDeUmUsuario(usuario.getIdUsuario())) {
			lista.add(permissao);
		}

		assertTrue(usuarioApi.possuiPermissoes(usuario, permissao));
		assertEquals(1, lista.size());
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		UsuarioController.getInstance().criarUsuario(loginUsuario, senhaUsuario);

		perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfil2 = new Perfil("Comprador");
		PerfilDAO.getInstance().criar(perfil2);
		permissaoController.criarPermissao("Relatório de compras.");
		permissao = permissaoController.consultarPermissaoPorNome("Relatório de compras.");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil2, permissao);

		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginUsuario);

		PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil2, LocalDate.now().plusYears(1));

		List<PerfilDTO> lista = usuarioApi.listarPerfisDeUmUsuario(usuario.getIdUsuario());
		assertEquals(2, lista.size());
	}

	@Test
	public void testLogin() {
		String loginExistente = "Grijo@gmail.com";
		String senhaCorreta = "234";
		String senhaIncorreta = "123";
		UsuarioController.getInstance().criarUsuario(loginExistente, senhaCorreta);
		assertTrue(usuarioApi.logar(loginExistente, senhaCorreta));
		assertFalse(usuarioApi.logar(loginExistente, senhaIncorreta));
	}

	/**
	 * Ao executar esse teste, sinta-se livre para utilizar um email real mudando a
	 * string destinatario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEnviarEConfirmarEmailDeConfirmacaoDeLogin() throws Exception {

		String destinatario = "Email@gmail.com"; // Seja responsável e não spame os amiguinhos. ^^
		String senha = "123";
		UsuarioController.getInstance().criarUsuario(destinatario, senha);

		boolean resultadoEnvioEmail = usuarioApi.enviarEmailDeConfirmacaoDeLogin(destinatario);
		assertTrue(resultadoEnvioEmail);

		Usuario u = UsuarioController.getInstance().consultarUsuario(destinatario);
		Integer codigoDeConfirmacao = u.getUltimoCodigo2FA();
		assertTrue(usuarioApi.confirmarCodigoDeConfirmacao(destinatario, codigoDeConfirmacao));

		// Limpar usuario do banco pós-teste
		String sqlLimparUsuarioDoBanco = "DELETE FROM usuario WHERE login = '" + destinatario + "';";
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession().createSQLQuery(sqlLimparUsuarioDoBanco).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Test
	public void testAlterarUsuario() {
		String loginDoUsuario = "UsuarioDeTesteAntesDaAlteracao@gmail.com";
		String senhaDoUsuario = "6666666";
		usuarioApi.criarUsuario(loginDoUsuario, senhaDoUsuario);
		UsuarioDTO usuarioConsultado = usuarioApi.consultarUsuario(loginDoUsuario);
		String novoLoginDoUsuario = "UsuarioDeTesteDepoisDaAlteracao@gmail.com";
		String novaSenhaDoUsuario = "9999999";
		usuarioApi.alterarUsuario(usuarioConsultado.getIdUsuario(), novoLoginDoUsuario, novaSenhaDoUsuario);
		usuarioConsultado = usuarioApi.consultarUsuarioPorId(usuarioConsultado.getIdUsuario());
		assertEquals(novoLoginDoUsuario, usuarioConsultado.getLogin());
	}

	@Test
	public void testDeletarUsuario() {
		String loginDoUsuario = "UsuarioDeTesteDeDelecao@gmail.com";
		String senhaDoUsuario = "123123123";
		usuarioApi.criarUsuario(loginDoUsuario, senhaDoUsuario);

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(loginDoUsuario2, senhaDoUsuario2);

		UsuarioDTO usuarioASerDeletado = usuarioApi.consultarUsuario(loginDoUsuario);
		usuarioApi.deletarUsuario(usuarioASerDeletado.getIdUsuario());
		int numeroDeUsuariosDepoisDoTeste = usuarioApi.listarTodosOsUsuarios().size();
		assertEquals(1, numeroDeUsuariosDepoisDoTeste);
	}

	@Test
	public void testMListarTodosUsuarios() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		usuarioApi.criarUsuario(loginDoUsuario, senhaDoUsuario);

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(loginDoUsuario2, senhaDoUsuario2);
		assertEquals(2, usuarioApi.listarTodosOsUsuarios().size());
	}

	@Test
	public void testListarPerfisAtivosDeUmUsuario() {
		perfilDAO.criar(new Perfil("Vendedor"));
		perfil = perfilDAO.consultarPorNome("Vendedor");

		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras");

		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		perfil = perfilDAO.consultarPorNome("Vendedor");

		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		usuarioDAO.criar(usuario);
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");

		PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
		PerfilDeUsuarioControllerApi apiController = new PerfilDeUsuarioControllerApi();
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		List<PerfilDTO> lista = usuarioApi.listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario());

		assertTrue(usuarioApi.permissaoAtiva(controller.consultarPorIdDoUsuario(usuario.getIdUsuario()).get(0)));
		assertEquals(2, lista.size());
		assertTrue(apiController.desativar(controller.listar().get(0)));
	}

}
