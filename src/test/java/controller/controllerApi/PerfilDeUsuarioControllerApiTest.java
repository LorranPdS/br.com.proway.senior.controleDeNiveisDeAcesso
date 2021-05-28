package controller.controllerApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.controllers.PerfilController;
import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.PermissaoController;
import controller.controllers.UsuarioController;
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

	@BeforeEach
	public void deletarTudoDoPerfilDeUsuario() {
		controllerSemApi.deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		controllerSemApi.deletarTodos();
		usuarioController.deletarTodos();
		perfilApi.deletarTodos();
		permissaoController.deletarTodos();

		popularTabelas();
	}

	@AfterAll
	public static void limparTabelas() {
		controllerSemApi.deletarTodos();
		usuarioController.deletarTodos();
		perfilApi.deletarTodos();
		permissaoController.deletarTodos();
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
		
		Permissao permissao1 = new Permissao("Relatório de compras");
		permissao = permissao1;
		permissaoApi.criarPermissao(permissao);
		permissao = permissaoController.consultarPermissaoPorNomeExato("Relatório de compras");

		perfilApi.atribuirPermissaoAUmPerfil(permissao, perfil);
		perfil = perfilController.consultarPerfil("Vendedor");

		usuarioApi.criarUsuario(new Usuario("thiago@gmail.com", "admin"));
		usuario = usuarioController.consultarUsuario("thiago@gmail.com");
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioController.consultarUsuario("thiago@gmail.com");

		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(),
				LocalDate.now().plusYears(1).toString());

		assertEquals(1, controllerApi.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilApi.atribuirPermissaoAUmPerfil(permissao, perfil);
		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(),
				LocalDate.now().plusYears(1).toString());

		List<PermissaoDTO> permissoes = (List<PermissaoDTO>) controllerApi.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		assertEquals(1, permissoes.size());
		assertEquals("Relatório de compras", permissoes.get(0).getNomePermissao());
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(),
				LocalDate.now().plusYears(1).toString());
		assertEquals(1, controllerSemApi.listar().size());

		assertEquals(1, (controllerApi.consultarPorIdDoPerfil(perfil.getIdPerfil())).size());
	}

	@Test
	public void testConsultarPorIdDoUsuario() {
		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(),
				LocalDate.now().plusYears(1).toString());
		assertEquals(1, controllerApi.listar().size());

		usuario = usuarioController.consultarUsuario("thiago@gmail.com");
		assertEquals(1, (controllerApi.consultarPorIdDoUsuario(usuario.getIdUsuario())).size());
	}

	@Test
	public void testDeletar() {
		assertEquals(0, controllerApi.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario().getIdUsuario(), ligacao.getPerfil().getIdPerfil(),
				ligacao.getDataExpiracao().toString());
		assertEquals(1, controllerApi.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		controllerApi.deletar(ligacao.getId());
		assertEquals(0, controllerApi.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario().getIdUsuario(), ligacao.getPerfil().getIdPerfil(),
				ligacao.getDataExpiracao().toString());
		assertEquals(1, controllerApi.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		controllerApi.alterar(ligacao.getId(), ligacao);
		assertEquals(LocalDate.now().plusMonths(3), controllerApi.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao.getUsuario().getIdUsuario(), ligacao.getPerfil().getIdPerfil(),
				ligacao.getDataExpiracao().toString());
		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		assertEquals(1, controllerApi.listar().size());
		assertEquals("Vendedor", controllerApi.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao1.getUsuario().getIdUsuario(), ligacao1.getPerfil().getIdPerfil(),
				ligacao1.getDataExpiracao().toString());

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao2.getUsuario().getIdUsuario(), ligacao2.getPerfil().getIdPerfil(),
				ligacao2.getDataExpiracao().toString());

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao3.getUsuario().getIdUsuario(), ligacao3.getPerfil().getIdPerfil(),
				ligacao3.getDataExpiracao().toString());

		assertEquals(3, controllerApi.listar().size());
	}

	@Test
	public void testUsuarioNaoPossuiPermissaoPara() {
		boolean possui = controllerApi.usuarioPossuiPermissaoPara(usuario.getIdUsuario(), permissao.getIdPermissao());
		assertFalse(possui);
	}

	@Test
	public void testUsuarioPossuiPermissaoPara() {
		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(), LocalDate.now().toString());
		boolean possui = controllerApi.usuarioPossuiPermissaoPara(usuario.getIdUsuario(), permissao.getIdPermissao());
		assertTrue(possui);
	}

	@Test
	public void testUsuarioPossuiOPerfil() {
		controllerApi.atribuirPerfilAUmUsuario(usuario.getIdUsuario(), perfil.getIdPerfil(), LocalDate.now().toString());
		boolean possui = controllerApi.usuarioPossuiOPerfil(usuario.getIdUsuario(), perfil.getIdPerfil());
		assertTrue(possui);
	}

	@Test
	public void testUsuarioNaoPossuiOPerfil() {
		boolean possui = controllerApi.usuarioPossuiOPerfil(usuario.getIdUsuario(), perfil.getIdPerfil());
		assertFalse(possui);
	}

	@Test
	public void testPermissaoAtivaTrue() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao1.getUsuario().getIdUsuario(), ligacao1.getPerfil().getIdPerfil(),
				ligacao1.getDataExpiracao().toString());

		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao1 = ligacoes.get(0);
		assertTrue(controllerApi.permissaoAtiva(ligacao1.getId()));
	}

	@Test
	public void testPermissaoAtivaFalseData() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now());
		controllerApi.atribuirPerfilAUmUsuario(ligacao1.getUsuario().getIdUsuario(), ligacao1.getPerfil().getIdPerfil(),
				ligacao1.getDataExpiracao().toString());

		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao1 = ligacoes.get(0);
		controllerSemApi.desativar(ligacao1.getId());
		
		ArrayList<PerfilDeUsuario> ligacoes2 = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao1 = ligacoes2.get(0);
		
		assertFalse(controllerApi.permissaoAtiva(ligacao1.getId()));
	}

	@Test
	public void testPermissaoAtivaFalse() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controllerApi.atribuirPerfilAUmUsuario(ligacao1.getUsuario().getIdUsuario(), ligacao1.getPerfil().getIdPerfil(),
				ligacao1.getDataExpiracao().toString());

		ArrayList<PerfilDeUsuario> ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao1 = ligacoes.get(0);

		controllerSemApi.desativar(ligacao1.getId());

		ligacoes = controllerSemApi.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao1 = ligacoes.get(0);

		assertFalse(controllerApi.permissaoAtiva(ligacao1.getId()));
	}
}
