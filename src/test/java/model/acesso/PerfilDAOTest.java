package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PerfilDAOTest {
	
	@Test
	public void testCriarPerfilQualquer() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.criarPerfilVazio("Vale transporte");
		perfilDAO.criarPerfilVazio("vale merenda");
		perfilDAO.criarPerfilVazio("vale vale");
		perfilDAO.criarPerfilVazio("vale Maria");
		perfilDAO.criarPerfilVazio("vale gerencia");
	}

	@Test
	public void criarPerfilVazioNoBancoDeDados() {
		PerfilDAO perfilDAO = new PerfilDAO();
		
		perfilDAO.db.limparDB("perfilTabela");
		perfilDAO.criarPerfilVazio("Recursos Humanos");
		
		try {
			ResultSet rs = perfilDAO.db.executeQuery("select * from perfilTabela");
			if(rs.next()) {
				assertEquals("Recursos Humanos", rs.getString(2));
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void criarPerfilVazioTemporario() {
		PerfilDAO perfilDAO = new PerfilDAO();		
		perfilDAO.db.limparDB("perfilTabela");
		
		Perfil perfil = new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false);
		perfilDAO.criarPerfilVazioTemporario(perfil);
		
		try {
			ResultSet rs = perfilDAO.db.executeQuery("select * from perfilTabela");
			if(rs.next()) {
				assertEquals("Recursos Humanos", rs.getString(2));
				assertEquals(LocalDate.of(2000, 05, 03).toString(), rs.getString(3));
				assertEquals(LocalDate.of(2000, 05, 03).toString(), rs.getString(3));
				assertEquals(LocalDate.of(2000, 05, 03).toString(), rs.getString(4));
				assertEquals("f", rs.getString(5));
			} else {
				fail("Banco não acessado");
			}
			
		} catch (SQLException e) {
			fail("Não encontrado");
		}		
	}
	
	@Test
	public void deletarPerfilNoBancoDeDados() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");
		
		perfilDAO.criarPerfilVazio("Recursos Humanos");
		perfilDAO.criarPerfilVazio("Financeiro");
		
		int i = 0;
		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idPerfil) from perfilTabela");
			if(rs.next()) {
				i = rs.getInt(1);
			} else {
				fail("Banco não acessado");
			}
			perfilDAO.deletarPerfil(i);
			
			rs = perfilDAO.db.executeQuery("select * from perfilTabela");	
			if(rs.next()) {
				assertTrue(Integer.parseInt(rs.getString("idperfil")) < i);		
				assertEquals(rs.getString(2), "Recursos Humanos");	
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void atualizarPerfilNoBancoDeDados() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");
		
		Perfil perfil = new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false);
		perfilDAO.criarPerfilVazioTemporario(perfil);
		
		Perfil perfilAtualizado = new Perfil("RH", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false);
		
		int i = 0;
		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idperfil) from perfilTabela");
			if(rs.next()) {
				i = rs.getInt(1);
			} else {
				fail("Banco não acessado");
			}
			perfilDAO.atualizarPerfil(i, perfilAtualizado);
			assertEquals(perfilDAO.buscarPerfil(i).getNomeDoPerfil(), "RH");
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
		

	@Test
	public void buscarPerfil() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");
		
		Perfil perfil = new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false);
		perfilDAO.criarPerfilVazioTemporario(perfil);

		int i = 0;
		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idperfil) from perfilTabela");
			if(rs.next()) {
				i = rs.getInt(1);
			} else {
				fail("Banco não acessado");
			}
			Perfil pm = perfilDAO.buscarPerfil(i);
			
			assertTrue(perfil.getNomeDoPerfil().equals(pm.getNomeDoPerfil()));		
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void buscarPerfilPornomePerfil() {

		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");
		
		Perfil perfil = new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false);
		perfilDAO.criarPerfilVazioTemporario(perfil);

		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idperfil) from perfilTabela");
			if(rs.next()) {
				Perfil pm = perfilDAO.buscarPerfil(perfil.getNomeDoPerfil());
				assertTrue(perfil.getNomeDoPerfil().equals(pm.getNomeDoPerfil()));		
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}

	
	@Test
	public void buscarPerfilPorStatus() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");

		perfilDAO.criarPerfilVazioTemporario(new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Cadastro", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), true));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Férias", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Controle de Ponto", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Financeiro", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), true));

		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idperfil) from perfilTabela");
			if(rs.next()) {
				ArrayList<Perfil> pmLista = perfilDAO.buscarPerfil(false);
				assertEquals(pmLista.size(), 3);
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void buscarTodasAsPerfil() {
		PerfilDAO perfilDAO = new PerfilDAO();
		perfilDAO.db.limparDB("perfilTabela");

		perfilDAO.criarPerfilVazioTemporario(new Perfil("Recursos Humanos", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Cadastro", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), true));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Férias", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Controle de Ponto", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), false));
		perfilDAO.criarPerfilVazioTemporario(new Perfil("Financeiro", LocalDate.of(2000, 05, 03), LocalDate.of(2000, 05, 03), true));

		try {
			ResultSet rs = perfilDAO.db.executeQuery("select max(idperfil) from perfilTabela");
			if(rs.next()) {
				ArrayList<Perfil> pmLista = perfilDAO.buscarTodosOsPerfis();
				assertEquals(pmLista.size(), 5);
			} else {
				fail("Banco não acessado");
			}
			
			
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
}
