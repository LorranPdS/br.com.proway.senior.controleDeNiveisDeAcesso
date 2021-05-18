package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.acesso.Perfil;
import model.acesso.Permissao;
import model.acesso.Usuario;
import utils.HashSenha;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

	@Ignore
	public void testAcleanDB() {
		DBConnection.truncateTablesAndRestartSequences();
	}

	@BeforeClass
	@AfterClass
	public static void testAlimparTestesDoController() {
		String sqlPermissao1 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoDeTesteDeCriação';";
		String sqlPermissao2 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoDeTesteDeAlteraçãoDepoisDaAlteração';";
		String sqlPerfil1 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeCriação';";
		String sqlPerfil2 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeAlteraçãoDepoisDaAlteração';";
		String sqlUsuario1 = "DELETE FROM usuario WHERE login = 'jonata@gmail.com';";
		String sqlUsuario2 = "DELETE FROM usuario WHERE login = 'UsuarioDeTesteDepoisDaAlteracao@gmail.com';";
		String sqlLogin1 = "DELETE FROM usuario WHERE login = 'Grijo@gmail.com';";
		String sqlAtribuicoes1 = "TRUNCATE TABLE perfil_permissao CASCADE;";
		String sqlAtribuicoes2 = "TRUNCATE TABLE usuario_perfil CASCADE;";
		String sqlAtribuicoes3 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao1';";
		String sqlAtribuicoes4 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao2';";
		String sqlAtribuicoes5 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao3';";
		String sqlAtribuicoes6 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeVerificaçãoDePermissão';";
		String sqlAtribuicoes7 = "DELETE FROM usuario WHERE login = 'UsuarioDeTesteDeVerificacaoDePermissao@gmail.com';";

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sqlPermissao1 + sqlPermissao2 + sqlPerfil1 + sqlPerfil2 + sqlUsuario1 + sqlUsuario2
							+ sqlLogin1 + sqlAtribuicoes1 + sqlAtribuicoes2 + sqlAtribuicoes3 + sqlAtribuicoes4
							+ sqlAtribuicoes5 + sqlAtribuicoes6 + sqlAtribuicoes7)
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Test
	public void testBCriarEConsultarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeCriação";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorNome = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorId = Controller.getInstance()
				.consultarPermissao(permissaoEncontradaPorNome.getIdPermissao());
		assertEquals(nomeDaPermissao, permissaoEncontradaPorNome.getNomePermissao(),
				permissaoEncontradaPorId.getNomePermissao());
	}

	@Test
	public void testCAlterarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeAlteraçãoAntesDaAlteração";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoConsultada = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		String novoNomeDaPermissao = "PermissaoDeTesteDeAlteraçãoDepoisDaAlteração";
		Controller.getInstance().alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		permissaoConsultada = Controller.getInstance().consultarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(novoNomeDaPermissao, permissaoConsultada.getNomePermissao());
	}

	@Test
	public void testDDeletarPermissao() {
		int numeroDePermissoesAntesDoTeste = Controller.getInstance().listarTodasAsPermissoes().size();
		String nomeDaPermissao = "PermissaoDeTesteDeDeleção";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoASerDeletada = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		Controller.getInstance().deletarPermissao(permissaoASerDeletada.getIdPermissao());
		int numeroDePermissoesDepoisDoTeste = Controller.getInstance().listarTodasAsPermissoes().size();
		assertEquals(numeroDePermissoesAntesDoTeste, numeroDePermissoesDepoisDoTeste);
	}

	@Test
	public void testEListarTodasAsPermissoes() {
		int numeroDePermissoesNoBanco = 2;
		assertEquals(numeroDePermissoesNoBanco, Controller.getInstance().listarTodasAsPermissoes().size());
	}

	@Test
	public void testFCriarEConsultarPerfil() {
		String nomeDoPerfil = "PerfilDeTesteDeCriação";
		Controller.getInstance().criarPerfil(nomeDoPerfil);
		Perfil perfilEncontradoPorNome = Controller.getInstance().consultarPerfil(nomeDoPerfil);
		Perfil perfilEncontradoPorId = Controller.getInstance().consultarPerfil(perfilEncontradoPorNome.getIdPerfil());
		assertEquals(nomeDoPerfil, perfilEncontradoPorNome.getNomePerfil(), perfilEncontradoPorId.getNomePerfil());
	}

	@Test
	public void testGAlterarPerfil() {
		String nomePerfil = "PerfilDeTesteDeAlteraçãoAntesDaAlteração";
		Controller.getInstance().criarPerfil(nomePerfil);
		Perfil perfilConsultado = Controller.getInstance().consultarPerfil(nomePerfil);
		String novoNomeDoPerfil = "PerfilDeTesteDeAlteraçãoDepoisDaAlteração";
		Controller.getInstance().alterarPerfil(perfilConsultado.getIdPerfil(), novoNomeDoPerfil);
		perfilConsultado = Controller.getInstance().consultarPerfil(perfilConsultado.getIdPerfil());
		assertEquals(novoNomeDoPerfil, perfilConsultado.getNomePerfil());
	}

	@Test
	public void testHDeletarPerfil() {
		int numeroDePerfisAntesDoTeste = Controller.getInstance().listarTodosOsPerfis().size();
		String nomeDoPerfil = "PerfilDeTesteDeDeleção";
		Controller.getInstance().criarPerfil(nomeDoPerfil);
		Perfil perfilASerDeletado = Controller.getInstance().consultarPerfil(nomeDoPerfil);
		Controller.getInstance().deletarPerfil(perfilASerDeletado.getIdPerfil());
		int numeroDePerfisDepoisDoTeste = Controller.getInstance().listarTodosOsPerfis().size();
		assertEquals(numeroDePerfisAntesDoTeste, numeroDePerfisDepoisDoTeste);
	}

	@Test
	public void testIListarTodosPerfis() {
		int numeroDePerfisNoBanco = 2;
		assertEquals(numeroDePerfisNoBanco, Controller.getInstance().listarTodosOsPerfis().size());
	}

	@Test
	public void testJCriarEConsultarUsuario() {
		String loginDoUsuario = "jonata@gmail.com";
		String senhaDoUsuario = "123";
		Controller.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);
		Usuario usuarioEncontradoPorNome = Controller.getInstance().consultarUsuario(loginDoUsuario);
		Usuario usuarioEncontradoPorId = Controller.getInstance()
				.consultarUsuario(usuarioEncontradoPorNome.getIdUsuario());
		assertEquals(loginDoUsuario, usuarioEncontradoPorNome.getLogin(), usuarioEncontradoPorId.getLogin());
	}

	@Test
	public void testKAlterarUsuario() {
		String loginDoUsuario = "UsuarioDeTesteAntesDaAlteracao@gmail.com";
		String senhaDoUsuario = "6666666";
		Controller.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);
		Usuario usuarioConsultado = Controller.getInstance().consultarUsuario(loginDoUsuario);
		String novoLoginDoUsuario = "UsuarioDeTesteDepoisDaAlteracao@gmail.com";
		String novaSenhaDoUsuario = "9999999";
		Controller.getInstance().alterarUsuario(usuarioConsultado.getIdUsuario(), novoLoginDoUsuario,
				novaSenhaDoUsuario);
		usuarioConsultado = Controller.getInstance().consultarUsuario(usuarioConsultado.getIdUsuario());
		assertEquals(novoLoginDoUsuario, usuarioConsultado.getLogin());
		assertEquals(HashSenha.criptografarSenha(novoLoginDoUsuario, novaSenhaDoUsuario),
				usuarioConsultado.getHashSenha());
	}

	@Test
	public void testLDeletarUsuario() {
		int numeroDeUsuariosAntesDoTeste = Controller.getInstance().listarTodosOsUsuarios().size();
		String loginDoUsuario = "UsuarioDeTesteDeDelecao@gmail.com";
		String senhaDoUsuario = "123123123";
		Controller.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);
		Usuario usuarioASerDeletado = Controller.getInstance().consultarUsuario(loginDoUsuario);
		Controller.getInstance().deletarUsuario(usuarioASerDeletado.getIdUsuario());
		int numeroDeUsuariosDepoisDoTeste = Controller.getInstance().listarTodosOsUsuarios().size();
		assertEquals(numeroDeUsuariosAntesDoTeste, numeroDeUsuariosDepoisDoTeste);
	}

	@Test
	public void testMListarTodosUsuarios() {
		int numeroDeUsuariosNoBanco = 2;
		assertEquals(numeroDeUsuariosNoBanco, Controller.getInstance().listarTodosOsUsuarios().size());
	}

	/**
	 * Ao executar esse teste, sinta-se livre para utilizar um email real mudando a string destinatario.
	 * @throws Exception
	 */
	@Test
	public void testNEnviarEConfirmarEmailDeConfirmacaoDeLogin() throws Exception {
		
		String destinatario = "EmailFicticio@gmail.com"; // Seja responsável e não spame os amiguinhos. ^^
		String senha = "123";
		Controller.getInstance().criarUsuario(destinatario, senha);
		
		boolean resultadoEnvioEmail = Controller.getInstance().enviarEmailDeConfirmacaoDeLogin(destinatario);
		assertTrue(resultadoEnvioEmail);
		
		Usuario u = Controller.getInstance().consultarUsuario(destinatario);
		Integer codigoDeConfirmacao = u.getUltimoCodigo2FA();
		assertTrue(Controller.getInstance().confirmarCodigoDeConfirmacao(destinatario, codigoDeConfirmacao));
		
		// Limpar usuario do banco pós-teste
		String sqlLimparUsuarioDoBanco = "DELETE FROM usuario WHERE login = '" + destinatario + "';";
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sqlLimparUsuarioDoBanco)
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPLogin() {
		String loginExistente = "Grijo@gmail.com";
		String loginInexistente = "Grijo23@gmail.com";
		String senhaCorreta = "234";
		String senhaIncorreta = "123";
		Controller.getInstance().criarUsuario(loginExistente, senhaCorreta);
		boolean loginPermitido = Controller.getInstance().logar(loginExistente, senhaCorreta);
		boolean loginProibidoPorSenhaIncorreta = Controller.getInstance().logar(loginExistente, senhaIncorreta);
		boolean loginProibidoPorLoginInexistente = Controller.getInstance().logar(loginInexistente, senhaCorreta);
		assertTrue(loginPermitido);
		assertFalse(loginProibidoPorSenhaIncorreta);
		assertFalse(loginProibidoPorLoginInexistente);
	}

	@Test
	public void testQverificarEListarPermissaoDeUmPerfil() {
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
	public void testRLverificarEListarPermissaoDeUmUsuario() {
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

}
