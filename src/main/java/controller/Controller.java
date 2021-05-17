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
 * Classe responsável por intermediar os dados da View e Model
 * 
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Lucas Grijó, rksgrijo@gmail.com
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

	public boolean logar(String login, String senha) {

		String senhaCriptografada = HashSenha.criptografarSenha(login, senha);

		Usuario usuario = UsuarioDAO.getInstance().consultarPorLogin(login);
		String senhaBanco = usuario.getHashSenha();

		if (senhaCriptografada.equals(senhaBanco)) {
			return true;
		}
		return false;
	}

	public boolean verificarPermissao(Usuario usuario, Permissao permissao) {
		List<Permissao> listaDePermissoesDesseUsuario = listarPermissoesDeUmUsuario(usuario.getIdUsuario());
		if(listaDePermissoesDesseUsuario.contains(permissao)) {
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

	// DAO - Usuario

	public void criarUsuario(String login, String senha) {
		Usuario usuario1 = new Usuario(login, senha);
		UsuarioDAO.getInstance().criar(usuario1);
	}

	public void deletarUsuario(Integer id) {
		// UsuarioDAO.getInstance().deletar(id);
	}

	public void alterarUsuario(Integer idUsuario, Usuario usuario) {

	}

	public Usuario consultarUsuario(Integer idUsuario) {
		return null;
	}

	public Usuario consultarUsuario(String login) {
		return UsuarioDAO.getInstance().consultarPorLogin(login);
	}

	public ArrayList<Usuario> listarTodosOsUsuarios() {
		return null;
	}

	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		return UsuarioDAO.getInstance().listarPermissoes(idUsuario);
	}

	public List<Perfil> listarPerfisDeUmUsuario(Usuario usuario) {
		return null;
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
		Perfil perfil = new Perfil();
		perfil.setIdPerfil(idPerfil);
		perfil.setNomePerfil(nomePerfil);
		PerfilDAO.getInstance().alterar(perfil);
	}

	public void deletarPerfil(Perfil perfil) {
		PerfilDAO.getInstance().deletar(perfil);
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

	public ArrayList<Perfil> listarTodosOsPerfils() {
		return (ArrayList<Perfil>) PerfilDAO.getInstance().listar();
	}

	public List<Permissao> listarPermissoesDeUmPerfil(int idPerfil) {
		return PerfilDAO.getInstance().listarPermissoes(idPerfil);
	}

	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {
		PerfilDAO.getInstance().atribuirPermissaoAUmPerfil(perfil, permissao);
	}

	// DAO - Permissao

	public void criarPermissao(String nomePermissao) {
		Permissao permissao = new Permissao(nomePermissao);
		PermissaoDAO.getInstance().criar(permissao);
	}

	public void alterarPermissao(Integer idPermissao, Permissao permissao) {

	}

	public void deletarPermissao(Integer idPermissao) {

	}

	public Permissao consultarPermissao(Integer idPermissao) {
		return null;
	}

	/**
	 * Consultará no banco de dados a permissão
	 * 
	 * @param nomePermissao
	 * @return Permissao
	 */
	public Permissao consultarPermissao(String nomePermissao) {
		return PermissaoDAO.getInstance().consultarPorNome(nomePermissao);
	}

	public ArrayList<Permissao> listarTodasAsPermissoes() {
		return null;
	}

	// ...

	// MISC

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
		Email email = new Email(emailDoDestinario, "Grupo 3", "2FA Niveis de Acesso",
				"O seu código é: " + gerarCodigo().toString());

		// ABSTRAIR MAIS ESSA LÓGICA (usar condicional ternaria)
		boolean resultadoEnvio = email.enviarEmail();
		if (resultadoEnvio == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gera um codigo aleatorio
	 * 
	 * Gera o codigo random para a verificacao de usuario
	 * 
	 * @return codigo de 5 digitos
	 */
	private Integer gerarCodigo() {

		Random random = new Random();
		int codigo = random.nextInt(99999);
		if (codigo <= 10000) {
			codigo += 10000;
		}
		return codigo;
	}

	// ROTINAS AUTOMATICAS

	public void expirarTodasAsPermissoesDoSistema() {

	}

	public void expirarTodasAsSenhaDoSistema() {

	}

}
