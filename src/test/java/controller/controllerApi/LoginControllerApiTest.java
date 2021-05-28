package controller.controllerApi;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import controller.controllers.PermissaoController;
import controller.controllers.UsuarioController;
import db.DBConnection;
import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class LoginControllerApiTest {
	
	LoginControllerApi loginApi = new LoginControllerApi();
	UsuarioControllerApi usuarioApi = new UsuarioControllerApi();
	UsuarioController usuarioController = new UsuarioController();

	@BeforeEach
	public void deletarTudo() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		usuarioController.deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@BeforeAll
	public static void limparEPopularTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();
	}

	@AfterAll
	public static void limparTabelas() {
		PerfilDeUsuarioDAO.getInstance().deletarTodos();
		UsuarioDAO.getInstance().deletarTodos();
		PerfilDAO.getInstance().deletarTodos();
		PermissaoDAO.getInstance().deletarTodos();

	}

	UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
	PerfilDAO perfilDAO = PerfilDAO.getInstance();
	static Perfil perfil;
	static Permissao permissao;
	static PermissaoController permissaoController = new PermissaoController();

	@org.junit.jupiter.api.Test
	public void testLogin() {
		String loginExistente = "Grijo@gmail.com";
		String senhaCorreta = "234";
		String senhaIncorreta = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(loginExistente, senhaCorreta));
		Usuario usuario = UsuarioController.getInstance().consultarUsuario(loginExistente);
		Usuario usuarioErrado = new Usuario();
		usuarioErrado.setLogin(loginExistente);
		usuarioErrado.setHashSenha(senhaIncorreta);
		assertTrue(loginApi.logar(usuario));
		assertFalse(loginApi.logar(usuarioErrado));
	}

	/**
	 * Ao executar esse teste, sinta-se livre para utilizar um email real mudando a
	 * string destinatario.
	 * 
	 * @throws Exception
	 */
	@org.junit.jupiter.api.Test
	public void testEnviarEConfirmarEmailDeConfirmacaoDeLogin() throws Exception {

		String destinatario = "Email@gmail.com"; // Seja responsável e não spame os amiguinhos. ^^
		String senha = "123";
		UsuarioController.getInstance().criarUsuario(new Usuario(destinatario, senha));

		boolean resultadoEnvioEmail = loginApi.enviarEmailDeConfirmacaoDeLogin(destinatario);
		assertTrue(resultadoEnvioEmail);

		Usuario u = UsuarioController.getInstance().consultarUsuario(destinatario);
		assertTrue(loginApi.confirmarCodigoDeConfirmacao(u));

		// Limpar usuario do banco pós-teste
		String sqlLimparUsuarioDoBanco = "DELETE FROM usuario WHERE login = '" + destinatario + "';";
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession().createSQLQuery(sqlLimparUsuarioDoBanco).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
