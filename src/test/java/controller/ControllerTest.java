package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.acesso.Perfil;
import model.acesso.PerfilDAO;
import model.acesso.Permissao;
import model.acesso.PermissaoDAO;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {
	
	@Test
	public void testAcleanDB() {
		DBConnection.truncateTablesAndRestartSequences();
	}
	
	@Ignore
	public void testEmail() throws Exception {
		boolean resultadoEnvioEmail = Controller.getInstance()
				.enviarEmailDeConfirmacaoDeLogin("NOMEFICTICIO@gmail.com");
		assertTrue(resultadoEnvioEmail);
	}

	@Ignore
	public void testDLogar() {
		String login = "Grijo";
		String senha = "234";
		Controller.getInstance().criarUsuario(login, senha);
		boolean logar = Controller.getInstance().logar(login, senha);
		System.out.println(logar);
	}

	@Ignore
	public void testDeletarPerfil() {
		String novoPerfil = "GERENCIA";
		Controller.getInstance().criarPerfil(novoPerfil);
		Perfil perfilCadastrado = Controller.getInstance().consultarPerfil(novoPerfil);
		assertEquals(novoPerfil, perfilCadastrado.getNomePerfil());

		Controller.getInstance().deletarPerfil(perfilCadastrado);

		perfilCadastrado = Controller.getInstance().consultarPerfil(perfilCadastrado.getIdPerfil());
		assertNull(perfilCadastrado);
	}

	@Ignore
	public void testACriarEConsultarUsuario() {
		String login = "jonata";
		String hashSenha = "123";
		Controller.getInstance().criarUsuario(login, hashSenha);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(login);
		assertEquals(login, usuarioEncontrado.getLogin());
	}
//
//	Permissao permissao = new Permissao("PermissaoParaDeletar");
//	PermissaoDAO.getInstance().criar(permissao);
//	int tamanhoAntesDeDeletar = PermissaoDAO.getInstance().listar().size();
//	PermissaoDAO.getInstance().deletar(permissao);
//	assertEquals(tamanhoAntesDeDeletar - 1, PermissaoDAO.getInstance().listar().size());

	
	@Ignore
	public void testDeletarUsuario() {
		String login = "thiagoXitado@bol.com";
		String senha = "thiago123";
		Controller.getInstance().criarUsuario(login, senha);
		int idUsuario = Controller.getInstance().consultarUsuario(login).getIdUsuario();
		Controller.getInstance().deletarUsuario(idUsuario);
		Usuario usuario = Controller.getInstance().consultarUsuario(idUsuario);
		assertNull(usuario);
	}

	@Ignore
	public void testAtribuirPerfilAUmUsuario() {
		Usuario usuario = new Usuario("Gabriel@gmail.com", "hashSenha123");
		UsuarioDAO.getInstance().criar(usuario);
		Perfil perfil = new Perfil("RH");
		PerfilDAO.getInstance().criar(perfil);
		LocalDate data = LocalDate.of(2021, 02, 04);

		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil, data);
	}

	@Ignore
	public void testBCriarEConsultarPerfil() {
		String perfil = "Administrativo";

		Controller.getInstance().criarPerfil(perfil);
		Perfil perfilEncontrado = Controller.getInstance().consultarPerfil(perfil);
		assertEquals(perfil, perfilEncontrado.getNomePerfil());
	}

	@Test
	public void testAlterarPerfil() {
		String nomePerfil = "Engenharia";
		Controller.getInstance().criarPerfil("Logistica");
		int idPerfilLogistica = Controller.getInstance().consultarPerfil("Logistica").getIdPerfil();
		Controller.getInstance().alterarPerfil(idPerfilLogistica, nomePerfil);
		Perfil perfilEncontrado = Controller.getInstance().consultarPerfil(idPerfilLogistica);
		assertEquals(nomePerfil, perfilEncontrado.getNomePerfil());
	}
	
	@Test
	public void testConsultarUsuarioPorId() {
		String usuario = "lorran@gmail";
		String senha = "123";
		Controller.getInstance().criarUsuario(usuario, senha);
		int usuarioId = UsuarioDAO.getInstance().consultarPorLogin(usuario).getIdUsuario();
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(usuarioId);
		assertEquals(usuarioId, usuarioEncontrado.getLogin());
	}

	@Test
	public void testBConsultarPerfilPorId() {
		String perfil = "ST";
		Controller.getInstance().criarPerfil(perfil);
		int idPerfil = PerfilDAO.getInstance().consultarPorNome(perfil).getIdPerfil();
		Perfil perfilEncontrado = Controller.getInstance().consultarPerfil(idPerfil);
		assertEquals(perfil, perfilEncontrado.getNomePerfil());
	}
	
	@Ignore
	public void testAlterarDadosUsuario() {
		String loginUsuario = "roberto";
		String senhaUsuario = "jaqueta";

		Controller.getInstance().criarUsuario(loginUsuario, senhaUsuario);
		Usuario usuarioEncontrado = Controller.getInstance().consultarUsuario(loginUsuario);
		assertEquals(loginUsuario, usuarioEncontrado.getLogin());

		loginUsuario = "mauricio";
		usuarioEncontrado.setLogin(loginUsuario);
		Controller.getInstance().alterarUsuario(usuarioEncontrado.getIdUsuario(), usuarioEncontrado);
		usuarioEncontrado = Controller.getInstance().consultarUsuario(loginUsuario);
		assertEquals(loginUsuario, usuarioEncontrado.getLogin());
	}

	@Ignore
	public void testBAtribuirPermissaoAUmPerfil() {
		Perfil perfilCriado = new Perfil("perfil Teste");
		PerfilDAO.getInstance().criar(perfilCriado);
		Permissao permissaoCriado = new Permissao("permissao1");
		PermissaoDAO.getInstance().criar(permissaoCriado);
		Perfil perfil = PerfilDAO.getInstance().consultarPorNome("perfil Teste");
		Permissao permissao = PermissaoDAO.getInstance().consultarPorNome("permissao1");
		Controller.getInstance().atribuirPermissaoAUmPerfil(permissao, perfil);
	}

	@Test
	public void testDCriarEConsultarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeCriação";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorNome = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorId = Controller.getInstance().consultarPermissao(permissaoEncontradaPorNome.getIdPermissao());
		assertEquals(nomeDaPermissao, permissaoEncontradaPorNome.getNomePermissao(), permissaoEncontradaPorId.getNomePermissao());
		
	}

	@Test
	public void testEAlterarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeAlteraçãoAntesDaAlteração";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoConsultada = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		String novoNomeDaPermissao = "PermissaoDeTesteDeAlteraçãoDepoisDaAlteração";
		Controller.getInstance().alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		permissaoConsultada = Controller.getInstance().consultarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(novoNomeDaPermissao, permissaoConsultada.getNomePermissao());
	}

	@Test
	public void testFdeletarPermissao() {
		int numeroDePermissoesAntesDoTeste = Controller.getInstance().listarTodasAsPermissoes().size();
		String nomeDaPermissao = "PermissaoDeTesteDeDeleção";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoASerDeletada = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		Controller.getInstance().deletarPermissao(permissaoASerDeletada.getIdPermissao());
		int numeroDePermissoesDepoisDoTeste = Controller.getInstance().listarTodasAsPermissoes().size();
		assertEquals(numeroDePermissoesAntesDoTeste, numeroDePermissoesDepoisDoTeste);
		
	}

	@Test
	public void testGListarTodasAsPermissoes() {
		int numeroDePermissoesNoBanco = 2;
		assertEquals(numeroDePermissoesNoBanco, Controller.getInstance().listarTodasAsPermissoes().size());
	}
	
	
	
	@Ignore
	public void testListarTodosPerfisNull() {
		ArrayList<Perfil> listaPerfis = Controller.getInstance().listarTodosOsPerfis();
		assertNull(listaPerfis);
	}

	@Ignore
	public void testListarTodosPerfisNotNull() {
		ArrayList<Perfil> listaPerfis = Controller.getInstance().listarTodosOsPerfis();
		assertNotNull(listaPerfis);
	}
	
	@Ignore
	public void testListarTodosUsuariosNull() {
		ArrayList<Usuario> listaUsuarios = Controller.getInstance().listarTodosOsUsuarios();
		assertNull(listaUsuarios);
	}

	@Ignore
	public void testListarTodosUsuariosNotNull() {
		ArrayList<Usuario> listaUsuarios = Controller.getInstance().listarTodosOsUsuarios();
		assertNotNull(listaUsuarios);
	}

	@Test
	public void testLverificarEListarPermissaoDeUmPerfil() {
		String nomePerfil = "PerfilDeTesteDeVerificaçãoDePermissão";
		Controller.getInstance().criarPerfil(nomePerfil);

		String nomePermissao1 = "PermissaoTesteDeAtribuicao1";
		Controller.getInstance().criarPermissao(nomePermissao1);
		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		Controller.getInstance().criarPermissao(nomePermissao2);
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		Controller.getInstance().criarPermissao(nomePermissao3);

		Perfil perfil = Controller.getInstance().consultarPerfil(nomePerfil);
		Permissao permissao1 = Controller.getInstance().consultarPermissao(nomePermissao1);
		Permissao permissao2 = Controller.getInstance().consultarPermissao(nomePermissao2);
		Permissao permissao3 = Controller.getInstance().consultarPermissao(nomePermissao3);

		Controller.getInstance().atribuirPermissaoAUmPerfil(permissao1, perfil);
		Controller.getInstance().atribuirPermissaoAUmPerfil(permissao2, perfil);
		DBConnection.shutdown();

		List<Permissao> listaPermissao = Controller.getInstance().listarPermissoesDeUmPerfil(perfil.getIdPerfil());

		System.out.println("0");
		listaPermissao.toString();

		assertEquals(2, listaPermissao.size());
		System.out.println("1");
		assertTrue(Controller.getInstance().verificarPermissao(perfil, listaPermissao.get(1)));
		System.out.println("2");
		assertFalse(Controller.getInstance().verificarPermissao(perfil, permissao3));

	}

	@Test
	public void testMLverificarEListarPermissaoDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		Controller.getInstance().criarUsuario(loginUsuario, senhaUsuario);
		String nomePerfil = "PerfilDeTesteDeVerificaçãoDePermissão";

		Usuario usuario = Controller.getInstance().consultarUsuario(loginUsuario);
		Perfil perfil = Controller.getInstance().consultarPerfil(nomePerfil);

		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil, null);
		DBConnection.shutdown();

		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		Permissao permissao2 = Controller.getInstance().consultarPermissao(nomePermissao2);
		Permissao permissao3 = Controller.getInstance().consultarPermissao(nomePermissao3);

		assertTrue(Controller.getInstance().verificarPermissao(perfil, permissao2));
		assertFalse(Controller.getInstance().verificarPermissao(perfil, permissao3));
	}

	@Ignore
	public void testXlimparBanco() {
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;")
					.executeUpdate();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE perfil CASCADE; ALTER SEQUENCE gerador_id_perfil RESTART 1;")
					.executeUpdate();
			DBConnection.getSession()
					.createSQLQuery("TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_permissao RESTART 1;")
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
