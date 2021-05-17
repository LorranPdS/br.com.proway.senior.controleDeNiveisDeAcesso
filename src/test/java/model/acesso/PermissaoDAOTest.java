package model.acesso;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissaoDAOTest {

	
	@Test
	public void testXlimparPermissoesDeTeste() {
		String sql1 = "DELETE FROM permissao WHERE nome_permissao = 'Visualizar';";
		String sql2 = "DELETE FROM permissao WHERE nome_permissao = 'Alterar';";
		String sql3 = "DELETE FROM permissao WHERE nome_permissao = 'Remover';";
		
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sql1 + sql2 + sql3)
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Test
	public void testBcriarPermissao() {
		Permissao permissao = new Permissao("Visualizar");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("Visualizar");
		assertEquals(permissao, permissaoEncontrada);
	}

	@Test
	public void testCconsultarPermissoesPorNome() {
		Permissao permissao = new Permissao("Alterar");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("Alterar");
		assertEquals("Alterar", permissaoEncontrada.getNomePermissao());
	}

	@Test
	public void testDlterarPermissao() {
		Permissao permissao = new Permissao("PermissaoErrada");
		PermissaoDAO.getInstance().criar(permissao);
		permissao.setNomePermissao("Remover");
		PermissaoDAO.getInstance().alterar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorId(permissao.getIdPermissao());
		assertEquals("Remover", permissaoEncontrada.getNomePermissao());
	}
	
	@Test
	public void testEDeletarPermissao() {
		Permissao permissao = new Permissao("PermissaoParaDeletar");
		PermissaoDAO.getInstance().criar(permissao);
		int tamanhoAntesDeDeletar = PermissaoDAO.getInstance().listar().size();
		PermissaoDAO.getInstance().deletar(permissao);
		assertEquals(tamanhoAntesDeDeletar - 1, PermissaoDAO.getInstance().listar().size());
	}
	
	@Test
	public void testVerificarPermissao() {
		
	}
}
