package controller.controllerApi;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.PerfilController;
import controller.PerfilDeUsuarioController;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class PerfilDeUsuarioControllerApiTest {

	@Before
	public void deletarTudoDoPerfilDeUsuario() {
		controller.deletarTodos();
	}

	@BeforeClass
	public static void limparEPopularTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		controller.deletarTodos();
		usuarioDAO.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();
	}

	static PerfilDeUsuarioControllerApi controller = new PerfilDeUsuarioControllerApi();
	static PerfilControllerApi perfilApi = new PerfilControllerApi();
	static UsuarioControllerApi usuarioDAO = new UsuarioControllerApi();
	static PermissaoControllerApi permissaoApi = new PermissaoControllerApi();
	static UsuarioDTO usuario;
	static PermissaoDTO permissao;
	static PerfilDTO perfil;

	public static void popularTabelas() {
		perfilApi.criarPerfil("Vendedor");
		perfil = perfilApi.consultarPerfil("Vendedor");

		permissaoApi.criarPermissao("Relatório de compras");
		permissao = permissaoApi.consultarPermissaoPorNome("Relatório de compras");
		
		perfilApi.atribuirPermissaoAUmPerfil(permissao, perfil);
		perfil = perfilApi.consultarPerfil("Vendedor");

		usuarioDAO.criarUsuario("thiago@gmail.com", "admin");
		usuario = usuarioDAO.consultarUsuario("thiago@gmail.com");
	}

	@Test
	public void testListarPerfisDeUmUsuario() {
		usuario = usuarioDAO.consultarUsuario("thiago@gmail.com");

		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		assertEquals(1, controller.listarPerfisDeUmUsuario(usuario.getIdUsuario()).size());
	}

	@Test
	public void testListarPermissoesDeUmUsuario() {
		perfilApi.atribuirPermissaoAUmPerfil(perfil, permissao);
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));

		ArrayList<PermissaoDTO> permissoes = controller.listarPermissoesDeUmUsuario(usuario.getIdUsuario());

		assertEquals(1, permissoes.size());
		assertEquals("Relatório de compras", permissoes.get(0).getNomePermissao());
	}

	@Test
	public void testConsultarPorIdDoPerfil() {
		controller.atribuirPerfilAUmUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		assertEquals(1, controller.listar().size());
		

		assertEquals(1, controller.consultarPorIdDoPerfil(perfil.getIdPerfil()).size());
	}

	@Test
	public void testDeletar() {
		assertEquals(0, controller.listar().size());
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		controller.deletar(ligacao);
		assertEquals(0, controller.listar().size());
	}

	@Test
	public void testAlterar() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		assertEquals(1, controller.listar().size());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);

		ligacao.setDataExpiracao(LocalDate.now().plusMonths(3));
		controller.alterar(ligacao);
		assertEquals(LocalDate.now().plusMonths(3), controller.consultarPorId(ligacao.getId()).getDataExpiracao());
	}

	@Test
	public void testConsultarPorId() {
		PerfilDeUsuario ligacao = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao.getUsuario(), ligacao.getPerfil(), ligacao.getDataExpiracao());
		ArrayList<PerfilDeUsuario> ligacoes = controller.consultarPorIdDoPerfil(perfil.getIdPerfil());
		ligacao = ligacoes.get(0);
		
		assertEquals(1, controller.listar().size());
		assertEquals("Vendedor", controller.consultarPorId(ligacao.getId()).getPerfil().getNomePerfil());
	}

	@Test
	public void testListar() {
		PerfilDeUsuario ligacao1 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao1.getUsuario(), ligacao1.getPerfil(), ligacao1.getDataExpiracao());

		PerfilDeUsuario ligacao2 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao2.getUsuario(), ligacao2.getPerfil(), ligacao2.getDataExpiracao());

		PerfilDeUsuario ligacao3 = new PerfilDeUsuario(usuario, perfil, LocalDate.now().plusYears(1));
		controller.atribuirPerfilAUmUsuario(ligacao3.getUsuario(), ligacao3.getPerfil(), ligacao3.getDataExpiracao());

		assertEquals(3, controller.listar().size());
	}
}
