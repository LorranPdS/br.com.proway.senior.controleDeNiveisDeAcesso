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
import model.dao.PerfilDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PerfilDeUsuarioControllerTest {

	@BeforeEach
	public void deletarTudoDoPerfilDeUsuario() {
		controller.deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterAll
	public static void limparTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	static PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	static PerfilDAO perfilDAO = PerfilDAO.getInstance();
	static UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	static Usuario usuario;
	static Permissao permissao;
	static Perfil perfil;

	public static void popularTabelas() {
		perfilDAO.criar(new Perfil("Vendedor"));
		perfil = perfilDAO.consultarPorNome("Vendedor");

		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras");

		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		perfil = perfilDAO.consultarPorNome("Vendedor");

		usuarioDAO.criar(new Usuario("thiago@gmail.com", "admin"));
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");

		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		assertEquals(1, controller.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		List<Permissao> permissoes = controller.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		assertEquals(1, permissoes.size());
		assertEquals("Relatório de compras", permissoes.get(0).getNomePermissao());
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controller.listar().size());

		assertEquals(1, controller.consultarPorIdDoPerfil(perfil.getIdPerfil()).size());
	}

	@Test
	public void testConsultarPorIdDoUsuario() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controller.listar().size());

		assertEquals(1, controller.consultarPorIdDoUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testDeletar() {
		assertEquals(0, controller.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		controller.deletar(ligacao.getId());
		assertEquals(0, controller.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		controller.alterar(ligacao.getId(), ligacao);
		assertEquals(LocalDate.now().plusMonths(3), controller.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		assertEquals(1, controller.listar().size());
		assertEquals("Vendedor", controller.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao1.getUsuario(), ligacao1.getPerfil(), ligacao1.getDataExpiracao());

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao2.getUsuario(), ligacao2.getPerfil(), ligacao2.getDataExpiracao());

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao3.getUsuario(), ligacao3.getPerfil(), ligacao3.getDataExpiracao());

		assertEquals(3, controller.listar().size());
	}

	@Test
	public void testUsuarioNaoPossuiPermissaoPara() {
		boolean possui = controller.usuarioPossuiPermissaoPara(usuario.getIdUsuario(), permissao.getIdPermissao());
		assertFalse(possui);
	}

	@Test
	public void testUsuarioPossuiPermissaoPara() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controller.usuarioPossuiPermissaoPara(usuario.getIdUsuario(), permissao.getIdPermissao());
		assertTrue(possui);
	}

	@Test
	public void testUsuarioPossuiOPerfil() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controller.usuarioPossuiOPerfil(usuario.getIdUsuario(), perfil.getIdPerfil());
		assertTrue(possui);
	}

	@Test
	public void testUsuarioNaoPossuiOPerfil() {
		boolean possui = controller.usuarioPossuiOPerfil(usuario.getIdUsuario(), perfil.getIdPerfil());
		assertFalse(possui);
	}

	@Test
	public void testPermissaoAtivaTrue() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(true);
		ligacao.setDataExpiracao(LocalDate.now().plusDays(10));
		assertTrue(controller.permissaoAtiva(ligacao));
	}

	@Test
	public void testPermissaoAtivaFalse() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(false);
		ligacao.setDataExpiracao(LocalDate.now().plusDays(10));
		assertFalse(controller.permissaoAtiva(ligacao));
	}

	@Test
	public void testPermissaoAtivaFalseData() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(true);
		ligacao.setDataExpiracao(LocalDate.of(2019, 01, 01));
		assertFalse(controller.permissaoAtiva(ligacao));
	}
	
	@Test 
	public void testDesativarLigacaoIdZero() {
		assertFalse(controller.desativar(0));
	}
	
	@Test 
	public void testDesativarLigacaoIdInexistente() {
		assertFalse(controller.desativar(1684684654));
	}
	
	@Test
	public void testListarPerfisAtivosDeUmUsuario() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(2));
		
		assertEquals(2, controller.listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario()).size());
		
		assertTrue(controller.desativar(controller.listar().get(0).getId()));
		assertEquals(1, controller.listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario()).size());
	}
	
	@Test
	public void testListarPerfisAtivosDeUmUsuarioInvalido() {
		assertEquals(0, controller.listarPerfisAtivosDeUmUsuario(6548).size());
	}
	
	@Test
	public void testListarTodasLigacoesAtivas() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(2));
		
		assertEquals(2, controller.listarTodasLigacoesAtivas().size());
	}
	
}
