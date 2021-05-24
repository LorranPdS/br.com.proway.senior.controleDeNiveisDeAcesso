package controller.controllerApi;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.dto.PermissaoDTO;

public class PermissaoControllerApiTest {
	PermissaoControllerApi controller = new PermissaoControllerApi();

	@After
	@Before
	public void deletarTudo() {
		controller.deletarTodos();
	}

	@Test
	public void testCriarPermissao() {
		assertEquals(0, controller.listarTodasAsPermissoes().size());
		controller.criarPermissao("Gerencia");
		assertEquals(1, controller.listarTodasAsPermissoes().size());
	}

	@Test
	public void testAlterarPermissao() {
		controller.criarPermissao("Gerencia");
		assertEquals(1, controller.listarTodasAsPermissoes().size());
		PermissaoDTO permissaoConsultada = controller.consultarPermissaoPorNome("Gerencia");
		String novoNomeDaPermissao = "Gerencia Novo";
		controller.alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		PermissaoDTO permissaoAlterada = controller.consultarPermissaoPorId(permissaoConsultada.getIdPermissao());
		assertEquals("Gerencia Novo", permissaoAlterada.getNomePermissao());
	}

	@Test
	public void testDeletarPermissao() {
		assertEquals(0, controller.listarTodasAsPermissoes().size());
		controller.criarPermissao("Gerencia");
		assertEquals(1, controller.listarTodasAsPermissoes().size());
		PermissaoDTO permissaoConsultada = controller.consultarPermissaoPorNome("Gerencia");
		controller.deletarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(0, controller.listarTodasAsPermissoes().size());
	}

	@Test
	public void testListarTodasAsPermissoes() {
		assertEquals(0, controller.listarTodasAsPermissoes().size());
		controller.criarPermissao("Gerencia");
		controller.criarPermissao("Subordinado");
		assertEquals(2, controller.listarTodasAsPermissoes().size());
	}

	@Test
	public void testDeletarTodos() {
		controller.criarPermissao("Gerencia");
		controller.deletarTodos();
		assertEquals(0, controller.listarTodasAsPermissoes().size());
	}

}
