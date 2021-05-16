package model.acesso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDAOTest {

	@Test
	public void testBCriarUsuarioEConsultarPorId() {
		Usuario usuarioDeTeste = new Usuario("loginTest", "senhaTest");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuarioDeTeste.getIdUsuario());
		assertEquals(usuarioDeTeste, usuarioEncontrado);
	}

	@Test
	public void testCAlterarUsuario() {
		Usuario usuarioDeTeste = new Usuario("LoginTeste2", "Cenha muito boa");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		usuarioDeTeste.setLogin("alteracaoTeste");
		UsuarioDAO.getInstance().alterar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuarioDeTeste.getIdUsuario());
		assertEquals("alteracaoTeste", usuarioEncontrado.getLogin());
	}

	@Test
	public void testDconsultarUsuarioPorLogin() {
		Usuario usuarioDeTeste = new Usuario("thiagose@gmail12.com", "123qwe123");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorLogin("thiagose@gmail12.com");
		assertEquals("thiagose@gmail12.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testEDeletarUsuario() {
		Usuario usuarioParaDeletar = new Usuario("XAu", "Tchau");
		UsuarioDAO.getInstance().criar(usuarioParaDeletar);
		int tamanhoAntesDeDeletar = UsuarioDAO.getInstance().listar().size();
		UsuarioDAO.getInstance().deletar(usuarioParaDeletar);
		assertEquals(tamanhoAntesDeDeletar - 1, UsuarioDAO.getInstance().listar().size());
		assertTrue(UsuarioDAO.getInstance().listar().size() < tamanhoAntesDeDeletar);
	}

	@Test
	public void testFAtribuirPerfilAUmUsuario() {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(2);
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(3);
		LocalDate dateDeExpiracao = LocalDate.of(2021, 05, 13);

		UsuarioPerfilId userPerfilId = new UsuarioPerfilId(usuario.getIdUsuario(), perfil.getIdPerfil());
		UsuarioPerfil usuarioPer = new UsuarioPerfil(userPerfilId, usuario, perfil, dateDeExpiracao);
//		System.out.println("Antes da atribuição");
//		usuario.getPerfis().toString();

		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(usuarioPer);
		// UsuarioDAO.getInstance().alterar(usuario);
//		System.out.println("Dps da atribuição");
//		usuario.getPerfis().toString();
	}

	@Test
	public void testGListarPermissoesDeUmUsuario() {
		List<Permissao> listaPermissao = UsuarioDAO.getInstance().listarPermissoes(2);
		
		System.out.println(listaPermissao.size());

		System.out.println("______________________ " + listaPermissao.size());
		System.out.println(listaPermissao.get(0).getNomePermissao());
	}

	@Test
	public void testTListar() {
		List<Perfil> listaPerfil = UsuarioDAO.getInstance().listarPerfis(2);

		System.out.println("______________________ " + listaPerfil.size());

		System.out.println(listaPerfil.get(0).getNomePerfil());
	}

	@Ignore
	public void testAlimparBanco() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
