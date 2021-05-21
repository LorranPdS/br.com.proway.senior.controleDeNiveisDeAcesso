package controller;

import java.time.LocalDate;

import model.acesso.PerfilDeUsuario;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Usuario;

public class PerfilDeUsuarioController {
	
	/**
	 * Atribui um {@link Perfil} a um {@link Usuario}.
	 * 
	 * Método responsável por atribuir um {@link Perfil} a um {@link Usuario}.
	 * 
	 * @param PerfilDeUsuario - usuarioPerfil
	 * @throws Exception - Caso a atribuição do {@link Perfil} ao {@link Usuario}
	 *                   não seja possivel.
	 * @since Sprint 4&5
	 */
	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil, LocalDate dataExpiracao){
		PerfilDeUsuario perfilDeUsuario = new PerfilDeUsuario(usuario, perfil, dataExpiracao);
		PerfilDeUsuarioDAO.getInstance().atribuirPerfilAUmUsuario(perfilDeUsuario);
	}
	
	
}
