package model.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

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
	}
	
	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");

		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);

		assertEquals(1, ligacaoDAO.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}
	
	@Test
	public void testListarPerfisAtivosDeUmUsuario() {
		usuario = usuarioDAO.consultarPorLogin("thiago@gmail.com");
		usuarioDAO.criar(new Usuario("usuario@gmail.com", "admin123"));

		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(2));
		ligacaoDAO.criar(ligacao);
		ligacaoDAO.criar(ligacao2);
		
		assertEquals(2, ligacaoDAO.listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario()).size());
		
		assertTrue(ligacaoDAO.desativar(ligacao2));
		assertEquals(1, ligacaoDAO.listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario()).size());
	}
	
	@Test
	public void testListarPerfisAtivosDeUmUsuarioInvalido() {
		assertEquals(0, ligacaoDAO.listarPerfisAtivosDeUmUsuario(6548).size());
	}
	
	@Test 
	public void testDesativarLigacaoNulla() {
		PerfilDeUsuario ligacao = null;
		assertFalse(ligacaoDAO.desativar(ligacao));
	}
	
	@Test 
	public void testDesativarLigacaoIdNull() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setUsuario(usuario);
		ligacao.setPerfil(perfil);
		ligacao.setDataExpiracao(LocalDate.now());
		ligacao.setAtivo(true);
		assertFalse(ligacaoDAO.desativar(ligacao));
	}
	
	@Test 
	public void testDesativarLigacaoIdInexistente() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setUsuario(usuario);
		ligacao.setPerfil(perfil);
		ligacao.setDataExpiracao(LocalDate.now());
		ligacao.setAtivo(true);
		ligacao.setId(6548);
		assertFalse(ligacaoDAO.desativar(ligacao));
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilDAO.atribuirPermissaoAUmPerfil(perfil, permissao);
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		ligacaoDAO.criar(ligacao);

		Set<Permissao> permissoes = ligacaoDAO.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		List<Permissao> listaPermissoes = new ArrayList<Permissao>();
		for (Permissao permissao : permissoes)
			listaPermissoes.add(permissao);

		assertEquals(1, listaPermissoes.size());
		assertEquals("Relatório de compras", listaPermissoes.get(0).getNomePermissao());
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
