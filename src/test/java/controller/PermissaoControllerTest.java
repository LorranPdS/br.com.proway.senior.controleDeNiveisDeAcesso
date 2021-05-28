package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PermissaoController;
import model.entidades.Permissao;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PermissaoControllerTest {

	PermissaoController controller = new PermissaoController();

	@AfterEach
	@BeforeEach
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
		Permissao permissaoConsultada = controller.consultarPermissaoPorNomeExato("Gerencia");
		String novoNomeDaPermissao = "Gerencia Novo";
		controller.alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		Permissao permissaoAlterada = controller.consultarPermissaoPorId(permissaoConsultada.getIdPermissao());
		assertEquals("Gerencia Novo", permissaoAlterada.getNomePermissao());
	}

	@Test
	public void testDeletarPermissao() {
		assertEquals(0, controller.listarTodasAsPermissoes().size());
		controller.criarPermissao("Gerencia");
		assertEquals(1, controller.listarTodasAsPermissoes().size());
		Permissao permissaoConsultada = controller.consultarPermissaoPorNomeExato("Gerencia");
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
