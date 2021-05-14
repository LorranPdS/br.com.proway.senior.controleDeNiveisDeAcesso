package model.acesso;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissaoDAOTest {

	@Ignore
	public void testAlimparBancoPermissao() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_permissao RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Test
	public void testBcriarPermissao() {
		Permissao permissao = new Permissao("permissao1");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("permissao1");
		assertEquals(permissao, permissaoEncontrada);
	}

	@Test
	public void testCconsultarPermissoesPorNome() {
		Permissao permissao = new Permissao("ThiagoXitado");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("ThiagoXitado");
		assertEquals("ThiagoXitado", permissaoEncontrada.getNomePermissao());
	}

	@Test
	public void testDlterarPermissao() {
		Permissao permissao = new Permissao("Permissao2");
		PermissaoDAO.getInstance().criar(permissao);
		permissao.setNomePermissao("alteracaoTeste");
		PermissaoDAO.getInstance().alterar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorId(permissao.getIdPermissao());
		assertEquals("alteracaoTeste", permissaoEncontrada.getNomePermissao());
	}
	
	@Test
	public void testEDeletarPermissao() {
		Permissao permissao = new Permissao("permissao3");
		PermissaoDAO.getInstance().criar(permissao);
		int tamanhoAntesDeDeletar = PermissaoDAO.getInstance().listar().size();
		PermissaoDAO.getInstance().deletar(permissao);
		assertEquals(tamanhoAntesDeDeletar - 1, PermissaoDAO.getInstance().listar().size());
	}
}
