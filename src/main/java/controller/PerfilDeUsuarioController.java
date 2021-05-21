package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.dao.PerfilDeUsuarioDAO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * Classe que faz contato com o {@link PerfilDeUsuarioDAO}.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6 *
 */
public class PerfilDeUsuarioController {
	
	PerfilDeUsuarioDAO ligacaoDAO = PerfilDeUsuarioDAO.getInstance();
	
	/**
	 * Atribui um {@link Perfil} a um {@link Usuario}.
	 * 
	 * <p>
	 * Metodo responsavel por atribuir um {@link Perfil} a um {@link Usuario}.
	 * 
	 * @param usuario Usuario {@link Usuario} que recebera um perfil de acesso.
	 * @param perfil Perfil {@link Perfil} que sera atribuido a um usuario.
	 * @throws Exception - Caso a atribuição do {@link Perfil} ao {@link Usuario}
	 *                   não seja possivel.
	 */
	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil, LocalDate dataExpiracao){
		PerfilDeUsuario perfilDeUsuario = new PerfilDeUsuario(usuario, perfil, dataExpiracao);
		ligacaoDAO.criar(perfilDeUsuario);
	}
	
	/**
	 * Consulta um {@link PerfilDeUsuario} pelo valor da coluna 'usuario_id'.
	 * 
	 * <p>
	 * Retorna uma lista do tipo {@link PerfilDeUsuario} que possui o 'usuario_id'
	 * igual 'id' recebi do parametro.
	 * 
	 * @param id Integer Id do objeto a ser consultado.
	 * @return ArrayList Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	public ArrayList<PerfilDeUsuario> consultarPorIdDoUsuario(Integer id) {
		return ligacaoDAO.consultarPorIdDoUsuario(id);
	}
	
	/**
	 * Consulta um {@link PerfilDeUsuario} pelo valor da coluna 'perfil_id'.
	 * 
	 * <p>
	 * Retorna uma lista do tipo {@link PerfilDeUsuario} que possui o 'perfil_id'
	 * igual 'id' recebi do parametro.
	 * 
	 * @param id Integer Id do objeto a ser consultado.
	 * @return ArrayList Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	public ArrayList<PerfilDeUsuario> consultarPorIdDoPerfil(Integer id) {
		return ligacaoDAO.consultarPorIdDoPerfil(id);
	}
	
	/**
	 * Retorna todas as {@link Permissao} de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado. Percorre todos os
	 * {@link Perfil} do {@link Usuario}, pega todas as {@link Permissao} de cada
	 * {@link Perfil} e adiciona em uma lista.
	 * 
	 * <p>
	 * Observacao: as {@link Permissao} nao se repetem na lista.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todas as {@link Permissao} do {@link Usuario}.
	 */
	public Set<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
	return ligacaoDAO.listarPermissoesDeUmUsuario(idUsuario);
	}
	
	/**
	 * Retorna todas as {@link Perfil} de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario}.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		return ligacaoDAO.listarPerfisDeUmUsuario(idUsuario);
	}
	
	/**
	 * Deleta um registro da tabela {@link PerfilDeUsuario} que corresponde ao
	 * 'objeto' recebido no parametro.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser deletado.
	 */
	public boolean deletar(PerfilDeUsuario objeto) {
		return ligacaoDAO.deletar(objeto);
	}
	
	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		ligacaoDAO.deletarTodos();
	}
	
	/**
	 * 
	 * Altera um PerfilDeUsuario no banco de dados.
	 * 
	 * Recebe um objeto do tipo {@link PerfilDeUsuario} no parametro e atualiza o
	 * registro correspondente que está no banco de dados.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser atualizado no banco de dados.
	 * @boolean Retorna true.
	 */
	public boolean alterar(PerfilDeUsuario objeto) {
		return ligacaoDAO.alterar(objeto);
	}
	
	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	public PerfilDeUsuario consultarPorId(int id) {
	return ligacaoDAO.consultarPorId(id);
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	public List<PerfilDeUsuario> listar() {
		return ligacaoDAO.listar();
	}
}
