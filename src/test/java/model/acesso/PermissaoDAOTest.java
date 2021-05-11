package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PermissaoDAOTest {
	
	@Test
	public void criarPermissoes() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.criarPermissao("Remover");
		permissaoDAO.criarPermissao("Adicionar");
		permissaoDAO.criarPermissao("Limpar");
		permissaoDAO.criarPermissao("Lavar");
		permissaoDAO.criarPermissao("Dormir");
	}
	
	@Test
	public void criarPermissaoNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		try {
			ResultSet rs = permissaoDAO.db.executeQuery("select * from permissoestabela");
			if(rs.next()) {
				assertEquals("Remover", rs.getString(2));
			} else {
				fail("Banco não acessado");
			}
			
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void deletarPermissaoNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		permissaoDAO.criarPermissao("Remover2");
		int i = 0;
		try {
			ResultSet rs = permissaoDAO.db.executeQuery("select max(idpermissao) from permissoestabela");
			if(rs.next()) {
				i = rs.getInt(1);
			} else {
				fail("Banco não acessado");
			}
			permissaoDAO.deletarPermissao(i);
			
			rs = permissaoDAO.db.executeQuery("select * from permissoestabela");
			if(rs.next()) {
				assertTrue(Integer.parseInt(rs.getString(1)) < i);		
				assertEquals(rs.getString(2), "Remover");
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void buscarPermissaoNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		Permissao pmteste = new Permissao("Remover");
		int i = 0;
		try {
			ResultSet rs = permissaoDAO.db.executeQuery("select max(idpermissao) from permissoestabela");
			if(rs.next()) {
				i = rs.getInt(1);
				Permissao pm = permissaoDAO.buscarPermissao(i);
				assertTrue(pmteste.getNomeDaPermissao().equals(pm.getNomeDaPermissao()));	
			} else {
				fail("Banco não acessado");
			}
				
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void buscarPermissaoPorNomeNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		Permissao pmteste = new Permissao("Remover");
		Permissao pm = permissaoDAO.buscarPermissao("Remover");
		assertTrue(pmteste.getNomeDaPermissao().equals(pm.getNomeDaPermissao()));		
	 
	}
	
	@Test
	public void buscarTodasAsPermissoesNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		permissaoDAO.criarPermissao("Adicionar");
		permissaoDAO.criarPermissao("Mover");
		Permissao pmteste1 = new Permissao("Remover");
		Permissao pmteste2 = new Permissao("Adicionar");
		Permissao pmteste3 = new Permissao("Mover");
		ArrayList<Permissao> listaPMteste = new ArrayList<Permissao>();
		listaPMteste.addAll(Arrays.asList(pmteste1, pmteste2, pmteste3));
		
		ArrayList<Permissao> listaPM = permissaoDAO.buscarTodasAsPermissoes();
		
		assertTrue(listaPMteste.get(0).getNomeDaPermissao().equals(listaPM.get(0).getNomeDaPermissao()));		
		assertTrue(listaPMteste.get(1).getNomeDaPermissao().equals(listaPM.get(1).getNomeDaPermissao()));		
		assertTrue(listaPMteste.get(2).getNomeDaPermissao().equals(listaPM.get(2).getNomeDaPermissao()));		
	}
	
	@Test
	public void deletarPermissaoPorNomeNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		permissaoDAO.criarPermissao("Remover2");
		try {
			permissaoDAO.deletarPermissao("Remover2");
			
			ResultSet rs = permissaoDAO.db.executeQuery("select * from permissoestabela");	
			if(rs.next()) {
				assertEquals(rs.getString(2), "Remover");
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void atualizarPermissaoNoBancoDeDados() {
		PermissaoDAO permissaoDAO = new PermissaoDAO();
		permissaoDAO.db.limparDB("permissoestabela");
		permissaoDAO.criarPermissao("Remover");
		Permissao pm = new Permissao("Adicionar");
		int i = 0;
		try {
			ResultSet rs = permissaoDAO.db.executeQuery("select max(idpermissao) from permissoestabela");
			if(rs.next()) {
				i = rs.getInt(1);
			} else {
				fail("Banco não acessado");
			}
			permissaoDAO.atualizarPermissao(i, pm);
			assertEquals(permissaoDAO.buscarPermissao(i).getNomeDaPermissao(), "Adicionar");
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
}
