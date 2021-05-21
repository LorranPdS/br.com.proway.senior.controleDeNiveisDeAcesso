package model.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.dao.PermissaoDAO;
import model.entidades.Permissao;

public class PermissaoDAOTest {

	
	@After
	@Before
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
	public void testDlterarPermissao() {
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
