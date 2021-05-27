package controller.controllerApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PermissaoController;
import model.dto.PermissaoDTO;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PermissaoControllerApiTest {
	PermissaoControllerApi controllerApi = new PermissaoControllerApi();
	PermissaoController controller = new PermissaoController();

	@AfterEach
	@BeforeEach
	public void deletarTudo() {
		controller.deletarTodos();
	}

	@Test
	public void testCriarPermissao() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao("Gerencia");
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testAlterarPermissao() {
		controllerApi.criarPermissao("Gerencia");
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
		PermissaoDTO permissaoConsultada = controllerApi.consultarPermissaoPorNome("Gerencia");
		String novoNomeDaPermissao = "Gerencia Novo";
		controllerApi.alterarPermissao(permissaoConsultada.getIdPermissao(), novoNomeDaPermissao);
		PermissaoDTO permissaoAlterada = controllerApi.consultarPermissaoPorId(permissaoConsultada.getIdPermissao());
		assertEquals("Gerencia Novo", permissaoAlterada.getNomePermissao());
	}

	@Test
	public void testDeletarPermissao() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao("Gerencia");
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
		PermissaoDTO permissaoConsultada = controllerApi.consultarPermissaoPorNome("Gerencia");
		controllerApi.deletarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testListarTodasAsPermissoes() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao("Gerencia");
		controllerApi.criarPermissao("Subordinado");
		assertEquals(2, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testDeletarTodos() {
		controllerApi.criarPermissao("Gerencia");
		controller.deletarTodos();
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
	}

}
