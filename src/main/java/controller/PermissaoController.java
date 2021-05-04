package controller;

import java.util.ArrayList;

import model.acesso.PermissaoDAO;
import model.acesso.PermissaoModel;

/**
 * Classe PermissaoController
 * 
 * Classe respons�vel pelas valida��es e verifica��es das entradas e sa�das
 * 
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 *
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
	 * M�todo criarPermissaoController
	 * 
	 * M�todo respons�vel pela cria��o da permiss�o, verificando previamente se a mesma 
	 * j� existe na lista de permiss�es cadastradas. 
	 * 
	 * @param idDaPermissao Integer
	 * @param nomeDaPermissao String
	 * @return boolean
	 */
	public boolean criarPermissaoController(String nomeDaPermissao) {
		ArrayList<PermissaoModel> permissaoModelLista = dao.buscarTodasAsPermissoes();		
		for (PermissaoModel permissaoModel : permissaoModelLista) {
			if(permissaoModel.getNomeDaPermissao().equals(nomeDaPermissao)) {
				return false;
			}
		} 
		dao.criarPermissao(nomeDaPermissao);
		return true;
	}
	
	/**
	 * M�todo deletarPermissaoController
	 * 
	 * M�todo realiza a exclus�o da permiss�o conforme id informado
	 * 
	 * @param idDaPermissao Integer
	 * @return void
	 */
	public boolean deletarPermissaoController(Integer idDaPermissao) {
		return dao.deletarPermissao(idDaPermissao);
	}
	
	/**
	 * M�todo deletarPermissaoController
	 * 
	 * M�todo realiza a exclus�o da permiss�o conforme nome informado
	 * 
	 * @param idDaPermissao Integer
	 * @return void
	 */
	public void deletarPermissaoController(String nomeDaPermissao) {
		dao.deletarPermissao(nomeDaPermissao);
	}

	/**
	 * M�todo buscarTodasAsPermissoes
	 * 
	 * M�todo retorna a lista de permiss�es criadas
	 * 
	 * @return ArrayList<PermissaoModel>
	 */
	public ArrayList<PermissaoModel> buscarTodasAsPermissoes() {
		return dao.buscarTodasAsPermissoes();
	}
	
	/**
	 * M�todo buscarPermissao
	 * 
	 * M�todo retorna a permissao com o nome indicado
	 * @param nome String
	 * @return PermissaoModel
	 */
	public PermissaoModel buscarPermissao(String nome) {
		return dao.buscarPermissao(nome);
	}
	
	/**
	 * M�todo buscarPermissao
	 * 
	 * M�todo retorna a permissao com o id indicado
	 * @param id Integer
	 * @return PermissaoModel
	 */
	public PermissaoModel buscarPermissao(Integer id) {
		return dao.buscarPermissao(id);
	}
	
	/**
	 * M�todo atualizarPermissao
	 * 
	 * Atualiza uma permiss�o com base no id e na permiss�o atualizada.
	 * 
	 * @param id Integer
	 * @param novaPermissao PermissaoModel
	 * @return boolean
	 */
	public boolean atualizarPermissao(Integer id, PermissaoModel novaPermissao) {
		return dao.atualizarPermissao(id, novaPermissao);
	}
}
