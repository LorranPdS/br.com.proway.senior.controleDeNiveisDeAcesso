package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.PermissaoController;
import controller.controllers.UsuarioController;
import db.DBConnection;
import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;
import utils.HashSenha;

public class UsuarioControllerTest {

	@BeforeEach
	public void deletarTudo() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UController.deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@AfterAll
	public static void limparTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

	}

	UsuarioController UController = new UsuarioController();
	PerfilDAO perfilDAO = PerfilDAO.getInstance();
	UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	static Usuario usuario;
	static Perfil perfil;
	static Permissao permissao;
	static PermissaoController permissaoController = new PermissaoController();

	// o teste de tentar com um login inexistente nao foi feito, pois como o metodo
	// busca polo loging, a query volta nulla e lenaca a excecao
	@Test
	public void testLogin() {
		String loginExistente = "Grijo@gmail.com";
		String senhaCorreta = "234";
		String senhaIncorreta = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginExistente, senhaCorreta));
		assertTrue(UsuarioController.getInstance().logar(loginExistente, senhaCorreta));
		assertFalse(UsuarioController.getInstance().logar(loginExistente, senhaIncorreta));
	}
	
	/**
	 * Ao executar esse teste, sinta-se livre para utilizar um email real mudando a
	 * string destinatario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNEnviarEConfirmarEmailDeConfirmacaoDeLogin() throws Exception {

		String destinatario = "Email@gmail.com"; // Seja responsável e não spame os amiguinhos. ^^
		String senha = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(destinatario, senha));

		boolean resultadoEnvioEmail = UsuarioController.getInstance().enviarEmailDeConfirmacaoDeLogin(destinatario);
		assertTrue(resultadoEnvioEmail);

		Usuario u = UsuarioController.getInstance().consultarUsuario(destinatario);
		Integer codigoDeConfirmacao = u.getUltimoCodigo2FA();
		assertTrue(UsuarioController.getInstance().confirmarCodigoDeConfirmacao(destinatario, codigoDeConfirmacao));

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
	public void testJCriarEConsultarUsuario() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));
		Usuario usuarioEncontradoPorNome = UsuarioController.getInstance().consultarUsuario(loginDoUsuario);
		Usuario usuarioEncontradoPorId = UsuarioController.getInstance()
				.consultarUsuario(usuarioEncontradoPorNome.getIdUsuario());
		assertEquals(loginDoUsuario, usuarioEncontradoPorNome.getLogin(), usuarioEncontradoPorId.getLogin());
	}

	@Test
	public void testKAlterarUsuario() {
		UsuarioController.getInstance().criarUsuario(new Usuario("AntesDaAlteracao@gmail.com", "6666666"));

		Usuario usuarioCadastrado = UsuarioController.getInstance()
				.consultarUsuario("AntesDaAlteracao@gmail.com");
		
		Usuario usuarioNovo = new Usuario();
		usuarioNovo.setLogin("DepoisDaAlteracao@gmail.com");
		usuarioNovo.setHashSenha("9999999");
		usuarioNovo.setIdUsuario(usuarioCadastrado.getIdUsuario());
		
		UsuarioController.getInstance().alterarUsuario(usuarioNovo.getIdUsuario(),usuarioNovo.getLogin(), usuarioNovo.getHashSenha());
		
		Usuario usuarioAlterado = UsuarioController.getInstance().consultarUsuario("DepoisDaAlteracao@gmail.com");
		
		assertEquals("DepoisDaAlteracao@gmail.com", usuarioAlterado.getLogin());
		assertEquals(HashSenha.criptografarSenha("DepoisDaAlteracao@gmail.com", "9999999"),
				usuarioAlterado.getHashSenha());
	}

	@Test
	public void testLDeletarUsuario() {
		String loginDoUsuario = "UsuarioDeTesteDeDelecao@gmail.com";
		String senhaDoUsuario = "123123123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario2, senhaDoUsuario2));

		Usuario usuarioASerDeletado = UsuarioController.getInstance().consultarUsuario(loginDoUsuario);
		UsuarioController.getInstance().deletarUsuario(usuarioASerDeletado.getIdUsuario());
		int numeroDeUsuariosDepoisDoTeste = UsuarioController.getInstance().listarTodosOsUsuarios().size();
		assertEquals(1, numeroDeUsuariosDepoisDoTeste);
	}

	@Test
	public void testMListarTodosUsuarios() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario2, senhaDoUsuario2));
		assertEquals(2, UsuarioController.getInstance().listarTodosOsUsuarios().size());
	}

	@Test
	public void testVerificarEListarPermissaoDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginUsuario, senhaUsuario));

		perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		permissaoController.criarPermissao("Relatório de compras.");
		permissao = PermissaoDAO.getInstance().consultarPorNomeExato("Relatório de compras.");
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);

		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginUsuario);
		PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();
		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		List<Permissao> lista = new ArrayList<>();

		for (Permissao permissao1 : UController.listarPermissoesDeUmUsuario(usuario.getIdUsuario())) {
			lista.add(permissao1);
		}

		assertTrue(
				UsuarioController.getInstance().possuiPermissoes(usuario.getIdUsuario(), permissao.getIdPermissao()));
		assertEquals(1, lista.size());
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginUsuario, senhaUsuario));

		perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfil2 = new Perfil("Comprador");
		PerfilDAO.getInstance().criar(perfil2);
		permissaoController.criarPermissao("Relatório de compras.");
		permissao = permissaoController.consultarPermissaoPorNomeExato("Relatório de compras.");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil2, permissao);

		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginUsuario);

		PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil2, LocalDate.now().plusYears(1));

		List<Perfil> lista = UsuarioController.getInstance().listarPerfisDeUmUsuario(usuario.getIdUsuario());
		assertEquals(2, lista.size());
	}

	@Test
	public void testListarPerfisAtivosDeUmUsuario() {
		perfilDAO.criar(new Perfil("Vendedor"));
		perfil = perfilDAO.consultarPorNome("Vendedor");

		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNomeExato("Relatório de compras");

		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		perfil = perfilDAO.consultarPorNome("Vendedor");

		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		usuarioDAO.criar(usuario);
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");

		PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		List<Perfil> lista = UsuarioController.getInstance().listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario());

		assertEquals(2, lista.size());
		assertTrue(controller.desativar(controller.listar().get(0).getId()));
		assertEquals(1, UsuarioController.getInstance().listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testPossuiPermissao() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginUsuario, senhaUsuario));

		perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfil2 = new Perfil("Comprador");
		PerfilDAO.getInstance().criar(perfil2);
		permissaoController.criarPermissao("Relatório de compras.");
		permissao = permissaoController.consultarPermissaoPorNomeExato("Relatório de compras.");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil2, permissao);

		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginUsuario);

		PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioController.atribuirPerfilAUmUsuario(usuario, perfil2, LocalDate.now().plusYears(1));

		assertTrue(UController.possuiPermissoes(usuario.getIdUsuario(), permissao.getIdPermissao()));
	}
}
