package controller.controllerApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PerfilDeUsuarioController;
import controller.controllers.UsuarioController;
import model.entidades.Usuario;

/**
 * a classe possui metodos que não são de nenhuma entidade do sistema, fazem
 * interaçoes com email, e o login de um usuario
 * 
 * @author Marcelo schaefer <b>marceloshaeferfilho@gmail.com</b> - sprint7
 *
 */
@RestController
public class LoginControllerApi {

	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	UsuarioController UController = new UsuarioController();

	public LoginControllerApi() {
	}

	/**
	 * Autentica uma tentativa de login de um usuario do sistema.
	 * 
	 * Um usuario entra com um login e senha. A senha deve ser criptografada antes
	 * de comparada com a salva no banco. Esse metodo NAO envia confirmacao de login
	 * 2FA. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param login
	 * @param senha
	 */
	@PostMapping("/login")
	public boolean logar(@RequestParam String login, @RequestParam String senha) {
		return UsuarioController.getInstance().logar(login, senha);

	}

	/**
	 * Envia um e-mail
	 * 
	 * Envia um e-mail para o usuario com um codigo aleatorio gerado para a
	 * confirmacao de um login. O ultimo codigo enviado e salvo no banco de dados
	 * para futura confirmacao. Chamando o metodo respectivo da classe
	 * {@link UsuarioController}.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   Codigo aleatorio gerado pelo sistema
	 * @throws Exception
	 */
	@PostMapping("/login/{emailDoDestinatario} ")
	public boolean enviarEmailDeConfirmacaoDeLogin(@RequestBody Usuario usuario) throws Exception {
		return UsuarioController.getInstance().enviarEmailDeConfirmacaoDeLogin(usuario.getLogin());
	}

	/**
	 * Confirma codigo 2FA.
	 * 
	 * Um usuario entra com um login e codigo de confirmacao (previamente recebido
	 * em seu email). O sistema verifica se o codigo bate com o salvo no banco de
	 * dados. Chamando o metodo respectivo da classe {@link UsuarioController}.
	 * 
	 * @param login.
	 * @param codigoDeConfirmacao
	 */
	@GetMapping("/login/{login}")
	public boolean confirmarCodigoDeConfirmacao(@RequestBody Usuario usuario) {
		return UsuarioController.getInstance().confirmarCodigoDeConfirmacao(usuario.getLogin(), usuario.getUltimoCodigo2FA());
	}

}
