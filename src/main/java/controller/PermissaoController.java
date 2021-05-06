package controller;

import java.util.ArrayList;

import model.acesso.PermissaoDAO;
import model.acesso.Permissao;

/**
 * Classe PermissaoController
 * 
 * Classe responsavel pelas validacoes e verificacoes das entradas e saidas
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Vitor Nathan Goncalves, vitor.goncalves@senior.com.br
 * @author Elton Francisco de Oliveira, elton.oliveira@senior.com.br
 * @author Vitor Andre Gehrke, vitor.gehrke@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 */

public class PermissaoController {

	private PermissaoDAO dao = new PermissaoDAO();
	
	public PermissaoDAO getDao() {
		return dao;
	}
	
	public void setDao(PermissaoDAO dao) {
		this.dao = dao;
	}

	/**
	 * Metodo criarPermissaoController
	 * 
	 * Metodo responsavel pela criacao da permissao, verificando previamente se a mesma 
	 * ja existe na lista de permissoes cadastradas. 
	 * 
	 * @param idDaPermissao Integer
	 * @param nomeDaPermissao String
	 * @return boolean
	 */
	public boolean criarPermissaoController(String nomeDaPermissao) {
		ArrayList<Permissao> permissaoModelLista = dao.buscarTodasAsPermissoes();		
		for (Permissao permissaoModel : permissaoModelLista) {
			if(permissaoModel.getNomeDaPermissao().equals(nomeDaPermissao)) {
				return false;
			}
		} 
		dao.criarPermissao(nomeDaPermissao);
		return true;
	}
	
	/**
	 * Metodo deletarPermissaoController
	 * 
	 * Metodo realiza a exclusão da permissao conforme id informado
	 * 
	 * @param idDaPermissao Integer
	 * @return boolean
	 */
	public boolean deletarPermissaoController(Integer idDaPermissao) {
		return dao.deletarPermissao(idDaPermissao);
	}
	
	/**
	 * Metodo deletarPermissaoController
	 * 
	 * Metodo realiza a exclusao da permissao conforme nome informado
	 * 
	 * @param nomeDaPermissao String
	 * @return boolean
	 */
	public boolean deletarPermissaoController(String nomeDaPermissao) {
		return dao.deletarPermissao(nomeDaPermissao);
	}

	/**
	 * Metodo buscarTodasAsPermissoes
	 * 
	 * Metodo retorna a lista de permissoes criadas
	 * 
	 * @return ArrayList<PermissaoModel>
	 */
	public ArrayList<Permissao> buscarTodasAsPermissoes() {
		return dao.buscarTodasAsPermissoes();
	}
	
	/**
	 * Metodo buscarPermissao
	 * 
	 * Metodo retorna a permissao com o nome indicado
	 * @param nome String
	 * @return PermissaoModel
	 */
	public Permissao buscarPermissao(String nome) {
		return dao.buscarPermissao(nome);
	}
	
	/**
	 * Metodo buscarPermissao
	 * 
	 * Metodo retorna a permissao com o id indicado
	 * @param id Integer
	 * @return PermissaoModel
	 */
	public Permissao buscarPermissao(Integer id) {
		return dao.buscarPermissao(id);
	}
	
	/**
	 * Metodo atualizarPermissao
	 * 
	 * Atualiza uma permissao com base no id e na permissão atualizada.
	 * 
	 * @param id Integer
	 * @param novaPermissao PermissaoModel
	 * @return boolean
	 */
	public boolean atualizarPermissao(Integer id, Permissao novaPermissao) {
		return dao.atualizarPermissao(id, novaPermissao);
	}
}
