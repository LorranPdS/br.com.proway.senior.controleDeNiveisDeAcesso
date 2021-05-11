package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import controller.PermissaoController;
import model.acesso.Permissao;

/**
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PermissaoControllerTest {

	@Test
	public void verificaSeOcorreACriacaoDeUmaPermissao() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		assertTrue(permissaoController.criarPermissaoController("Adicionar"));

	}

	@Test
	public void verificaSeOcorreACriacaoDeDuasPermissoes() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		assertTrue(permissaoController.criarPermissaoController("Adicionar"));
		assertTrue(permissaoController.criarPermissaoController("Remover"));
	}

	@Test
	public void verificaSeNaoAdicionaPermissoesDuplicadas() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		assertTrue(permissaoController.criarPermissaoController("Adicionar"));
		assertFalse(permissaoController.criarPermissaoController("Adicionar"));
	}
	
	@Test
	public void verificaSeAPermissaoFoiExcluida() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		permissaoController.criarPermissaoController("Excluir");
		
		permissaoController.deletarPermissaoController("Excluir");	
		assertNull(permissaoController.buscarPermissao("Excluir"));
	}
	
	@Test
	public void verificaSeAPermissaoFoiExcluidaPeloID() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		permissaoController.criarPermissaoController("Excluir");
		
		assertTrue(permissaoController.deletarPermissaoController(permissaoController.buscarPermissao("Excluir").getIdDaPermissao()));
	}
	
	@Test
	public void buscarPermissaoPeloNome() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		assertEquals("Adicionar", permissaoController.buscarPermissao("Adicionar").getNomeDaPermissao());
		
	}
	
	@Test
	public void buscarTodasAsPermissoes() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		permissaoController.criarPermissaoController("Excluir");
		permissaoController.criarPermissaoController("Remover");
		
		ArrayList<Permissao> listaPermissoes = permissaoController.buscarTodasAsPermissoes();
		
		Permissao p1 = new Permissao("Adicionar");
		Permissao p2 = new Permissao("Excluir");
		Permissao p3 = new Permissao("Remover");
		
		listaPermissoes.addAll(Arrays.asList(p1, p2, p3));
		assertEquals(listaPermissoes.get(0).getNomeDaPermissao(), p1.getNomeDaPermissao());
		assertEquals(listaPermissoes.get(1).getNomeDaPermissao(), p2.getNomeDaPermissao());
		assertEquals(listaPermissoes.get(2).getNomeDaPermissao(), p3.getNomeDaPermissao());
	}
	
	@Test
	public void buscarUmaPermissaoPorID() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		
		
		Permissao p1 = permissaoController.buscarPermissao(permissaoController.buscarPermissao("Adicionar").getIdDaPermissao());
		
		assertEquals(p1.getNomeDaPermissao(), "Adicionar");
	}
	
	@Test
	public void atualizarUmaPermissao() {
		PermissaoController permissaoController = new PermissaoController();
		permissaoController.getDao().db.limparDB("permissoestabela");
		permissaoController.criarPermissaoController("Adicionar");
		Permissao pm = new Permissao("Remover");
		
		permissaoController.atualizarPermissao(permissaoController.buscarPermissao("Adicionar").getIdDaPermissao(), pm);
		assertEquals(pm.getNomeDaPermissao(), permissaoController.buscarPermissao("Remover").getNomeDaPermissao());
	}

}
