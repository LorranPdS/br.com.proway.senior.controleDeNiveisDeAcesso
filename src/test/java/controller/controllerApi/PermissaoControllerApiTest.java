package controller.controllerApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PermissaoController;
import model.dto.PermissaoDTO;
import model.entidades.Permissao;

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

	Permissao permissao = new Permissao("Gerencia");

	@Test
	public void testCriarPermissao() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao(permissao);
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testAlterarPermissao() {
		controllerApi.criarPermissao(permissao);
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
		Permissao permissaoConsultada = controller.consultarPermissaoPorNomeExato("Gerencia");
		Permissao permissaoNova = permissaoConsultada;
		permissaoNova.setNomePermissao("Novo nome");
		controllerApi.alterarPermissao(permissaoConsultada.getIdPermissao(), permissaoNova);
		Permissao permissaoAlterada = controller.consultarPermissaoPorId(permissaoConsultada.getIdPermissao());
		assertEquals("Novo nome", permissaoAlterada.getNomePermissao());
	}

	@Test
	public void testDeletarPermissao() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao(permissao);
		assertEquals(1, controllerApi.listarTodasAsPermissoes().size());
		Permissao permissaoConsultada = controller.consultarPermissaoPorNomeExato("Gerencia");
		controllerApi.deletarPermissao(permissaoConsultada.getIdPermissao());
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testListarTodasAsPermissoes() {
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
		controllerApi.criarPermissao(permissao);
		Permissao permissao2 = new Permissao("Outra permissao.");
		controllerApi.criarPermissao(permissao2);
		assertEquals(2, controllerApi.listarTodasAsPermissoes().size());
	}

	@Test
	public void testDeletarTodos() {
		controllerApi.criarPermissao(permissao);
		controller.deletarTodos();
		assertEquals(0, controllerApi.listarTodasAsPermissoes().size());
	}

}
