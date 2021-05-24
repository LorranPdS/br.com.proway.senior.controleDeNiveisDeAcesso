package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.entidades.Perfil;
import model.entidades.Permissao;


public class PerfilControllerTest {

	@After
	@Before
	public void deletarTudo() {
		PerfilController.getInstance().deletarTodos();
		PermissaoController.getInstance().deletarTodos();
	}

	@Test
	public void testVerificarEListarPermissaoDeUmPerfil() {
		String nomePerfil = "PerfilDeTesteDeVerificacaoDePermissao";
		PerfilController.getInstance().criarPerfil(nomePerfil);
   
		String nomePermissao1 = "PermissaoTesteDeAtribuicao1";
		PermissaoController.getInstance().criarPermissao(nomePermissao1);
		String nomePermissao2 = "PermissaoTesteDeAtribuicao2";
		PermissaoController.getInstance().criarPermissao(nomePermissao2);
		String nomePermissao3 = "PermissaoTesteDeAtribuicao3";
		PermissaoController.getInstance().criarPermissao(nomePermissao3);

		Perfil perfil = PerfilController.getInstance().consultarPerfil(nomePerfil);
		
		Permissao permissao1 = PermissaoController.getInstance().consultarPermissao(nomePermissao1);
		Permissao permissao2 = PermissaoController.getInstance().consultarPermissao(nomePermissao2);
		Permissao permissao3 = PermissaoController.getInstance().consultarPermissao(nomePermissao3);

		PerfilController.getInstance().atribuirPermissaoAUmPerfil(permissao1, perfil);
		PerfilController.getInstance().atribuirPermissaoAUmPerfil(permissao2, perfil);

		List<Permissao> listaPermissao = PerfilController.getInstance().listarPermissoesDeUmPerfil(perfil.getIdPerfil());

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
		Perfil perfilEncontradoPorId = PerfilController.getInstance().consultarPerfil(perfilEncontradoPorNome.getIdPerfil());
		
		assertEquals(nomeDoPerfil, perfilEncontradoPorNome.getNomePerfil(), perfilEncontradoPorId.getNomePerfil());
	}
	
	@Test(expected = NoResultException.class)
	public void testConsultarPerfilCatch() {
		 
		String nomeDoPerfil = "PerfilDeTesteDeCriacao";
		PerfilController.getInstance().criarPerfil(nomeDoPerfil);

		String nomeInvalido = "PerfilNaoValido";
		
		PerfilController.getInstance().consultarPerfil(nomeInvalido);
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
		
		int numeroDePerfisNoBanco = 1;
		assertEquals(numeroDePerfisNoBanco, PerfilController.getInstance().listarTodosOsPerfis().size());
	}

}
