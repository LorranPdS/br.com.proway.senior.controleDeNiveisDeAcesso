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
 * Classe Controller
 * 
 * Classe responsÃ¡vel por intermediar os dados da View e Model. Os mÃ©todos dessa
 * classe definem a API de nosso sistema.
 * 
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Lucas GrijÃ³, rksgrijo@gmail.com
 * @author Thiago, thiagoluizbarbieri@gmail.com
 */
public class Controller {

	static Controller instance;

	private Controller() {
	}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	// Funcionalidades Principais

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
	 * Envia o e-mail para o usuario com codigo aleatorio gerado para a confirmacao.
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
		String corpoDoEmail = "O seu cÃ³digo Ã©: " + codigoDeConfirmacao.toString();

		Email email = new Email(emailDoDestinario, nomeDoRemetente, assuntoDoEmail, corpoDoEmail);

		return (email.enviarEmail()) ? true : false;
	}

	/**
	 * Gera um codigo aleatorio
	 * 
	 * Gera o codigo random para a verificacao de usuario
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
	 * Criação de um {@link Usuario} no objeto.
	 * 
	 * Responsável por criar um objeto do tipo {@link Usuario} com os atributos login e senha.
	 * O objeto {@link Usuario} é enviado ao {@link UsuarioDAO} para ser persistido no banco de dados.
	 * 
	 * @param login
	 * @param senha
	 */
	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	/**
	 * Remoção de um {@link Usuario} pelo id.
	 * 
	 * Responsável por consultar um {@link Usuario} pelo seu id no banco de dados, retornando o objeto
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
	 * Será feita uma consulta do {@link Usuario} no banco de dados através do id, o qual retornará o objeto
	 * completo. Feito isso, o login e a senha serão setados ao objeto e enviado ao {@link UsuarioDAO} para ser
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
	 * Será feita uma consulta do {@link Usuario} no banco de dados através de seu id, o qual retornará o objeto
	 * completo.
	 * 
	 * @param Integer - idUsuario
	 * @throws NullPointerException - Caso não exista o usuário no banco de dados.
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
	 * Será feita uma consulta do {@link Usuario} no banco de dados através de seu nome, o qual retornará o objeto
	 * completo.
	 * 
	 * @param String
	 * @throws NullPointerException caso não exista o {@link Usuario} no banco de dados.
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
	 * Será feita uma consulta de todos os {@link Usuario} registrados no banco de dados. Caso haja 
	 * {@link Usuario} registrados no banco de dados, eles serão retornados, caso contrário, será retornado null.
	 * 
	 * @return ArrayList<Usuario>
	 */
	public ArrayList<Usuario> listarTodosOsUsuarios() {
		ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) UsuarioDAO.getInstance().listar();
		ArrayList<Usuario> resultado = !usuariosEncontrados.isEmpty() ? usuariosEncontrados : null;
		return resultado;
	}

	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		return UsuarioDAO.getInstance().listarPermissoes(idUsuario);
	}

	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		return UsuarioDAO.getInstance().listarPerfis(idUsuario);
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

	public void criarPerfil(String nomePerfil) {
		Perfil perfil = new Perfil(nomePerfil);
		PerfilDAO.getInstance().criar(perfil);
	}

	public void alterarPerfil(Integer idPerfil, String nomePerfil) {
		Perfil perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		perfil.setNomePerfil(nomePerfil);
		PerfilDAO.getInstance().alterar(perfil);
	}

	public void deletarPerfil(Integer idPerfil) {
		Perfil p = PerfilDAO.getInstance().consultarPorId(idPerfil);
		PerfilDAO.getInstance().deletar(p);
	}

	public Perfil consultarPerfil(Integer idPerfil) {
		try {
			return PerfilDAO.getInstance().consultarPorId(idPerfil);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Perfil consultarPerfil(String nome) {
		try {
			return PerfilDAO.getInstance().consultarPorNome(nome);
		} catch (NullPointerException e) {
			return null;
		}

	}

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

	public void expirarTodasAsPermissoesDoSistema() {
		List<Usuario> listaUsuario = UsuarioDAO.getInstance().listar();
		for ( Usuario usuario : listaUsuario) {
			UsuarioDAO.getInstance().consultarPorId(usuario.getIdUsuario());
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

	public void expirarTodasAsSenhaDoSistema() {

	}

}


