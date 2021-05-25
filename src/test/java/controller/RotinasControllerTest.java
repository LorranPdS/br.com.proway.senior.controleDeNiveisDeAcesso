package controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.controllerApi.PerfilControllerApi;
import controller.controllerApi.PerfilDeUsuarioControllerApi;
import controller.controllerApi.PermissaoControllerApi;
import controller.controllerApi.UsuarioControllerApi;
import controller.controllers.PerfilController;
import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.PermissaoController;
import controller.controllers.RotinasController;
import controller.controllers.UsuarioController;
import model.dto.PerfilDTO;
import model.dto.PermissaoDTO;
import model.dto.UsuarioDTO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
public class RotinasControllerTest {

	@BeforeClass
	public static void limparEPopularTabelas() {
		perfilDeUsuarioApi.deletarTodos();
		usuarioApi.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();

		popularTabelas();
	}

	@AfterClass
	public static void limparTabelas() {
		perfilDeUsuarioApi.deletarTodos();
		usuarioApi.deletarTodos();
		perfilApi.deletarTodos();
		permissaoApi.deletarTodos();
	} 

	static RotinasController rotinasController = new RotinasController();
	static PerfilController perfilController = new PerfilController();
	static UsuarioController usuarioController = new UsuarioController();
	static PermissaoController permissaoController = new PermissaoController();
	static PerfilDeUsuarioController perfilDeUsuarioController = new PerfilDeUsuarioController();

	static PerfilDeUsuarioControllerApi perfilDeUsuarioApi = new PerfilDeUsuarioControllerApi();
	static PerfilControllerApi perfilApi = new PerfilControllerApi();
	static UsuarioControllerApi usuarioApi = new UsuarioControllerApi();
	static PermissaoControllerApi permissaoApi = new PermissaoControllerApi();

	static Usuario usuario1;
	static Usuario usuario2;
	static Usuario usuario3;
	static Usuario usuario4;
	static Usuario usuario5;

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

		usuarioApi.criarUsuario("carlos@gmail.com", "admin");
		usuarioApi.criarUsuario("julia@gmail.com", "admin123");
		usuarioApi.criarUsuario("joao@gmail.com", "admin");
		usuarioApi.criarUsuario("maria@gmail.com", "admin");
		usuarioApi.criarUsuario("alan@gmail.com", "admin");

		usuario1 = usuarioController.consultarUsuario("carlos@gmail.com");
		usuario2 = usuarioController.consultarUsuario("julia@gmail.com");
		usuario3 = usuarioController.consultarUsuario("joao@gmail.com");
		usuario4 = usuarioController.consultarUsuario("maria@gmail.com");
		usuario5 = usuarioController.consultarUsuario("alan@gmail.com");
	}

	@Test
	public void testDesativarTodasPermissoesExpiradas() {
		perfilDeUsuarioApi.atribuirPerfilAUmUsuario(usuario1, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioApi.atribuirPerfilAUmUsuario(usuario2, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioApi.atribuirPerfilAUmUsuario(usuario3, perfil, LocalDate.now().plusYears(1));
		perfilDeUsuarioApi.atribuirPerfilAUmUsuario(usuario4, perfil, LocalDate.of(2002, 02, 02));
		perfilDeUsuarioApi.atribuirPerfilAUmUsuario(usuario5, perfil, LocalDate.of(2002, 02, 02));

		assertEquals(5, perfilDeUsuarioController.listarTodasLigacoesAtivas().size());
		
		assertEquals(2, rotinasController.desativarTodasPermissoesExpiradas());
		assertEquals(3, perfilDeUsuarioController.listarTodasLigacoesAtivas().size());
	}

}
