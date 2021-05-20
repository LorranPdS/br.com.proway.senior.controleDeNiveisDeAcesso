package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.acesso.UsuarioPerfil;
import model.acesso.UsuarioPerfilId;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;
import utils.Email;
import utils.HashSenha;

public class UsuarioController {
	
	private static UsuarioController instance;

	private UsuarioController() {
	}

	public static UsuarioController getInstance() {
		if (instance == null) {
			instance = new UsuarioController();
		}
		return instance;
	}
	
	/** UsuarioController
	 * Autentica uma tentativa de login de um usuario do sistema.
	 * 
	 * Um usuario entra com um login e senha. A senha deve ser criptografada antes de comparada com a salva no banco.
	 * Esse metodo NAO envia confirmacao de login 2FA.
	 * @param login
	 * @param senha
	 */
	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(login, senha);

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		if (usuario == null) {
			return false;
		} else {
			String senhaBanco = usuario.getHashSenha();

			if (senhaCriptografada.equals(senhaBanco)) {
				return true;
			}
			return false;
		}

	}

	/** UsuarioController
	 * Envia um e-mail
	 * 
	 * Envia um e-mail para o usuario com um codigo aleatorio gerado para a confirmacao de um login.
	 * O ultimo codigo enviado e salvo no banco de dados para futura confirmacao.
	 * 
	 * @param loginDoUsuario equivalente ao email do usuario.
	 * @param codigoGerado   Codigo aleatorio gerado pelo sistema
	 * @throws Exception
	 */
	public boolean enviarEmailDeConfirmacaoDeLogin(String emailDoDestinario) throws Exception {
		Usuario u = UsuarioDAO.getInstance().consultarPorLogin(emailDoDestinario);
		Integer codigoDeConfirmacao = gerarCodigoDeConfirmacao();
		u.setUltimoCodigo2FA(codigoDeConfirmacao);
		UsuarioDAO.getInstance().alterar(u);

		String nomeDoRemetente = "Grupo 3";
		String assuntoDoEmail = "2FA Niveis de Acesso";
		String corpoDoEmail = "O seu cÃƒÂ³digo ÃƒÂ©: " + codigoDeConfirmacao.toString();

		Email email = new Email(emailDoDestinario, nomeDoRemetente, assuntoDoEmail, corpoDoEmail);

		return (email.enviarEmail()) ? true : false;
	}

	/** UsuarioController
	 * Gera um codigo aleatorio
	 * 
	 * Gera o codigo random para a autenticacao 2FA de um login de usuario
	 * 
	 * @return codigo de 5 digitos
	 */
	private Integer gerarCodigoDeConfirmacao() {
		Random random = new Random();
		int codigo = random.nextInt(99999);
		if (codigo <= 10000) {
			codigo += 10000;
		}
		return codigo;
	}

	/** UsuarioController
	 * Confirma codigo 2FA.
	 * 
	 * Um usuario entra com um login e codigo de confirmacao (previamente recebido em seu email).
	 * O sistema verifica se o codigo bate com o salvo no banco de dados. 
	 * @param login
	 * @param codigoDeConfirmacao
	 */
	public boolean confirmarCodigoDeConfirmacao(String login, Integer codigoDeConfirmacao) {
		if (UsuarioDAO.getInstance().verificarCodigoDeConfirmacao(login, codigoDeConfirmacao) != null)
			return true;
		else
			return false;
	}
	
	/** UsuarioController
	 * Vai ser feita uma consulta no banco de dados pelo ID do usuario para saber se o {@link Usuario} tem permissoes.
	 * 
	 * @param usuario - Usuario
	 * @param permissao - Permissao
	 * @return ArrayList<Permissao>
	 */
	public boolean verificarPermissao(Usuario usuario, Permissao permissao) {
		List<Permissao> listaDePermissoesDesseUsuario = listarPermissoesDeUmUsuario(usuario.getIdUsuario());
		if (listaDePermissoesDesseUsuario.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}
	
	/** UsuarioController
	 * Criacao de um {@link Usuario} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Usuario} com os atributos login e senha.
	 * O objeto {@link Usuario} e enviado ao {@link UsuarioDAO} para ser persistido no banco de dados.
	 * 
	 * @param login
	 * @param senha
	 */
	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	/** UsuarioController
	 * Remocao de um {@link Usuario} pelo id.
	 * 
	 * Responsavel por consultar um {@link Usuario} pelo seu id no banco de dados, retornando o objeto
	 * com os dados do {@link Usuario} preenchidos e, posteriormente, enviando ao {@link UsuarioDAO} para
	 * ser removido do banco de dados.
	 * 
	 * @param Integer - id
	 */
	public void deletarUsuario(Integer id) {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(id);
		UsuarioDAO.getInstance().deletar(usuario);
	}

	 /** UsuarioController
	 * Alteracao de um {@link Usuario}.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves do id, o qual retornara o objeto
	 * completo. Feito isso, o login e a senha sera setados ao objeto e enviado ao {@link UsuarioDAO} para ser
	 * atualizado no banco de dados.
	 * 
	 * @param Integer - idUsuario
	 * @param String - login
	 * @param String - senha
	 */
	public void alterarUsuario(Integer idUsuario, String login, String senha) {
		Usuario usuario = consultarUsuario(idUsuario);
		usuario.setLogin(login);
		usuario.setHashSenha(HashSenha.criptografarSenha(login, senha));
		UsuarioDAO.getInstance().alterar(usuario);
	}

	/** UsuarioController
	 * Consulta de {@link Usuario} pelo id
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu id, o qual retornara o objeto
	 * completo.
	 * 
	 * @param Integer - idUsuario
	 * @throws NullPointerException - Caso nao exista o usuario no banco de dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(Integer idUsuario) {
		try {
			return UsuarioDAO.getInstance().consultarPorId(idUsuario);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/** UsuarioController
	 * Consulta de {@link Usuario} pelo login.
	 * 
	 * Sera feita uma consulta do {@link Usuario} no banco de dados atraves de seu nome, o qual retornara o objeto
	 * completo.
	 * 
	 * @param String
	 * @throws NullPointerException caso nao exista o {@link Usuario} no banco de dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(String login) {
		try {
			return UsuarioDAO.getInstance().consultarPorLogin(login);			
		} catch (NullPointerException e) {
			return null;
		}
	}

	/** UsuarioController
	 * Lista todos os {@link Usuario}.
	 * 
	 * Sera feita uma consulta de todos os {@link Usuario} registrados no banco de dados. Caso haja 
	 * {@link Usuario} registrados no banco de dados, eles serao retornados, caso contrario, sera retornado null.
	 * 
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listarTodosOsUsuarios() {
		ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) UsuarioDAO.getInstance().listar();
		ArrayList<Usuario> resultado = !usuariosEncontrados.isEmpty() ? usuariosEncontrados : null;
		return resultado;
	}

	/** UsuarioController
	 * Lista todas as {@link Permissao} do {@link Usuario}.
	 * 
	 * SerÃ¡ feita uma consulta de todas as {@link Permissao} registradas no banco de dados. Caso haja 
	 * {@link Permissao} registradas no banco de dados, elas serÃ£o retornadas, caso contrÃ¡rio, serÃ¡ retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Permissao> listaPermissao = UsuarioDAO.getInstance().listarPermissoes(idUsuario);
		List<Permissao> resultado = !listaPermissao.isEmpty() ? listaPermissao : null;
		return resultado;
	}

	/** UsuarioController
	 * Lista todos os {@link Perfil} do {@link Usuario}.
	 * 
	 * SerÃ¡ feita uma consulta de todos os {@link Perfil} registrados no banco de dados. Caso haja 
	 * {@link Perfil} registrados no banco de dados, eles serÃ£o retornados, caso contrÃ¡rio, serÃ¡ retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Perfil>
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		List<Perfil> listaPerfis =  UsuarioDAO.getInstance().listarPerfis(idUsuario);
		List<Perfil> resultado = !listaPerfis.isEmpty() ? listaPerfis : null;
		return resultado;
	}

	/** UsuarioController
	 * Ã‰ atribuido um {@link Perfil} a um {@link Usuario} quando Ã© passado um {@link Usuario} para aquele {@link Perfil}.
	 * ApÃ³s criado, vai verificar se a data de expiracao nao e nula e vai criar um {@link Perfil} com a data expiracao, se 
	 * caso a data de expiracao for nula e criado um {@link Perfil} sem data de expiracao.
	 * 
	 * @param usuario Usuario
	 * @param perfil Perfil
	 * @param dataExp LocalDate
	 */
	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil, LocalDate dataExp) {
		UsuarioPerfilId userPerfilId = new UsuarioPerfilId(usuario.getIdUsuario(), perfil.getIdPerfil());
		UsuarioPerfil usuarioPerfil;
		if (dataExp != null) {
			usuarioPerfil = new UsuarioPerfil(userPerfilId, usuario, perfil, dataExp);
		} else {
			usuarioPerfil = new UsuarioPerfil(userPerfilId, usuario, perfil);
		}
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(usuarioPerfil);
	}
}
