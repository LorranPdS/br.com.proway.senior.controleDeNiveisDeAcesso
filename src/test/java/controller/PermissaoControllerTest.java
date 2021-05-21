package controller;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.entidades.Permissao;

public class PermissaoControllerTest {

	@After
	@Before
	public void deletarTudo() {
		PermissaoController.getInstance().deletarTodos();
	}

	@Test
	public void testCriarPermissao() {
		assertEquals(0, PermissaoController.getInstance().listarTodasAsPermissoes().size());
		PermissaoController.getInstance().criarPermissao("Gerencia");
		assertEquals(1, PermissaoController.getInstance().listarTodasAsPermissoes().size());
	}

	@Test
	public void testAlterarPermissao() {
		PermissaoController.getInstance().criarPermissao("Gerencia");
		assertEquals(1, PermissaoController.getInstance().listarTodasAsPermissoes().size());
		Permissao permissaoConsultada = PermissaoController.getInstance().consultarPermissaoPorNome("Gerencia");
		String novoNomeDaPermissao = "Gerencia Novo";
		PermissaoController.getInstance().alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		Permissao permissaoAlterada = PermissaoController.getInstance().consultarPermissaoPorId(permissaoConsultada.getIdPermissao());
		assertEquals("Gerencia Novo", permissaoAlterada.getNomePermissao());
	}

	@Test
	public void testDeletarPermissao() {
		assertEquals(0, PermissaoController.getInstance().listarTodasAsPermissoes().size());
		PermissaoController.getInstance().criarPermissao("Gerencia");
		assertEquals(1, PermissaoController.getInstance().listarTodasAsPermissoes().size());
		Permissao permissaoConsultada = PermissaoController.getInstance().consultarPermissaoPorNome("Gerencia");
		PermissaoController.getInstance().deletarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(0, PermissaoController.getInstance().listarTodasAsPermissoes().size());
	}

	@Test
	public void testListarTodasAsPermissoes() {
		assertEquals(0, PermissaoController.getInstance().listarTodasAsPermissoes().size());
		PermissaoController.getInstance().criarPermissao("Gerencia");
		PermissaoController.getInstance().criarPermissao("Subordinado");
		assertEquals(2, PermissaoController.getInstance().listarTodasAsPermissoes().size());
	}

	@Test
	public void testDeletarTodos() {
		PermissaoController.getInstance().criarPermissao("Gerencia");
		PermissaoController.getInstance().deletarTodos();
		assertEquals(0, PermissaoController.getInstance().listarTodasAsPermissoes().size());
	}
}
