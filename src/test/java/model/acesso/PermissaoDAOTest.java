package model.acesso;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import db.DBConnection;

public class PermissaoDAOTest {

	@Test
	public void testAlimparBancoPermissao() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
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
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorId(1);
		assertEquals(permissao, permissaoEncontrada);
	}
}
