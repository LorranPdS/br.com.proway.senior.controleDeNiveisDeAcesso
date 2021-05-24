package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.dao.PerfilDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class PerfilDeUsuarioControllerTest {

	@Before
	public void deletarTudoDoPerfilDeUsuario() {
		controller.deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterClass
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

		ArrayList<Permissao> permissoes = controller.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

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

		controller.deletar(ligacao);
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
		controller.alterar(ligacao);
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
		boolean possui = controller.usuarioPossuiPermissaoPara(usuario, permissao);
		assertFalse(possui);
	}
	
	@Test
	public void testUsuarioPossuiPermissaoPara() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controller.usuarioPossuiPermissaoPara(usuario, permissao);
		assertTrue(possui);
	}
	
	@Test
	public void testUsuarioPossuiOPerfil() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controller.usuarioPossuiOPerfil(usuario, perfil);
		assertTrue(possui);
	}
	
	@Test
	public void testUsuarioNaoPossuiOPerfil() {
		boolean possui = controller.usuarioPossuiOPerfil(usuario, perfil);
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
	

}
