package controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import db.DBConnection;
import model.entidades.Perfil;
import model.entidades.Permissao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PerfilControllerTest {

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
	public void testQverificarEListarPermissaoDeUmPerfil() {
		String nomePerfil = "PerfilDeTesteDeVerificacaoDePermissao";
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
	public void testFCriarEConsultarPerfil() {
		String nomeDoPerfil = "PerfilDeTesteDeCriacao";
		Controller.getInstance().criarPerfil(nomeDoPerfil);
		Perfil perfilEncontradoPorNome = Controller.getInstance().consultarPerfil(nomeDoPerfil);
		Perfil perfilEncontradoPorId = Controller.getInstance().consultarPerfil(perfilEncontradoPorNome.getIdPerfil());
		assertEquals(nomeDoPerfil, perfilEncontradoPorNome.getNomePerfil(), perfilEncontradoPorId.getNomePerfil());
	}

	@Test
	public void testGAlterarPerfil() {
		String nomePerfil = "PerfilDeTesteDeAlteracaoAntesDaAlteracao";
		Controller.getInstance().criarPerfil(nomePerfil);
		Perfil perfilConsultado = Controller.getInstance().consultarPerfil(nomePerfil);
		String novoNomeDoPerfil = "PerfilDeTesteDeAlteracaoDepoisDaAlteracao";
		Controller.getInstance().alterarPerfil(perfilConsultado.getIdPerfil(), novoNomeDoPerfil);
		perfilConsultado = Controller.getInstance().consultarPerfil(perfilConsultado.getIdPerfil());
		assertEquals(novoNomeDoPerfil, perfilConsultado.getNomePerfil());
	}

	@Test
	public void testHDeletarPerfil() {
		int numeroDePerfisAntesDoTeste = Controller.getInstance().listarTodosOsPerfis().size();
		String nomeDoPerfil = "PerfilDeTesteDeDelecao";
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

}
