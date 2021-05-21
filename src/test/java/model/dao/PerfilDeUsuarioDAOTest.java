package model.dao;

import static org.junit.Assert.assertEquals;

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
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}
	
	static Usuario usuario;
	static Permissao permissao;
	static Perfil perfil;
	
	public static void popularTabelas() {
		PerfilDAO.getInstance().criar(new Perfil("Vendedor"));
		perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor"); 
		
		PermissaoDAO.getInstance().criar(new Permissao("Relatório de compras"));
		permissao = PermissaoDAO.getInstance().consultarPorNome("Relatório de compras");
		
		UsuarioDAO.getInstance().criar( new Usuario("thiago@gmail.com", "admin"));
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = UsuarioDAO.getInstance().consultarPorLogin("thiago@gmail.com");
		
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		PerfilDeUsuarioDAO.getInstance().atribuirPerfilAUmUsuario(ligacao);
		
		assertEquals(1, PerfilDeUsuarioDAO.getInstance().listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		PerfilDeUsuarioDAO.getInstance().atribuirPerfilAUmUsuario(ligacao);
		
		Set<Permissao> permissoes = PerfilDeUsuarioDAO.getInstance().listarPermissoesDeUmUsuario(usuario.getIdUsuario());
		
		List<Permissao> listaPermissoes = new ArrayList<Permissao>();
		for(Permissao permissao : permissoes)
			listaPermissoes.add(permissao);
		
		assertEquals(1, listaPermissoes.size());
		assertEquals("Relatório de compras", listaPermissoes.get(0).getNomePermissao());
	}


}
