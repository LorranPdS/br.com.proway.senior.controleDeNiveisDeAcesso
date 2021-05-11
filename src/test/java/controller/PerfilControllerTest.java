package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import model.acesso.Perfil;


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
		
		Perfil pm = new Perfil("Vale vale", LocalDate.of(2012, 10, 29), LocalDate.of(2028, 4, 23), true);
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
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.deletarPerfilController("Vale alimentação");
		
		assertTrue(perfilController.buscarTodosOsPerfis() == null);
	}
	
	@Test
	public void verificaSeOPerfilFoiExcluidoPeloID() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.deletarPerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil());
		
		assertTrue(perfilController.buscarPerfil("Vale alimentação") == null);
	}
	
	@Test
	public void buscarPerfilPeloNome() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		Perfil pm = perfilController.buscarPerfil("Vale alimentação");
		
		assertEquals(pm.getNomeDoPerfil(), "Vale alimentação");
	}
	
	@Test
	public void buscarPerfilPeloID() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		Perfil pm = perfilController.buscarPerfil(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil());
		
		assertEquals(pm.getNomeDoPerfil(), "Vale alimentação");
	}
	
	@Test
	public void buscarTodosOsPerfis() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.criarPerfilVazioController("Vale refeição");
		perfilController.criarPerfilVazioController("Vale transporte");
		
		ArrayList<Perfil> pmLista = perfilController.buscarTodosOsPerfis();
		assertEquals("Vale alimentação", pmLista.get(0).getNomeDoPerfil());
		assertEquals("Vale refeição", pmLista.get(1).getNomeDoPerfil());
		assertEquals("Vale transporte", pmLista.get(2).getNomeDoPerfil());	
	}
	
	@Test
	public void testeAlterarNomePerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.alterarNomePerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil(), "Vale vuvuzela");
		
		assertNotNull(perfilController.buscarPerfil("Vale vuvuzela"));
	}
	
	@Test
	public void testeAlterarDataInicioPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.alterarDataInicioPerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil(), LocalDate.of(1234, 5, 6));
		
		assertEquals(LocalDate.of(1234, 5, 6), perfilController.buscarPerfil("Vale alimentação").getInicioValidadePerfil());
	}
	
	@Test
	public void testeAlterarDataFimPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.alterarDataFimPerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil(), LocalDate.of(1234, 5, 6));
		
		assertEquals(LocalDate.of(1234, 5, 6), perfilController.buscarPerfil("Vale alimentação").getFimValidadePerfil());
	}
	
	@Test
	public void testeAlterarStatusPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		perfilController.alterarStatusPerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil(), false);
		
		assertEquals(false, perfilController.buscarPerfil("Vale alimentação").isPerfilAtivo());
	}
	
	@Test
	public void testeAtualizarPerfil() {
		PerfilController perfilController = new PerfilController();
		perfilController.getDao().db.limparDB("perfiltabela");
		
		perfilController.criarPerfilVazioController("Vale alimentação");
		Perfil novoPerfil = new Perfil("Vale alimentação", LocalDate.of(2020, 3, 6), LocalDate.of(2023, 5, 18), true);
		perfilController.atualizarPerfilController(perfilController.buscarPerfil("Vale alimentação").getIdDoPerfil(), novoPerfil);
		
		assertEquals(false, perfilController.buscarPerfil("Vale alimentação").isPerfilAtivo());
		assertEquals(LocalDate.of(2020, 3, 6), perfilController.buscarPerfil("Vale alimentação").getInicioValidadePerfil());
		assertEquals(LocalDate.of(2023, 5, 18), perfilController.buscarPerfil("Vale alimentação").getFimValidadePerfil());
	}
}
