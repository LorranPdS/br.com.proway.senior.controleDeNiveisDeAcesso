package model.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PerfilDeUsuarioDAOTest {

	@Before
	public void deletarTudoDoPerfilDeUsuario() {
		ligacaoDAO.deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		ligacaoDAO.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		ligacaoDAO.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilDAO.deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	static PerfilDeUsuarioDAO ligacaoDAO = PerfilDeUsuarioDAO.getInstance();
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

		usuarioDAO.criar(new Usuario("thiago@gmail.com", "admin"));
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);
		assertEquals(1, ligacaoDAO.listar().size());

		assertEquals(1, ligacaoDAO.consultarPorIdDoPerfil(perfil.getIdPerfil()).size());
	}

	@Test
	public void testDeletar() {
		assertEquals(0, ligacaoDAO.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);
		assertEquals(1, ligacaoDAO.listar().size());

		ligacaoDAO.deletar(ligacao);
		assertEquals(0, ligacaoDAO.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);
		assertEquals(1, ligacaoDAO.listar().size());

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		ligacaoDAO.alterar(ligacao);
		assertEquals(LocalDate.now().plusMonths(3), ligacaoDAO.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);
		assertEquals(1, ligacaoDAO.listar().size());
		assertEquals("Vendedor", ligacaoDAO.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testConsultarPorIdUsuario() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);
		
		assertEquals(1, ligacaoDAO.consultarPorIdDoUsuario(usuario.getIdUsuario()).size());
	}
	
	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao1);

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(2));
		ligacaoDAO.criar(ligacao2);

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(3));
		ligacaoDAO.criar(ligacao3);

		assertEquals(3, ligacaoDAO.listar().size());
	}
}
