package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import model.acesso.PerfilModel;

/**
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PerfilControllerTest {

	@Test
	public void verificaSeOcorreACriacaoDeUmPerfil() {

		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		assertTrue(perfilController.criarPerfilVazioController("Ponto"));
	}
	
	@Test
	public void verificaSeOcorreACriacaoDeUmPerfilTemporario() {

		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		PerfilModel pm = new PerfilModel("Vale vale", LocalDate.of(2012, 10, 29), LocalDate.of(2028, 4, 23), true);
		assertTrue(perfilController.criarPerfilVazioTemporarioController(pm));
	}

	@Test
	public void verificaSeNaoAdicionaPerfisDuplicados() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		assertTrue(perfilController.criarPerfilVazioController("Ponto"));
		assertFalse(perfilController.criarPerfilVazioController("Ponto"));
	}
	
	@Test
	public void verificaSeOPerfilFoiExcluidoPeloNome() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.deletarPerfilController("Vale alimenta��o");
		
		assertTrue(perfilController.buscarTodosOsPerfis() == null);
	}
	
	@Test
	public void verificaSeOPerfilFoiExcluidoPeloID() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.deletarPerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil());
		
		assertTrue(perfilController.buscarPerfil("Vale alimenta��o") == null);
	}
	
	@Test
	public void buscarPerfilPeloNome() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		PerfilModel pm = perfilController.buscarPerfil("Vale alimenta��o");
		
		assertEquals(pm.getNomeDoPerfil(), "Vale alimenta��o");
	}
	
	@Test
	public void buscarPerfilPeloID() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		PerfilModel pm = perfilController.buscarPerfil(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil());
		
		assertEquals(pm.getNomeDoPerfil(), "Vale alimenta��o");
	}
	
	@Test
	public void buscarTodosOsPerfis() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.criarPerfilVazioController("Vale refei��o");
		perfilController.criarPerfilVazioController("Vale transporte");
		
		ArrayList<PerfilModel> pmLista = perfilController.buscarTodosOsPerfis();
		assertEquals("Vale alimenta��o", pmLista.get(0).getNomeDoPerfil());
		assertEquals("Vale refei��o", pmLista.get(1).getNomeDoPerfil());
		assertEquals("Vale transporte", pmLista.get(2).getNomeDoPerfil());	
	}
	
	@Test
	public void testeAlterarNomePerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.alterarNomePerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil(), "Vale vuvuzela");
		
		assertNotNull(perfilController.buscarPerfil("Vale vuvuzela"));
	}
	
	@Test
	public void testeAlterarDataInicioPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.alterarDataInicioPerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil(), LocalDate.of(1234, 5, 6));
		
		assertEquals(LocalDate.of(1234, 5, 6), perfilController.buscarPerfil("Vale alimenta��o").getInicioValidadePerfil());
	}
	
	@Test
	public void testeAlterarDataFimPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.alterarDataFimPerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil(), LocalDate.of(1234, 5, 6));
		
		assertEquals(LocalDate.of(1234, 5, 6), perfilController.buscarPerfil("Vale alimenta��o").getFimValidadePerfil());
	}
	
	@Test
	public void testeAlterarStatusPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		perfilController.alterarStatusPerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil(), false);
		
		assertEquals(false, perfilController.buscarPerfil("Vale alimenta��o").isPerfilAtivo());
	}
	
	@Test
	public void testeAtualizarPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimenta��o");
		PerfilModel novoPerfil = new PerfilModel("Vale alimenta��o", LocalDate.of(2020, 3, 6), LocalDate.of(2023, 5, 18), true);
		perfilController.atualizarPerfilController(perfilController.buscarPerfil("Vale alimenta��o").getIdDoPerfil(), novoPerfil);
		
		assertEquals(false, perfilController.buscarPerfil("Vale alimenta��o").isPerfilAtivo());
		assertEquals(LocalDate.of(2020, 3, 6), perfilController.buscarPerfil("Vale alimenta��o").getInicioValidadePerfil());
		assertEquals(LocalDate.of(2023, 5, 18), perfilController.buscarPerfil("Vale alimenta��o").getFimValidadePerfil());
	}
}
