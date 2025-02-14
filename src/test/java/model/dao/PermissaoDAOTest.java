package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.entidades.Permissao;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PermissaoDAOTest {

	
	@AfterEach
	@BeforeEach
	public void deletarTudo() {
		PermissaoDAO.getInstance().deletarTodos();
	}

	@Test
	public void testBcriarPermissao() {
		Permissao permissao = new Permissao("Visualizar");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("Visualizar");
		assertEquals(permissao, permissaoEncontrada);
	}

	@Test
	public void testCconsultarPermissoesPorNome() {
		Permissao permissao = new Permissao("Alterar");
		PermissaoDAO.getInstance().criar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorNome("Alterar");
		assertEquals("Alterar", permissaoEncontrada.getNomePermissao());
	}

	@Test
	public void testDalterarPermissao() {
		Permissao permissao = new Permissao("PermissaoErrada");
		PermissaoDAO.getInstance().criar(permissao);
		permissao.setNomePermissao("Remover");
		PermissaoDAO.getInstance().alterar(permissao);
		Permissao permissaoEncontrada = PermissaoDAO.getInstance().consultarPorId(permissao.getIdPermissao());
		assertEquals("Remover", permissaoEncontrada.getNomePermissao());
	}
	
	@Test
	public void testEDeletarPermissao() {
		Permissao permissao = new Permissao("PermissaoParaDeletar");
		PermissaoDAO.getInstance().criar(permissao);
		int tamanhoAntesDeDeletar = PermissaoDAO.getInstance().listar().size();
		PermissaoDAO.getInstance().deletar(permissao);
		assertEquals(tamanhoAntesDeDeletar - 1, PermissaoDAO.getInstance().listar().size());
	}

}
