package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilDAOTest {


	@BeforeEach
	public void deletarTudo() {
		PerfilDAO.getInstance().deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterAll
	public static void limparTabelas() {
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	static Permissao permissao;
	static Perfil perfil;

	public static void popularTabelas() {

		PermissaoDAO.getInstance().criar(new Permissao("Relatorio de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNomeExato("Relatorio de compras");
	}

	@Test
	public void testAtribuirPermissaoAUmPerfil() {
		PerfilDAO.getInstance().criar(new Perfil("Vendedor"));
		perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		assertEquals(1, perfil.getPermissoes().size());
	}
	
	@Test
	public void testDesatribuirPermissaoDeUmPerfil() {
		PerfilDAO.getInstance().criar(new Perfil("Vendedor"));
		perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		assertEquals(1, perfil.getPermissoes().size());
		
		PerfilDAO.getInstance().desatribuirPermissaoDeUmPerfil(perfil, permissao);
		assertEquals(0, perfil.getPermissoes().size());
	}

	@Test
	public void testSalvarPerfil() {
		PerfilDAO.getInstance().criar(new Perfil("Administrador"));
		assertEquals(1, PerfilDAO.getInstance().listar().size());
	}

	@Test
	public void testAtualizarUmPerfil() {
		Perfil perfilOriginal = new Perfil("Administrador");
		PerfilDAO.getInstance().criar(perfilOriginal);
		assertEquals(1, PerfilDAO.getInstance().listar().size());
		perfilOriginal.setNomePerfil("Administrador Alterado");
		PerfilDAO.getInstance().alterar(perfilOriginal);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfilOriginal.getIdPerfil());
		assertEquals("Administrador Alterado", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testDeletarUmPerfil() {
		Perfil perfil = new Perfil("Administrador");

		PerfilDAO.getInstance().criar(perfil);

		assertEquals(1, PerfilDAO.getInstance().listar().size());

		PerfilDAO.getInstance().deletar(perfil);

		assertEquals(0, PerfilDAO.getInstance().listar().size());
	}

	@Test
	public void testBuscarPerfilPorNome() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		assertEquals("Vendedor", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testListarTodosOsPerfis() {
		assertEquals(0, PerfilDAO.getInstance().listar().size());
		PerfilDAO.getInstance().criar(new Perfil("Administrador Desenvolvimento"));
		PerfilDAO.getInstance().criar(new Perfil("Administrador Compras"));
		PerfilDAO.getInstance().criar(new Perfil("Administrador RH"));
		assertEquals(3, PerfilDAO.getInstance().listar().size());
	}

	@Test
	public void testListarPermissoesDeUmPerfil() {
		PerfilDAO.getInstance().criar(new Perfil("Vendedor"));
		perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor"); 
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		List<Permissao> permissoes = PerfilDAO.getInstance().listarPermissoesDeUmPerfil(perfil.getIdPerfil());
		assertEquals("Relatorio de compras", permissoes.get(0).getNomePermissao());
	}
	
	@Test
	public void testDeletarPerfilInexistente() {
		assertFalse(PerfilDAO.getInstance().deletar(new Perfil("Perfil nao existe")));
	}
}
