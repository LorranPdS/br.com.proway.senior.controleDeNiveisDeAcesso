package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;


/**
 * 
 * @author Vitor Peres vitor.peres@senior.com.br
 * @author David Willian david.oliveira@senior.com.br
 * @author Leonardo Pereira leonardo.pereira@senior.com.br
 *        
 *         versão 2.0 -- implementação postgreSQL + jdbc
 * @author Elton F Oliveira elton.oliveira@senior.com.br
 * @author Vitor A Gehrke vitor.gehrke@senior.com.br
 */
public class UsuarioDAOTest {
	
	@Test
	public void testCriarUsuarioQualquer() {
		UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
		
		usuarioDAO.criarUsuario("ekjfajlkfbngj", "Marcos");
		usuarioDAO.criarUsuario("jsngjg", "Pedro");
		usuarioDAO.criarUsuario("ekjfajlkfbakjbgkajbgjkngj", "João");
		usuarioDAO.criarUsuario("ekjfajl54g5ag43kfbngj", "Maria");
		usuarioDAO.criarUsuario("ekjfaj684a163ga6g8lkfbngj", "Antonia");
	}
	
	
	@Test
	public void testCriarUsuarioNoBancoDeDados() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.db.limparDB("usuariostabela");
		usuarioDAO.criarUsuario("qweqweqweqweqwe", "Ricardo");
		try {
			ResultSet rs = usuarioDAO.db.executeQuery("select * from usuariostabela");
			if(rs.next()) {
				assertEquals("qweqweqweqweqwe", "Ricardo", rs.getString(3));
			} else {
				fail("Banco não acessado");
			}
			
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	
	@Test
	public void testDeletarusuarioPorLoginNoBancoDeDados() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.db.limparDB("usuariostabela");
		usuarioDAO.criarUsuario("Remover", "Elton");
		usuarioDAO.criarUsuario("Remover2", "Vitor");
		try {
			usuarioDAO.deletarUsuarioPorLogin("Elton");
			
			ResultSet rs = usuarioDAO.db.executeQuery("select * from usuariostabela");	
			if(rs.next()) {
				assertEquals(rs.getString(3), "Vitor");	
			} else {
				fail("Banco não acessado");
			}
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
	

	@Test
	public void testBuscarUsuarioPorNomeNoBancoDeDados() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.db.limparDB("usuariostabela");
		usuarioDAO.criarUsuario("irioweuriowueriouwer", "Joao");;
		Usuario pmteste = new Usuario("irioweuriowueriouwer", "Joao");
		Usuario pm = usuarioDAO.buscarUsuario("Joao");
		assertTrue(pmteste.getLoginDoUsuario().equals(pm.getLoginDoUsuario()));		
	 
	}
	
	@Test
	public void buscarTodosOsUsuariosNoBancoDeDados() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.db.limparDB("usuariostabela");
		usuarioDAO.criarUsuario("Remover", "Joao");
		usuarioDAO.criarUsuario("Adicionar", "Maria");
		usuarioDAO.criarUsuario("Mover", "José");
		Usuario umteste1 = new Usuario("Remover", "Joao");
		Usuario umteste2 = new Usuario("Adicionar", "Maria");
		Usuario umteste3 = new Usuario("Mover", "José");
		ArrayList<Usuario> listaUMteste = new ArrayList<Usuario>();
		listaUMteste.addAll(Arrays.asList(umteste1, umteste2, umteste3));
		
		ArrayList<Usuario> listaUM = usuarioDAO.buscarTodosUsuarios();
		
		assertTrue(listaUMteste.get(0).getLoginDoUsuario().equals(listaUM.get(0).getLoginDoUsuario()));		
		assertTrue(listaUMteste.get(1).getLoginDoUsuario().equals(listaUM.get(1).getLoginDoUsuario()));		
		assertTrue(listaUMteste.get(2).getLoginDoUsuario().equals(listaUM.get(2).getLoginDoUsuario()));		
	}
	
	@Test
	public void testAtualizarUsuarioNoBancoDeDados() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.db.limparDB("usuariostabela");
		usuarioDAO.criarUsuario("Remover", "Carol");
		Usuario pm = new Usuario("qweqweqwe", "Aline");
		int i = 0;
		try {
			ResultSet rs = usuarioDAO.db.executeQuery("select max(idusuario) from usuariostabela");
			if(rs.next()) {
				i = rs.getInt(1);
			}
			usuarioDAO.atualizarUsuario(i, pm);
			assertEquals(usuarioDAO.buscarUsuario(i).getLoginDoUsuario(), "Aline");
			assertEquals(usuarioDAO.buscarUsuario(i).getHashSenhaDoUsuario(), "qweqweqwe");
		} catch (SQLException e) {
			fail("Não encontrado");
		}
	}
}
