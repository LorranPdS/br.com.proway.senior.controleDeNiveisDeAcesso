package controller.controllerApi;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.PerfilController;
import controller.PermissaoController;
import model.dto.PerfilDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;

public class PerfilControllerApiTest {
	
	PermissaoController controllerPermissao = new PermissaoController();
	PerfilControllerApi controllerPerfilApi = new PerfilControllerApi();
	PerfilController controllerPerfil = new PerfilController();
	
	@After
	@Before
	public void deletarTudo() {
		controllerPerfilApi.deletarTodos();
		controllerPermissao.deletarTodos();
	}
	
	@Test
	public void testConsultarPerfil() {

		Permissao permissao = new Permissao("acesso geral");
		controllerPermissao.criarPermissao(permissao.getNomePermissao());
		permissao = controllerPermissao.consultarPermissaoPorNome("acesso geral");
		
		Perfil perfil = new Perfil("tu");
		controllerPerfil.criarPerfil(perfil.getNomePerfil());
		perfil = controllerPerfil.consultarPerfil("tu");
		
		controllerPerfil.atribuirPermissaoAUmPerfil(permissao, perfil);
		
		assertEquals(1, perfil.getPermissoes().size());
		
		System.out.println(controllerPerfilApi.consultarPerfil(perfil.getNomePerfil()));
	}
	
	@Test
	public void testListarTodosPerfis() {
		
		String nomePerfil = "PerfilDeTesteDeListar";
		controllerPerfilApi.criarPerfil(nomePerfil);
		String nomePerfil2 = "PerfilDeTesteDeListar2";
		controllerPerfilApi.criarPerfil(nomePerfil2);
		
		assertEquals(2, controllerPerfilApi.listarTodosOsPerfis().size());
		System.out.println(controllerPerfilApi.listarTodosOsPerfis());
		
	}
	
	@Test
	public void testVerificarEListarPermissaoDeUmPerfil() {
		String nomePerfil = "PerfilDeTesteDeVerificacaoDePermissao";
		controllerPerfilApi.criarPerfil(nomePerfil);
   
		String nomePermissao1 = "PermissaoTesteDeAtribuicao1";
		controllerPermissao.criarPermissao(nomePermissao1);
		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		controllerPermissao.criarPermissao(nomePermissao2);
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		controllerPermissao.criarPermissao(nomePermissao3);

		Perfil perfil = controllerPerfil.consultarPerfil(nomePerfil);
		
		Permissao permissao1 = controllerPermissao.consultarPermissaoPorNome(nomePermissao1);
		Permissao permissao2 =controllerPermissao.consultarPermissaoPorNome(nomePermissao2);
		Permissao permissao3 = controllerPermissao.consultarPermissaoPorNome(nomePermissao3);

		controllerPerfilApi.atribuirPermissaoAUmPerfil(permissao1, perfil);
		controllerPerfilApi.atribuirPermissaoAUmPerfil(permissao2, perfil);

		List<Permissao> listaPermissao = controllerPerfilApi.listarPermissoesDeUmPerfil(perfil.getIdPerfil());

		System.out.println("0");
		listaPermissao.toString();

		assertEquals(2, listaPermissao.size());
		System.out.println("1");
		assertTrue(controllerPerfilApi.possuiPermissoes(perfil, listaPermissao.get(1)));
		System.out.println("2");
		assertFalse(controllerPerfilApi.possuiPermissoes(perfil, permissao3));

	}
	
	@Test
	public void testAlterarPerfil() {
		String nomePerfil = "PerfilDeTesteDeAlteracaoAntesDaAlteracao";
		controllerPerfilApi.criarPerfil(nomePerfil);
		PerfilDTO perfilConsultado = controllerPerfilApi.consultarPerfil(nomePerfil);
		String novoNomeDoPerfil = "PerfilDeTesteDeAlteracaoDepoisDaAlteracao";
		controllerPerfilApi.alterarPerfil(perfilConsultado.getIdPerfil(), novoNomeDoPerfil);
		perfilConsultado = controllerPerfilApi.consultarPerfilPorId(perfilConsultado.getIdPerfil());
		assertEquals(novoNomeDoPerfil, perfilConsultado.getNomePerfil());
	}
	
	@Test
	public void testDeletarPerfil() {
		String nomePerfil = "PerfilDeTesteDeDeletar";
		controllerPerfilApi.criarPerfil(nomePerfil);
		
		int numeroDePerfisAntesDoTeste = controllerPerfilApi.listarTodosOsPerfis().size();
		
		String nomeDoPerfil = "PerfilDeTesteDeDelecao";
		
		controllerPerfilApi.criarPerfil(nomeDoPerfil);
		
		PerfilDTO perfilASerDeletado = controllerPerfilApi.consultarPerfil(nomeDoPerfil);
		
		controllerPerfilApi.deletarPerfil(perfilASerDeletado.getIdPerfil());
		
		int numeroDePerfisDepoisDoTeste = controllerPerfilApi.listarTodosOsPerfis().size();
		
		assertEquals(numeroDePerfisAntesDoTeste, numeroDePerfisDepoisDoTeste);
	} 

}
