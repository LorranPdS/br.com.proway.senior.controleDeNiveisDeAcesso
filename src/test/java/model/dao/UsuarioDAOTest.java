package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PermissaoController;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class UsuarioDAOTest {

	@BeforeEach
	public void deletarTudo() {
		UsuarioDAO.getInstance().deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterAll
	public static void limparTabelas() {
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

	}

	public static void popularTabelas() {
		PermissaoController controllerPermissao = new PermissaoController();
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);

		controllerPermissao.criarPermissao("Relatório de compras.");
		Permissao permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras.");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	@Test
	public void testCriarUsuario() {
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		assertEquals(1, UsuarioDAO.getInstance().listar().size());
	}

	@Test
	public void testConsultarUsuarioPorId() {
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuario.getIdUsuario());
		assertEquals("thiago@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testAlterarUsuario() {
		Usuario usuario = new Usuario("antes@gmail.com", "senha2");
		UsuarioDAO.getInstance().criar(usuario);
		usuario.setLogin("depois@gmail.com");
		UsuarioDAO.getInstance().alterar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuario.getIdUsuario());
		assertEquals("depois@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testConsultarUsuarioPorLogin() {
		Usuario usuario = new Usuario("login@gmail.com", "senha3");
		UsuarioDAO.getInstance().criar(usuario);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorLogin("login@gmail.com");
		assertEquals(usuario.getIdUsuario(), usuarioEncontrado.getIdUsuario());
	}

	@Test
	public void testDeletarUsuario() {
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		assertEquals(1, UsuarioDAO.getInstance().listar().size());
		UsuarioDAO.getInstance().deletar(usuario);
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
	}

	@Test
	public void testVerificarCodigoDeConfirmacao() {
		assertEquals(0, UsuarioDAO.getInstance().listar().size());
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		usuario.setUltimoCodigo2FA(1234);
		UsuarioDAO.getInstance().criar(usuario);
		assertEquals(usuario, UsuarioDAO.getInstance().verificarCodigoDeConfirmacao(
				UsuarioDAO.getInstance().consultarPorLogin("thiago@gmail.com").getLogin(), 1234));
	}

}
