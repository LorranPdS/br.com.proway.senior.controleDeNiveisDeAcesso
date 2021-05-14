package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilDAOTest {

	@Test
	public void testASalvarPerfil() {
		Perfil perfil = new Perfil("ADMIN");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfil.getIdPerfil());
		assertEquals(perfil, perfilEncontrado);
	}

	@Test
	public void testBAtualizar() {
		Perfil perfilOriginal = new Perfil("Secretaria");
		PerfilDAO.getInstance().criar(perfilOriginal);
		perfilOriginal.setNomePerfil("Comprador");
		PerfilDAO.getInstance().alterar(perfilOriginal);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfilOriginal.getIdPerfil());
		assertEquals("Comprador", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testCDeletar() {
		Perfil perfil = new Perfil("ADMIN");
		System.out.println(perfil.toString());
		boolean resultado = PerfilDAO.getInstance().deletar(perfil);
		// List<Perfil> listaPerfisDepoisDeDeletar = PerfilDAO.getInstance().listar();
		assertTrue(resultado);
	}

	@Test
	public void testDBuscarPorNome() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		
		System.out.println(perfilEncontrado.getNomePerfil());
		assertEquals(perfil.getNomePerfil(), perfilEncontrado.getNomePerfil());
	}
	
	

	@Test
	public void testListarTodos() {
		List<Perfil> listaPerfis = PerfilDAO.getInstance().listar();
		assertNotNull(listaPerfis);
	}

	@Test
	public void testXLimparBanco() {

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession().createSQLQuery("TRUNCATE TABLE perfil CASCADE;").executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
