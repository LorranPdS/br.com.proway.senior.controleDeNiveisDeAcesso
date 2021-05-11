package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.acesso.HashSenha;
import model.acesso.Perfil;
import model.acesso.Permissao;
import model.acesso.Usuario;
import model.acesso.UsuarioDAO;
import model.interfaces.InterfaceUsuarioController;

/**
 * Classe UsuarioController
 * 
 * Classe responsável pelas validações e verificações das entradas e saídas
 * 
 * @author Sprint 3
 * @author David Willian, david.oliveira@senior.com.br
 * @author Leonardo Pereira, leonardo.pereira@senior.com.br
 * @author Vitor Peres, vitor.peres@senior.com.br
 * 
 * @author Sprint 4
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 */
public class UsuarioController implements InterfaceUsuarioController {

	public UsuarioDAO daoUsuario = new UsuarioDAO();
	



	

//	
//	/**
//	 * Método que altera a senha do usuario
//	 * 
//	 * Verifica se a nova senha atende os critérios de senha segura e se a nova senha é diferente da senha atual.
//	 * Recebe id e nova senha para alterar
//	 * 
//	 * @param int id do usuario procurado para alteraçao
//	 * @param String senhaNova do usuario
//	 */
//	public boolean alteraSenha(Integer usuarioID, String senhaNova) {
//
//		if (this.validarSenha(senhaNova) && !this.verificarHashSenha(usuarioID, senhaNova)) {
//
//			daoUsuario.buscarUsuario(usuarioID).setHashSenhaDoUsuario(this.converterSenhaEmHashSenha(senhaNova));
//
//			daoUsuario.atualizarUsuario(usuarioID, daoUsuario.buscarUsuario(usuarioID));
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * Método que altera o login do usuario
//	 * 
//	 * Recebe id e novo login para alterar
//	 * 
//	 * @param int id do usuario procurado para alteraçao
//	 * @param String loginNovo do usuario
//	 */
//	public boolean alteraLogin(Integer usuarioID, String loginNovo) {
//		
//		if(this.validarEmail(loginNovo)) {
//
//			if(!daoUsuario.buscarUsuario(usuarioID).getLoginDoUsuario().equals(loginNovo)){
//				
//				daoUsuario.buscarUsuario(usuarioID).setHashSenhaDoUsuario(loginNovo);
//				
//				daoUsuario.atualizarUsuario(usuarioID, daoUsuario.buscarUsuario(usuarioID));
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * Método que altera perfil do usuario.
	 * 
	 * Recebe id do usuario, e perfil novo para alterar
	 * 
	 * @param int id do usuario procurado para a troca de perfil
	 * @param Perfil perfilNovo do usuario que vai ter o perfil trocado.
	 */
	
//	@Deprecated
//	public void alteraPerfil(int id, ArrayList<PerfilModel> listaPerfil) {
//	
//		UsuarioModel usuarioEscolhido = daoUsuario.get(id);
//		usuarioEscolhido.setListaDePerfisDoUsuario(listaPerfil);
//		daoUsuario.update(usuarioEscolhido);
//	}
	
	/**
	 *  Cria um usuário.
	 *  
	 *  Recebe as informações de um usuário, transforma a senha em hash e cria um objeto UsuarioModel
	 *  @param idDoUsuario int
	 *  @param loginDoUsuario String
	 *  @param senha String
	 *  @return boolean
	 */
	public boolean criarUsuarioController(String loginDoUsuario, String senha) {
				
		for (Usuario usuarioModel : daoUsuario.buscarTodosUsuarios()) {
			if(usuarioModel.getLoginDoUsuario().equals(loginDoUsuario)) {
				return false;
			}
		} 
		if(validarSenha(senha)) {
			return daoUsuario.criarUsuario(this.converterSenhaEmHashSenha(senha), loginDoUsuario);
		} 
		return false;
	}
	
	/**
	 *  Deleta um usuário de acordo com o login informado  
	 *  
	 *  @param idDoUsuario int
	 *  @param loginDoUsuario String
	 *  @return boolean
	 */
	public boolean deletarUsuarioController(Integer idDoUsuario, String loginDoUsuario) {
		
		for (Usuario usuarioModel : daoUsuario.buscarTodosUsuarios()) {
			if (usuarioModel.getLoginDoUsuario().equals(loginDoUsuario)) {
				daoUsuario.deletarUsuario(idDoUsuario);
				return true;
			}
		}
		return false;
	}
	
	/**
	 *  Atualiza um usuário de acordo com o login informado  
	 *  
	 *  @param idDoUsuario int
	 *  @param loginDoUsuario String
	 *  @return boolean
	 */
	public boolean atualizaUsuarioController(Integer idDoUsuario, Usuario usuarioAtualizar) {
		
		if (daoUsuario.buscarUsuario(idDoUsuario) == null) {
			return false;
		} else {			
					
			return daoUsuario.atualizarUsuario(idDoUsuario, usuarioAtualizar);
		}

	}
	
	/**
	 *  Busca um usuário de acordo com o login informado  
	 *  
	 *  @param idDoUsuario int
	 *  @param loginDoUsuario String
	 *  @return UsuarioModel
	 */
	public Usuario buscarUsuarioController(Integer idDoUsuario) {
				return daoUsuario.buscarUsuario(idDoUsuario);
	}
		
	
	/**
	 *  Busca todos os usuarios cadastrados  
	 *  
	 *  @return ArrayList<UsuarioModel>
	 */
	public ArrayList<Usuario> buscarTodosUsuariosController() {
		return daoUsuario.buscarTodosUsuarios();
	}
	
	/**
	 *  Atualiza um usuário de acordo com o login informado  
	 *  
	 *  @param idDoUsuario int
	 *  @param loginDoUsuario String
	 *  @return boolean
	 */
//	public boolean atualizarUsuarioController(Integer idDoUsuario, String loginDoUsuario) {
//		
//		for (UsuarioModel usuarioModel : daoUsuario.buscarTodosUsuarios()) {
//			if (usuarioModel.getLoginDoUsuario().equals(loginDoUsuario)) {
//				daoUsuario.atualizarUsuario(idDoUsuario, usuarioModel);
//				return true;
//			}
//		}
//		return false;
//	}
	

	
}
