package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.acesso.Perfil;
import model.acesso.PerfilDAO;
import model.acesso.Permissao;
import model.acesso.PermissaoDAO;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import model.acesso.UsuarioPerfil;
import model.acesso.UsuarioPerfilId;
import utils.Email;
import utils.HashSenha;

/**
 * Classe responsável por intermediar os dados da View e Model.
 * 
 * Os métodos dessa classe definem a API de nosso sistema.
 * 
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Lucas Grijó, rksgrijo@gmail.com
 * @author Thiago, thiagoluizbarbieri@gmail.com
 * @since Sprint 4&5.
 */
public class Controller {

	private static Controller instance;

	private Controller() {
	}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Funcionalidades Principais

	/**
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

	/**
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
		String corpoDoEmail = "O seu código é: " + codigoDeConfirmacao.toString();

		Email email = new Email(emailDoDestinario, nomeDoRemetente, assuntoDoEmail, corpoDoEmail);

		return (email.enviarEmail()) ? true : false;
	}

	/**
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

	/**
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

	public boolean verificarPermissao(Usuario usuario, Permissao permissao) {
		List<Permissao> listaDePermissoesDesseUsuario = listarPermissoesDeUmUsuario(usuario.getIdUsuario());
		if (listaDePermissoesDesseUsuario.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verificarPermissao(Perfil perfil, Permissao permissao) {
		List<Permissao> listaDePermissoesDessePerfil = listarPermissoesDeUmPerfil(perfil.getIdPerfil());
		if (listaDePermissoesDessePerfil.contains(permissao)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Cria��o de um {@link Usuario} no objeto.
	 * 
	 * Respons�vel por criar um objeto do tipo {@link Usuario} com os atributos login e senha.
	 * O objeto {@link Usuario} � enviado ao {@link UsuarioDAO} para ser persistido no banco de dados.
	 * 
	 * @param login
	 * @param senha
	 */
	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	/**
	 * Remo��o de um {@link Usuario} pelo id.
	 * 
	 * Respons�vel por consultar um {@link Usuario} pelo seu id no banco de dados, retornando o objeto
	 * com os dados do {@link Usuario} preenchidos e, posteriormente, enviando ao {@link UsuarioDAO} para
	 * ser removido do banco de dados.
	 * 
	 * @param Integer - id
	 */
	public void deletarUsuario(Integer id) {
		Usuario usuario = UsuarioDAO.getInstance().consultarPorId(id);
		UsuarioDAO.getInstance().deletar(usuario);
	}

	 /**
	 * Alteracao de um {@link Usuario}.
	 * 
	 * Ser� feita uma consulta do {@link Usuario} no banco de dados atrav�s do id, o qual retornar� o objeto
	 * completo. Feito isso, o login e a senha ser�o setados ao objeto e enviado ao {@link UsuarioDAO} para ser
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

	/**
	 * Consulta de {@link Usuario} pelo id
	 * 
	 * Ser� feita uma consulta do {@link Usuario} no banco de dados atrav�s de seu id, o qual retornar� o objeto
	 * completo.
	 * 
	 * @param Integer - idUsuario
	 * @throws NullPointerException - Caso n�o exista o usu�rio no banco de dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(Integer idUsuario) {
		try {
			return UsuarioDAO.getInstance().consultarPorId(idUsuario);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Consulta de {@link Usuario} pelo login.
	 * 
	 * Ser� feita uma consulta do {@link Usuario} no banco de dados atrav�s de seu nome, o qual retornar� o objeto
	 * completo.
	 * 
	 * @param String
	 * @throws NullPointerException caso n�o exista o {@link Usuario} no banco de dados.
	 * @return Usuario
	 */
	public Usuario consultarUsuario(String login) {
		try {
			return UsuarioDAO.getInstance().consultarPorLogin(login);			
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Lista todos os {@link Usuario}.
	 * 
	 * Ser� feita uma consulta de todos os {@link Usuario} registrados no banco de dados. Caso haja 
	 * {@link Usuario} registrados no banco de dados, eles ser�o retornados, caso contr�rio, ser� retornado null.
	 * 
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listarTodosOsUsuarios() {
		ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) UsuarioDAO.getInstance().listar();
		ArrayList<Usuario> resultado = !usuariosEncontrados.isEmpty() ? usuariosEncontrados : null;
		return resultado;
	}

	/**
	 * Lista todas as {@link Permissao} do {@link Usuario}.
	 * 
	 * Ser� feita uma consulta de todas as {@link Permissao} registradas no banco de dados. Caso haja 
	 * {@link Permissao} registradas no banco de dados, elas ser�o retornadas, caso contr�rio, ser� retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Permissao> listaPermissao = UsuarioDAO.getInstance().listarPermissoes(idUsuario);
		List<Permissao> resultado = !listaPermissao.isEmpty() ? listaPermissao : null;
		return resultado;
	}

	/**
	 * Lista todos os {@link Perfil} do {@link Usuario}.
	 * 
	 * Ser� feita uma consulta de todos os {@link Perfil} registrados no banco de dados. Caso haja 
	 * {@link Perfil} registrados no banco de dados, eles ser�o retornados, caso contr�rio, ser� retornado null.
	 * 
	 * @param idUsuario - int
	 * @return List<Perfil>
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		List<Perfil> listaPerfis =  UsuarioDAO.getInstance().listarPerfis(idUsuario);
		List<Perfil> resultado = !listaPerfis.isEmpty() ? listaPerfis : null;
		return resultado;
	}

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

	/**
	 * Criacao de um {@link Perfil} no objeto.
	 * 
	 * Responsavel por criar um objeto do tipo {@link Perfil} com o atributo
	 * nomePerfil. O objeto {@link Perfil} e enviado ao {@link PerfilDAO} para ser
	 * persistido no banco de dados.
	 * 
	 * @param String - nomePerfil
	 * 
	 */
	public void criarPerfil(String nomePerfil) {
		Perfil perfil = new Perfil(nomePerfil);
		PerfilDAO.getInstance().criar(perfil);
	}

	/**
	 * Alteracao de um {@link Perfil}.
	 * 
	 * Responsavel por alterar um {@link Perfil} pre existente com os atributos
	 * idPerfil e nomePerfil. A busca e realizada pelo idPerfil por intermedio do
	 * PerfilDAO,o nome do perfil e adicionado ao objeto e alterado no banco de
	 * dados.
	 * 
	 * @param Integer idPerfil
	 * @param String  nomePerfil
	 */
	public void alterarPerfil(Integer idPerfil, String nomePerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		perfil.setNomePerfil(nomePerfil);
		PerfilDAO.getInstance().alterar(perfil);
	}

	/**
	 * Remoção de um {@link Perfil} pelo id.
   *
	 * Responsável por deletar um objeto do tipo {@link Perfil} com os atributos idPerfil.
	 * O objeto {@link Perfil} é enviado ao {@link Perfil} para ser removido no banco de dados.
	 * 
	 * @param idPerfil - Integer
	 */
	public void deletarPerfil(Integer idPerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		PerfilDAO.getInstance().deletar(perfil);
	}

	/**
	 * Consulta de {@link Perfil} pelo id.
	 * 
	 * Responsavel por alterar um {@link Perfil} pre existente com os atributos
	 * idPerfil e nomePerfil. A busca e realizada pelo idPerfil por intermedio do
	 * {@link PerfilDAO},o nome do perfil e adicionado ao objeto e alterado no banco de
	 * dados.
	 * 
	 * 
	 * @param Integer 
	 * @throws NullPointerException Caso não exista o {@link Perfil} no banco de dados.
	 * @return Perfil
	 */
	public Perfil consultarPerfil(Integer idPerfil) {
		try {
			return PerfilDAO.getInstance().consultarPorId(idPerfil);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Tem a funcao de consultar por nome um objeto do tipo {@link Perfil} com o atributo nomePerfil.
	 * O objeto {@link Perfil} vai ser consultado no banco de dados pelo nome.
	 * 
	 * @param nome - String
	 * @return {@link Perfil} 
	 */
	public Perfil consultarPerfil(String nome) {
		try {
			return PerfilDAO.getInstance().consultarPorNome(nome);
		} catch (NullPointerException e) {
			return null;
		}

	}

	/**
	 * Consulta todos os perfis no banco de dados.
	 * 
	 * @return resultado - ArrayList<Perfil>
	 */
	public ArrayList<Perfil> listarTodosOsPerfis() {
		ArrayList<Perfil> perfisEncontrados = (ArrayList<Perfil>) PerfilDAO.getInstance().listar();
		ArrayList<Perfil> resultado = !perfisEncontrados.isEmpty() ? perfisEncontrados : null;
		return resultado;

	}

	public List<Permissao> listarPermissoesDeUmPerfil(int idPerfil) {
		return PerfilDAO.getInstance().listarPermissoes(idPerfil);
	}

	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	// DAO - Permissaos

	public void criarPermissao(String nomePermissao) {
		Permissao permissao = new Permissao(nomePermissao);
		PermissaoDAO.getInstance().criar(permissao);
	}

	/**
	 * Alteracao de uma {@link Permissao}.
	 * 
	 * Ser� feita uma consulta da {@link Permissao} no banco de dados atrav�s do id e nome da permissao,
	 * o qual retornar� o objeto completo. Feito isso, o novo nome da {@link Permissao} ser� setado ao objeto
	 * e enviado ao {@link PermissaoDAO} para ser atualizado no banco de dados.
	 * 
	 * @param idPermissao - Interger
	 * @param nomePermissao - String
	 */
	public void alterarPermissao(Integer idPermissao, String nomePermissao) {
		Permissao p = consultarPermissao(idPermissao);
		p.setNomePermissao(nomePermissao);
		PermissaoDAO.getInstance().alterar(p);
	}

	public void deletarPermissao(Integer idPermissao) {
		Permissao p = consultarPermissao(idPermissao);
		PermissaoDAO.getInstance().deletar(p);
	}

	public Permissao consultarPermissao(Integer idPermissao) {
		return PermissaoDAO.getInstance().consultarPorId(idPermissao);
	}

	public Permissao consultarPermissao(String nomePermissao) {
		return PermissaoDAO.getInstance().consultarPorNome(nomePermissao);
	}

	public List<Permissao> listarTodasAsPermissoes() {
		return PermissaoDAO.getInstance().listar();
	}

	// ROTINAS AUTOMATICAS
	/**
	 * Remove todas as permissoes expiradas. 
	 * 
	 * Lista todos os {@link Usuario}s e remove a atribuicao de {@link Perfil}, 
	 * caso a data tenha vencido.
	 */
	public void expirarTodasAsPermissoesDoSistema() {
		List<Usuario> listaUsuario = UsuarioDAO.getInstance().listar();
		for ( Usuario usuario : listaUsuario) {
			for (UsuarioPerfil usuarioPerfil : usuario.getPerfis()) {
				if (usuarioPerfil.getDataExpiracao() != null) {
					if (usuarioPerfil.getDataExpiracao().isBefore(LocalDate.now())) {
						UsuarioDAO.getInstance().removerPerfilDeUmUsuario(usuarioPerfil.getPerfil().getIdPerfil(), 
								usuarioPerfil.getUsuario().getIdUsuario());
					}
				}
			}
		}
	}

	/**
	 * Expira senhas que passaram da validade.
	 * 
	 * Esse metodo deve verificar o campo ultima_alteracao_senha do banco de todos os usuarios do sistema e verificar se elas
	 * passaram da data de validade (regra de negocio a ser definida). Apos isso ele deve bloquear o acesso dos usuarios expirados
	 * ate que eles troquem a senha atraves de uma mensagem enviada automaticamente em seu email/login.
	 */
	public void expirarTodasAsSenhaDoSistema() {

	}

}
