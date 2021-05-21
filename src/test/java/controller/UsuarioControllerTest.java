package controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;
import utils.HashSenha;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioControllerTest {

	@Ignore
	public void testAcleanDB() {
		DBConnection.truncateTablesAndRestartSequences();
	}

	@AfterClass
	@BeforeClass
	public static void testAlimparTestesDoController() {
		String sqlPermissao1 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoDeTesteDeCriacao';";
		String sqlPermissao2 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoDeTesteDeAlteracaoDepoisDaAlteracao';";
		String sqlPerfil1 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeCriacao';";
		String sqlPerfil2 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeAlteracaoDepoisDaAlteracao';";
		String sqlUsuario1 = "DELETE FROM usuario WHERE login = 'jonata@gmail.com';";
		String sqlUsuario2 = "DELETE FROM usuario WHERE login = 'UsuarioDeTesteDepoisDaAlteracao@gmail.com';";
		String sqlLogin1 = "DELETE FROM usuario WHERE login = 'Grijo@gmail.com';";
		String sqlAtribuicoes1 = "TRUNCATE TABLE perfil_permissao CASCADE;";
		String sqlAtribuicoes2 = "TRUNCATE TABLE usuario_perfil CASCADE;";
		String sqlAtribuicoes3 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao1';";
		String sqlAtribuicoes4 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao2';";
		String sqlAtribuicoes5 = "DELETE FROM permissao WHERE nome_permissao = 'PermissaoTesteDeAtribuicao3';";
		String sqlAtribuicoes6 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilDeTesteDeVerificacaoDePermissao';";
		String sqlAtribuicoes7 = "DELETE FROM usuario WHERE login = 'UsuarioDeTesteDeVerificacaoDePermissao@gmail.com';";
		String sqlAtribuicoes8 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilAtribuicaoTestePrimeiro';";
		String sqlAtribuicoes9 = "DELETE FROM perfil WHERE nome_perfil = 'PerfilAtribuicaoTesteSegundo';";
		String sqlAtribuicoes10 = "DELETE FROM usuario WHERE login = 'TesteAtribuicao@gmail.com';";

		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sqlPermissao1 + sqlPermissao2 + sqlPerfil1 + sqlPerfil2 + sqlUsuario1 + sqlUsuario2
							+ sqlLogin1 + sqlAtribuicoes1 + sqlAtribuicoes2 + sqlAtribuicoes3 + sqlAtribuicoes4
							+ sqlAtribuicoes5 + sqlAtribuicoes6 + sqlAtribuicoes7 + sqlAtribuicoes8 + sqlAtribuicoes9
							+ sqlAtribuicoes10)
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

	/**
	 * Ao executar esse teste, sinta-se livre para utilizar um email real mudando a
	 * string destinatario.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testNEnviarEConfirmarEmailDeConfirmacaoDeLogin() throws Exception {

		String destinatario = "Email@gmail.com"; // Seja responsável e não spame os amiguinhos. ^^
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
			DBConnection.getSession().createSQLQuery(sqlLimparUsuarioDoBanco).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
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

	@Test
	public void testRLverificarEListarPermissaoDeUmUsuario() {
		String loginUsuario = "UsuarioDeTesteDeVerificacaoDePermissao@gmail.com";
		String senhaUsuario = "244466666";
		Controller.getInstance().criarUsuario(loginUsuario, senhaUsuario);
		String nomePerfil = "PerfilDeTesteDeVerificacaoDePermissao";

		Usuario usuario = Controller.getInstance().consultarUsuario(loginUsuario);
		Perfil perfil = Controller.getInstance().consultarPerfil(nomePerfil);

		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil, null);
		DBConnection.shutdown();

		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		Permissao permissao2 = Controller.getInstance().consultarPermissao(nomePermissao2);
		Permissao permissao3 = Controller.getInstance().consultarPermissao(nomePermissao3);

		usuario = Controller.getInstance().consultarUsuario(loginUsuario);

		assertTrue(Controller.getInstance().possuiPermissoes(usuario, permissao2));
		assertFalse(Controller.getInstance().possuiPermissoes(usuario, permissao3));
	}

	@Test
	public void testSRemovendoUmPerfilDeUmUsuario() {
		String loginDoUsuario = "TesteAtribuicao@gmail.com";
		String senhaDoUsuario = "senha10";
		Controller.getInstance().criarUsuario(loginDoUsuario, senhaDoUsuario);
		Usuario usuario = Controller.getInstance().consultarUsuario(loginDoUsuario);

		String nomePerfil1 = "PerfilAtribuicaoTestePrimeiro";
		Controller.getInstance().criarPerfil(nomePerfil1);
		Perfil perfil1 = Controller.getInstance().consultarPerfil(nomePerfil1);
		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil1, LocalDate.of(2021, 05, 17));
		DBConnection.shutdown();

		String nomePerfil2 = "PerfilAtribuicaoTesteSegundo";
		Controller.getInstance().criarPerfil(nomePerfil2);
		Perfil perfil2 = Controller.getInstance().consultarPerfil(nomePerfil2);
		Controller.getInstance().atribuirPerfilAUmUsuario(usuario, perfil2, LocalDate.of(2021, 05, 19));
		DBConnection.shutdown();

		int tamanho = Controller.getInstance().consultarUsuario(loginDoUsuario).getPerfis().size();

		DBConnection.shutdown();
		Controller.getInstance().expirarTodasAsPermissoesDoSistema();
		DBConnection.shutdown();

		assertNotEquals(tamanho, Controller.getInstance().consultarUsuario(loginDoUsuario).getPerfis().size());
	}

}
