package controller;

import java.time.LocalDate;
import java.util.List;

import model.acesso.Perfil;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import model.acesso.UsuarioPerfil;

public class RotinasController {
	
	private static RotinasController instance;

	private RotinasController() {
	}

	public static RotinasController getInstance() {
		if (instance == null) {
			instance = new RotinasController();
		}
		return instance;
	}
	
	/** RotinaController
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

	/** RotinaController
	 * Expira senhas que passaram da validade.
	 * 
	 * Esse metodo deve verificar o campo ultima_alteracao_senha do banco de todos os usuarios do sistema e verificar se elas
	 * passaram da data de validade (regra de negocio a ser definida). Apos isso ele deve bloquear o acesso dos usuarios expirados
	 * ate que eles troquem a senha atraves de uma mensagem enviada automaticamente em seu email/login.
	 */
	public void expirarTodasAsSenhaDoSistema() {

	}

}
