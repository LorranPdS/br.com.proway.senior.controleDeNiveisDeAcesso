package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PerfilController;
import controller.controllers.PermissaoController;
import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilControllerTest {

	PermissaoController controllerPermissao = new PermissaoController();

//	@AfterEach
//	@BeforeEach
//	public void deletarTudo() {
//		PerfilController.getInstance().deletarTodos();
//		controllerPermissao.deletarTodos();
//	}

	@Test
	public void testVerificarEListarPermissaoDeUmPerfil() {
		String nomePerfil = "PerfilDeTesteDeVerificacaoDePermissao";
		PerfilController.getInstance().criarPerfil(nomePerfil);

		String nomePermissao1 = "PermissaoTesteDeAtribuicao1";
		controllerPermissao.criarPermissao(nomePermissao1);
		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		controllerPermissao.criarPermissao(nomePermissao2);
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		controllerPermissao.criarPermissao(nomePermissao3);

		Perfil perfil = PerfilController.getInstance().consultarPerfil(nomePerfil);

		Permissao permissao1 = controllerPermissao.consultarPermissaoPorNome(nomePermissao1);
		Permissao permissao2 = controllerPermissao.consultarPermissaoPorNome(nomePermissao2);
		Permissao permissao3 = controllerPermissao.consultarPermissaoPorNome(nomePermissao3);

		PerfilController.getInstance().atribuirPermissaoAUmPerfil(permissao1, perfil);
		PerfilController.getInstance().atribuirPermissaoAUmPerfil(permissao2, perfil);

		List<Permissao> listaPermissao = PerfilController.getInstance()
				.listarPermissoesDeUmPerfil(perfil.getIdPerfil());

		System.out.println("0");
		listaPermissao.toString();

		assertEquals(2, listaPermissao.size());
		System.out.println("1");
		assertTrue(PerfilController.getInstance().possuiPermissoes(perfil, listaPermissao.get(1)));
		System.out.println("2");
		assertFalse(PerfilController.getInstance().possuiPermissoes(perfil, permissao3));

	}

	@Test
	public void testCriarEConsultarPerfil() {
		String nomeDoPerfil = "PerfilDeTesteDeCriacao";
		PerfilController.getInstance().criarPerfil(nomeDoPerfil);

		Perfil perfilEncontradoPorNome = PerfilController.getInstance().consultarPerfil(nomeDoPerfil);
		Perfil perfilEncontradoPorId = PerfilController.getInstance()
				.consultarPerfil(perfilEncontradoPorNome.getIdPerfil());

		assertEquals(nomeDoPerfil, perfilEncontradoPorNome.getNomePerfil(), perfilEncontradoPorId.getNomePerfil());
	}

	@Test
	public void testConsultarPerfilPorNomeInexistente() {
		assertNull(PerfilController.getInstance().consultarPerfil("Nome perfil inexistente"));
	}

	@Test
	public void testConsultarPerfilPorIdInexistente() {
		assertNull(PerfilController.getInstance().consultarPerfil(9876));
	}

	@Test
	public void testAlterarPerfil() {
		String nomePerfil = "PerfilDeTesteDeAlteracaoAntesDaAlteracao";
		PerfilController.getInstance().criarPerfil(nomePerfil);
		Perfil perfilConsultado = PerfilController.getInstance().consultarPerfil(nomePerfil);
		String novoNomeDoPerfil = "PerfilDeTesteDeAlteracaoDepoisDaAlteracao";
		PerfilController.getInstance().alterarPerfil(perfilConsultado.getIdPerfil(), novoNomeDoPerfil);
		perfilConsultado = PerfilController.getInstance().consultarPerfil(perfilConsultado.getIdPerfil());
		assertEquals(novoNomeDoPerfil, perfilConsultado.getNomePerfil());
	}

	@Test
	public void testDeletarPerfil() {
		String nomePerfil = "PerfilDeTesteDeDeletar";
		PerfilController.getInstance().criarPerfil(nomePerfil);

		int numeroDePerfisAntesDoTeste = PerfilController.getInstance().listarTodosOsPerfis().size();

		String nomeDoPerfil = "PerfilDeTesteDeDelecao";

		PerfilController.getInstance().criarPerfil(nomeDoPerfil);

		Perfil perfilASerDeletado = PerfilController.getInstance().consultarPerfil(nomeDoPerfil);

		PerfilController.getInstance().deletarPerfil(perfilASerDeletado.getIdPerfil());

		int numeroDePerfisDepoisDoTeste = PerfilController.getInstance().listarTodosOsPerfis().size();

		assertEquals(numeroDePerfisAntesDoTeste, numeroDePerfisDepoisDoTeste);
	}

	@Test
	public void testListarTodosPerfis() {
		String nomePerfil = "PerfilDeTesteDeListar";
		PerfilController.getInstance().criarPerfil(nomePerfil);

		assertEquals(1, PerfilController.getInstance().listarTodosOsPerfis().size());
	}
	
	@Test
	public void testListarPerfisComListaVazia() {
		assertNull(PerfilController.getInstance().listarTodosOsPerfis());
	}

}
