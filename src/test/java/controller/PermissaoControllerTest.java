package controller;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.entidades.Permissao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PermissaoControllerTest {

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
	public void testBCriarEConsultarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeCriacao";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorNome = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		Permissao permissaoEncontradaPorId = Controller.getInstance()
				.consultarPermissao(permissaoEncontradaPorNome.getIdPermissao());
		assertEquals(nomeDaPermissao, permissaoEncontradaPorNome.getNomePermissao(),
				permissaoEncontradaPorId.getNomePermissao());
	}

	@Test
	public void testCAlterarPermissao() {
		String nomeDaPermissao = "PermissaoDeTesteDeAlteracaoAntesDaAlteracao";
		Controller.getInstance().criarPermissao(nomeDaPermissao);
		Permissao permissaoConsultada = Controller.getInstance().consultarPermissao(nomeDaPermissao);
		String novoNomeDaPermissao = "PermissaoDeTesteDeAlteracaoDepoisDaAlteracao";
		Controller.getInstance().alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		permissaoConsultada = Controller.getInstance().consultarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(novoNomeDaPermissao, permissaoConsultada.getNomePermissao());
	}

	@Test
	public void testDDeletarPermissao() {
		int numeroDePermissoesAntesDoTeste = Controller.getInstance().listarTodasAsPermissoes().size();
		String nomeDaPermissao = "PermissaoDeTesteDeDelecao";
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
}
