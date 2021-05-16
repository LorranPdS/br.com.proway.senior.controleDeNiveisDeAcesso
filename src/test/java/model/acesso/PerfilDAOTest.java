package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilDAOTest {

	@Test
	public void testBSalvarPerfil() {
		Perfil perfil = new Perfil("ADMIN");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfil.getIdPerfil());
		assertEquals(perfil, perfilEncontrado);
	}

	@Test
	public void testCAtualizar() {
		Perfil perfilOriginal = new Perfil("Secretaria");
		PerfilDAO.getInstance().criar(perfilOriginal);
		perfilOriginal.setNomePerfil("Comprador");
		PerfilDAO.getInstance().alterar(perfilOriginal);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfilOriginal.getIdPerfil());
		assertEquals("Comprador", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testDDeletar() {
		Perfil perfil = new Perfil("ADMIN");
		System.out.println(perfil.toString());
		boolean resultado = PerfilDAO.getInstance().deletar(perfil);
		assertTrue(resultado);
	}

	@Test
	public void testEBuscarPorNome() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		
		System.out.println(perfilEncontrado.getNomePerfil());
		assertEquals(perfil.getNomePerfil(), perfilEncontrado.getNomePerfil());
	}
	
	@Test
	public void testFListarTodos() {
		List<Perfil> listaPerfis = PerfilDAO.getInstance().listar();
		assertNotNull(listaPerfis);
	}
	
	@Test
	public void testListaPermissoesDoPerfil() {
		List<Permissao> listaPermissao = PerfilDAO.getInstance().listarPermissoes(1);
		
		System.out.println(listaPermissao.size());
		System.out.println(listaPermissao.get(0).getNomePermissao());
	}
	
	@Test
	public void testGatribuirPermissaoAUmPerfil() {
//		Perfil perfil = new Perfil("PerfilTestAtribuicao");
//		PerfilDAO.getInstance().criar(perfil);
//		Permissao permissao = new Permissao("permissao1");
//		PermissaoDAO.getInstance().criar(permissao);	
		
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(2, PerfilDAO.getInstance().consultarPorId(3));
	}

	@Ignore
	public void testALimparBancoPerfil() {

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE perfil CASCADE; ALTER SEQUENCE gerador_id_perfil RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
}
