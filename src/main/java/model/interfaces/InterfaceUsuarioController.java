package model.interfaces;

import model.acesso.Usuario;

/**
 * @author Sprint 3
 * @author David Willian, david.oliveira@senior.com.br
 * @author Leonardo Pereira, leonardo.pereira@senior.com.br
 * @author Vitor Peres, vitor.peres@senior.com.br
 */
public interface InterfaceUsuarioController {

	public boolean validarEmail(String email);

	public boolean validarSenha(String senha);

	public String enviarEmail(String loginDoUsuario);

}