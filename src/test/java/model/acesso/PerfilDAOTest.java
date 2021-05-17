package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilDAOTest {

	@Ignore
	public void testA() {
		DBConnection.truncateTablesAndRestartSequences();
	}

	@Test
	public void testXlimparPerfisDeTeste() {		
		String sql1 = "DELETE FROM perfil WHERE nome_perfil = 'Comprador';";
		String sql2 = "DELETE FROM perfil WHERE nome_perfil = 'Vendedor';";
		String sql3 = "DELETE FROM perfil WHERE nome_perfil = 'ADMIN';";
		String sql4 = "TRUNCATE TABLE perfil_permissao CASCADE;";
		String sql5 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao1';";
		String sql6 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao2';";
		String sql7 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilTestAtribuicao';";

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession().createSQLQuery(sql1 + sql2 + sql3 + sql4 + sql5 + sql6 + sql7).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Test
	public void testBSalvarPerfil() {
		Perfil perfil = new Perfil("ADMIN");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfil.getIdPerfil());
		assertEquals(perfil, perfilEncontrado);
	}

	@Test
	public void testCAtualizarUmPerfil() {
		Perfil perfilOriginal = new Perfil("Secretaria");
		PerfilDAO.getInstance().criar(perfilOriginal);
		perfilOriginal.setNomePerfil("Comprador");
		PerfilDAO.getInstance().alterar(perfilOriginal);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorId(perfilOriginal.getIdPerfil());
		assertEquals("Comprador", perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testDDeletarUmPerfil() {
		Perfil perfil = new Perfil("PerfilASerDeletado");
		System.out.println(perfil.toString());
		boolean resultado = PerfilDAO.getInstance().deletar(perfil);
		assertTrue(resultado);
	}

	@Test
	public void testEBuscarPerfilPorNome() {
		Perfil perfil = new Perfil("Vendedor");
		PerfilDAO.getInstance().criar(perfil);
		Perfil perfilEncontrado = PerfilDAO.getInstance().consultarPorNome("Vendedor");

		System.out.println(perfilEncontrado.getNomePerfil());
		assertEquals(perfil.getNomePerfil(), perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testFListarTodosOsPerfis() {
		DBConnection.shutdown();
		List<Perfil> listaPerfis = PerfilDAO.getInstance().listar();
		assertEquals(3, listaPerfis.size());
	}

	@Test
	public void testGAtribuirPermissaoAUmPerfilEListarPermissoes() {
		Perfil perfil = new Perfil("PerfilTestAtribuicao");
		PerfilDAO.getInstance().criar(perfil);
		Permissao permissao1 = new Permissao("PermissaoTesteDeAtribuicao1");
		PermissaoDAO.getInstance().criar(permissao1);
		Permissao permissao2 = new Permissao("PermissaoTesteDeAtribuicao2");
		PermissaoDAO.getInstance().criar(permissao2);

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao1);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao2);

		List<Permissao> listaPermissao = PerfilDAO.getInstance().listarPermissoes(perfil.getIdPerfil());
//		System.out.println("---- TAMANHO LISTA PERMISSOES DO PERFIL ---- " + listaPermissao.size());
//		for (Permissao permissao : listaPermissao) {
//			System.out.println("----Perfis: " + permissao.getNomePermissao());
//		}

		assertEquals(2, listaPermissao.size());
	}

}
