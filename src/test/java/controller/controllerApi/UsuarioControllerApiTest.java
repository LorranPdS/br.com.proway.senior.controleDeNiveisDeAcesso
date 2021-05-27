package controller.controllerApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.PermissaoController;
import controller.controllers.UsuarioController;
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
import utils.HashSenha;

public class UsuarioControllerApiTest {
	
	UsuarioControllerApi usuarioApi = new UsuarioControllerApi();
	UsuarioController usuarioController = new UsuarioController();

	@BeforeEach
	public void deletarTudo() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		usuarioController.deletarTodos();
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

	UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	PerfilDAO perfilDAO = PerfilDAO.getInstance();
	static Perfil perfil;
	static Permissao permissao;
	static PermissaoController permissaoController = new PermissaoController();

	@org.junit.jupiter.api.Test
	public void testJCriarEConsultarUsuario() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));
		Usuario usuarioEncontradoPorNome = UsuarioController.getInstance().consultarUsuario(loginDoUsuario);
		Usuario usuarioEncontradoPorId = UsuarioController.getInstance()
				.consultarUsuario(usuarioEncontradoPorNome.getIdUsuario());
		assertEquals(loginDoUsuario, usuarioEncontradoPorNome.getLogin(), usuarioEncontradoPorId.getLogin());
	}

	@org.junit.jupiter.api.Test
	public void testListarTodosUsuarios() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(new Usuario(loginDoUsuario2, senhaDoUsuario2));
		assertEquals(2, usuarioApi.listarTodosOsUsuarios().size());
	}

	@org.junit.jupiter.api.Test
	public void testVerificarEListarPermissaoDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		usuarioApi.criarUsuario(new Usuario(loginUsuario, senhaUsuario));

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

		assertTrue(usuarioApi.possuiPermissoes(usuario.getIdUsuario(), permissao.getIdPermissao()));
		assertEquals(1, lista.size());
	}

	@org.junit.jupiter.api.Test
	public void testListarPerfisDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginUsuario, senhaUsuario));

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

	@org.junit.jupiter.api.Test
	public void testAlterarUsuario() {
		usuarioApi.criarUsuario(new Usuario("AntesDaAlteracao@gmail.com", "6666666"));

		Usuario usuarioCadastrado = UsuarioController.getInstance()
				.consultarUsuario("AntesDaAlteracao@gmail.com");
		
		Usuario usuarioNovo = new Usuario("DepoisDaAlteracao@gmail.com", "9999999");
		usuarioNovo.setIdUsuario(usuarioCadastrado.getIdUsuario());
		
		usuarioApi.alterarUsuario(usuarioNovo.getIdUsuario(), usuarioNovo);
		
		Usuario usuarioAlterado = UsuarioController.getInstance().consultarUsuario("DepoisDaAlteracao@gmail.com");
		
		assertEquals("DepoisDaAlteracao@gmail.com", usuarioAlterado.getLogin());
		assertEquals(HashSenha.criptografarSenha("DepoisDaAlteracao@gmail.com", "9999999"),
				usuarioAlterado.getHashSenha());
	}

	@org.junit.jupiter.api.Test
	public void testDeletarUsuario() {
		String loginDoUsuario = "UsuarioDeTesteDeDelecao@gmail.com";
		String senhaDoUsuario = "123123123";
		usuarioApi.criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(new Usuario(loginDoUsuario2, senhaDoUsuario2));

		UsuarioDTO usuarioASerDeletado = usuarioApi.consultarUsuario(loginDoUsuario);
		usuarioApi.deletarUsuario(usuarioASerDeletado.getIdUsuario());
		int numeroDeUsuariosDepoisDoTeste = usuarioApi.listarTodosOsUsuarios().size();
		assertEquals(1, numeroDeUsuariosDepoisDoTeste);
	}

	@org.junit.jupiter.api.Test
	public void testMListarTodosUsuarios() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		usuarioApi.criarUsuario(new Usuario(loginDoUsuario, senhaDoUsuario));

		String loginDoUsuario2 = "marcelo@gmail.com";
		String senhaDoUsuario2 = "456";
		usuarioApi.criarUsuario(new Usuario(loginDoUsuario2, senhaDoUsuario2));
		assertEquals(2, usuarioApi.listarTodosOsUsuarios().size());
	}
}
