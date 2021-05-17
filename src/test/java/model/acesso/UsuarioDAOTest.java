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

	@Test
	public void testAlimparUsuariosDeTeste() {
		String sql1 = "DELETE FROM usuario WHERE login = 'Thiago@gmail.com';";
		String sql2 = "DELETE FROM usuario WHERE login = 'Luiz@gmail.com';";
		String sql3 = "DELETE FROM usuario WHERE login = 'Joao@gmail.com';";
		String sql4 = "TRUNCATE TABLE perfil_permissao CASCADE;";
		String sql5 = "TRUNCATE TABLE usuario_perfil CASCADE;";
		String sql6 = "DELETE FROM permissao WHERE nome_permissao = 'Viver';";
		String sql7 = "DELETE FROM permissao WHERE nome_permissao = 'Comer';";
		String sql8 = "DELETE FROM permissao WHERE nome_permissao = 'Morrer';";
		String sql9 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilAtribuido1';";
		String sql10 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilAtribuido2';";
		String sql11 = "DELETE FROM usuario WHERE login = 'UsuarioTesteDeAtribuicaoDePerfil@gmail.com';";

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sql1 + sql2 + sql3 + sql4 + sql5 + sql6 + sql7 + sql8 + sql9 + sql10 + sql11)
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

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
	public void testEAtribuirPerfilAUmUsuarioEListarPerfis() {
		Usuario usuario = new Usuario("UsuarioTesteDeAtribuicaoDePerfil@gmail.com", "senha5");
		UsuarioDAO.getInstance().criar(usuario);
		Perfil perfil1 = new Perfil("PerfilAtribuido1");
		PerfilDAO.getInstance().criar(perfil1);
		LocalDate dateDeExpiracao = LocalDate.of(2021, 05, 13);
		
		Integer idUsuario = usuario.getIdUsuario();
		System.out.println("1");
		UsuarioPerfilId PK1 = new UsuarioPerfilId(idUsuario, perfil1.getIdPerfil());
		UsuarioPerfil UP1 = new UsuarioPerfil(PK1, usuario, perfil1, dateDeExpiracao);
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(UP1);
		System.out.println("2");
		
		DBConnection.shutdown();
		
		Perfil perfil2 = new Perfil("PerfilAtribuido2");
		PerfilDAO.getInstance().criar(perfil2);
		usuario = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeAtribuicaoDePerfil@gmail.com");
		idUsuario = usuario.getIdUsuario();
		UsuarioPerfilId PK2 = new UsuarioPerfilId(idUsuario, perfil2.getIdPerfil());
		UsuarioPerfil UP2 = new UsuarioPerfil(PK2, usuario, perfil2, dateDeExpiracao);
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(UP2);
		
		DBConnection.shutdown();
		System.out.println("3");
		usuario = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeAtribuicaoDePerfil@gmail.com");
		idUsuario = usuario.getIdUsuario();
		//Integer idUsuarioDps = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeAtribuicaoDePerfil@gmail.com").getIdUsuario();
		List<Perfil> listaDePerfis = UsuarioDAO.getInstance().listarPerfis(idUsuario);
		assertEquals(2, listaDePerfis.size());

	}
	

	@Ignore
	public void testGListarPermissoesDeUmUsuario() {
		Permissao p1 = new Permissao("Viver");
		PermissaoDAO.getInstance().criar(p1);
		Permissao p2 = new Permissao("Comer");
		PermissaoDAO.getInstance().criar(p2);
		Permissao p3 = new Permissao("Morrer");
		PermissaoDAO.getInstance().criar(p3);

		Perfil perfil1 = PerfilDAO.getInstance().consultarPorNome("PerfilAtribuido1");
		Perfil perfil2 = PerfilDAO.getInstance().consultarPorNome("PerfilAtribuido2");

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil1, p1);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil1, p2);

		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil2, p1);
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil2, p3);

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeAtribuicaoDePerfil@gmail.com");

		List<Permissao> listaPermissao = UsuarioDAO.getInstance().listarPermissoes(usuario.getIdUsuario());

		System.out.println("---- TAMANHO LISTA PERMISSOES ---- " + listaPermissao.size());
		for (Permissao permissao : listaPermissao) {
			System.out.println("---- Permissoes: " + permissao.getNomePermissao());
		}

		assertEquals(listaPermissao, 4);

	}

}
