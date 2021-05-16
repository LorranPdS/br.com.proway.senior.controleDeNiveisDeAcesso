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
	public void testACriarUsuarioEConsultarPorId() {
		Usuario usuarioDeTeste = new Usuario("Thiago@gmail.com", "senha1");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuarioDeTeste.getIdUsuario());
		assertEquals(usuarioDeTeste, usuarioEncontrado);
	}

	@Test
	public void testBAlterarUsuario() {
		Usuario usuarioDeTeste = new Usuario("Errado@gmail.com", "senha2");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		usuarioDeTeste.setLogin("Luiz@gmail.com");
		UsuarioDAO.getInstance().alterar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorId(usuarioDeTeste.getIdUsuario());
		assertEquals("Luiz@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testCconsultarUsuarioPorLogin() {
		Usuario usuarioDeTeste = new Usuario("Joao@gmail.com", "senha3");
		UsuarioDAO.getInstance().criar(usuarioDeTeste);
		Usuario usuarioEncontrado = UsuarioDAO.getInstance().consultarPorLogin("Joao@gmail.com");
		assertEquals("Joao@gmail.com", usuarioEncontrado.getLogin());
	}

	@Test
	public void testDDeletarUsuario() {
		Usuario usuarioParaDeletar = new Usuario("Marco@gmail.com", "senha4");
		UsuarioDAO.getInstance().criar(usuarioParaDeletar);
		int tamanhoAntesDeDeletar = UsuarioDAO.getInstance().listar().size();
		UsuarioDAO.getInstance().deletar(usuarioParaDeletar);
		assertEquals(tamanhoAntesDeDeletar - 1, UsuarioDAO.getInstance().listar().size());
		assertTrue(UsuarioDAO.getInstance().listar().size() < tamanhoAntesDeDeletar);
	}

	@Test
	public void testEAtribuirPerfilAUmUsuario() {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(2);
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(3);
		LocalDate dateDeExpiracao = LocalDate.of(2021, 05, 13);

		UsuarioPerfilId userPerfilId = new UsuarioPerfilId(usuario.getIdUsuario(), perfil.getIdPerfil());
		UsuarioPerfil usuarioPer = new UsuarioPerfil(userPerfilId, usuario, perfil, dateDeExpiracao);

		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(usuarioPer);
	}

	@Test
	public void testGListarPermissoesDeUmUsuario() {
		List<Permissao> listaPermissao = UsuarioDAO.getInstance().listarPermissoes(2);
		
		System.out.println("---- TAMANHO LISTA PERMISSOES ---- " + listaPermissao.size());
		for (Permissao permissao : listaPermissao) {
			System.out.println("---- Permissoes: "+permissao.getNomePermissao());
		}

	}

	@Test
	public void testFListar() {
		List<Perfil> listaPerfil = UsuarioDAO.getInstance().listarPerfis(2);

		System.out.println("---- TAMANHO LISTA PERFIS ---- " + listaPerfil.size());
		for (Perfil perfil : listaPerfil) {
			System.out.println("---- Perfis: "+perfil.getNomePerfil());
		}
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
