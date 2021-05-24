package controller.controllerApi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.PerfilController;
import controller.PerfilDeUsuarioController;
import controller.PermissaoController;
import controller.UsuarioController;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class PerfilDeUsuarioControllerApiTest {

	@Before
	public void deletarTudoDoPerfilDeUsuario() {
		controllerApi.deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		controllerApi.deletarTodos();
		usuarioApi.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		controllerApi.deletarTodos();
		usuarioApi.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();
	}

	static PerfilDeUsuarioControllerApi controllerApi = new PerfilDeUsuarioControllerApi();
	static PerfilDeUsuarioController controllerSemApi = new PerfilDeUsuarioController();
	
	static PerfilController perfilController = new PerfilController();
	static UsuarioController usuarioController = new UsuarioController();
	static PermissaoController permissaoController = new PermissaoController();
	
	static PerfilControllerApi perfilApi = new PerfilControllerApi();
	static UsuarioControllerApi usuarioApi = new UsuarioControllerApi();
	static PermissaoControllerApi permissaoApi = new PermissaoControllerApi();
	
	static Usuario usuario;
	static Permissao permissao;
	static Perfil perfil;
	
	static UsuarioDTO usuarioDTO;
	static PermissaoDTO permissaoDTO;
	static PerfilDTO perfilDTO;

	public static void popularTabelas() {
		perfilApi.criarPerfil("Vendedor");
		perfil = perfilController.consultarPerfil("Vendedor");

		permissaoApi.criarPermissao("Relatório de compras");
		permissao = permissaoController.consultarPermissaoPorNome("Relatório de compras");
		
		perfilApi.atribuirPermissaoAUmPerfil(permissao, perfil);
		perfil = perfilController.consultarPerfil("Vendedor");

		usuarioApi.criarUsuario("thiago@gmail.com", "admin");
		usuario = usuarioController.consultarUsuario("thiago@gmail.com");
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioController.consultarUsuario("thiago@gmail.com");

		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		assertEquals(1, controllerApi.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilApi.atribuirPermissaoAUmPerfil(permissao, perfil);
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		ArrayList<PermissaoDTO> permissoes = controllerApi.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		assertEquals(1, permissoes.size());
		assertEquals("Relatório de compras", permissoes.get(0).getNomePermissao());
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controllerApi.listar().size());

		assertEquals(1, controllerApi.consultarPorIdDoPerfil(perfil.getIdPerfil()).size());
	}

	@Test
	public void testConsultarPorIdDoUsuario() {
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controllerApi.listar().size());
		
		usuario = usuarioController.consultarUsuario("thiago@gmail.com");
		assertEquals(1, controllerApi.consultarPorIdDoUsuario(usuario.getIdUsuario()).size());
	}
	
	@Test
	public void testDeletar() {
		assertEquals(0, controllerApi.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		
		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controllerApi.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		controllerApi.deletar(ligacao);
		assertEquals(0, controllerApi.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controllerApi.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		controllerApi.alterar(ligacao);
		assertEquals(LocalDate.now().plusMonths(3), controllerApi.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);
		
		assertEquals(1, controllerApi.listar().size());
		assertEquals("Vendedor", controllerApi.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao1.getUsuario(), ligacao1.getPerfil(), ligacao1.getDataExpiracao());

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao2.getUsuario(), ligacao2.getPerfil(), ligacao2.getDataExpiracao());

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao3.getUsuario(), ligacao3.getPerfil(), ligacao3.getDataExpiracao());

		assertEquals(3, controllerApi.listar().size());
	}
	
	@Test
	public void testUsuarioNaoPossuiPermissaoPara() {
		boolean possui = controllerApi.usuarioPossuiPermissaoPara(usuario, permissao);
		assertFalse(possui);
	}
	
	@Test
	public void testUsuarioPossuiPermissaoPara() {
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controllerApi.usuarioPossuiPermissaoPara(usuario, permissao);
		assertTrue(possui);
	}
	
	@Test
	public void testUsuarioPossuiOPerfil() {
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now());
		boolean possui = controllerApi.usuarioPossuiOPerfil(usuario, perfil);
		assertTrue(possui);
	}
	
	@Test
	public void testUsuarioNaoPossuiOPerfil() {
		boolean possui = controllerApi.usuarioPossuiOPerfil(usuario, perfil);
		assertFalse(possui);
	}
	
	@Test
	public void testPermissaoAtivaTrue() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(true);
		ligacao.setDataExpiracao(LocalDate.now().plusDays(10));
		assertTrue(controllerApi.permissaoAtiva(ligacao));
	}
	
	@Test
	public void testPermissaoAtivaFalse() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(false);
		ligacao.setDataExpiracao(LocalDate.now().plusDays(10));
		assertFalse(controllerApi.permissaoAtiva(ligacao));
	}
	
	@Test
	public void testPermissaoAtivaFalseData() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setAtivo(true);
		ligacao.setDataExpiracao(LocalDate.of(2019, 01, 01));
		assertFalse(controllerApi.permissaoAtiva(ligacao));
	}
	
	@Test
	public void testListarTodasLigacoesAtivas() {
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(2));
		
		assertEquals(2, controllerApi.listarTodasLigacoesAtivas().size());
	}
	
	@Test 
	public void testDesativarLigacaoNulla() {
		PerfilDeUsuario ligacao = null;
		assertFalse(controllerApi.desativar(ligacao));
	}
	
	@Test 
	public void testDesativarLigacaoIdNull() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setUsuario(usuario);
		ligacao.setPerfil(perfil);
		ligacao.setDataExpiracao(LocalDate.now());
		ligacao.setAtivo(true);
		assertFalse(controllerApi.desativar(ligacao));
	}
	
	@Test 
	public void testDesativarLigacaoIdInexistente() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario();
		ligacao.setUsuario(usuario);
		ligacao.setPerfil(perfil);
		ligacao.setDataExpiracao(LocalDate.now());
		ligacao.setAtivo(true);
		ligacao.setId(6548);
		assertFalse(controllerApi.desativar(ligacao));
	}
}
