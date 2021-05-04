package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.acesso.PerfilDAO;
import model.acesso.PerfilModel;
import model.acesso.PermissaoDAO;
import model.acesso.PermissaoModel;

/**
 * Classe PerfilController
 * 
 * Classe respons�vel pelas valida��es e verifica��es das entradas e sa�das
 * 
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */

public class PerfilController {

	private PerfilDAO dao = new PerfilDAO();
	
	public PerfilDAO getDao() {
		return dao;
	}

	public void setDao(PerfilDAO dao) {
		this.dao = dao;
	}

	/**
	 * M�todo criarPerfilVazioController
	 * 
	 * M�todo respons�vel pela cria��o do perfil vazio, verificando previamente se o
	 * mesmo j� existe na lista de perfis cadastrados.
	 * 
	 * @param idDoPerfil   Integer
	 * @param nomeDoPerfil String
	 * @return boolean
	 */
	public boolean criarPerfilVazioController(String nomeDoPerfil) {

		if(dao.buscarTodosOsPerfis() == null || dao.buscarTodosOsPerfis().size() == 0) {
			dao.criarPerfilVazio(nomeDoPerfil);
			return true;
		} else {
			if (dao.buscarPerfil(nomeDoPerfil) == null) {
				dao.criarPerfilVazio(nomeDoPerfil);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * M�todo criarPerfilVazioController
	 * 
	 * M�todo respons�vel pela cria��o do perfil vazio, verificando previamente se o
	 * mesmo j� existe na lista de perfis cadastrados.
	 * 
	 * @param idDoPerfil   Integer
	 * @param nomeDoPerfil String
	 * @return boolean
	 */
	public boolean criarPerfilVazioTemporarioController(PerfilModel perfilModel) {

		if(dao.buscarTodosOsPerfis() == null || dao.buscarTodosOsPerfis().size() == 0) {
			dao.criarPerfilVazioTemporario(perfilModel);
			return true;
		} else {
			if (dao.buscarPerfil(perfilModel.getNomeDoPerfil()) == null) {
				dao.criarPerfilVazioTemporario(perfilModel);
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * M�todo deletarPerfilController
	 * 
	 * M�todo realiza a exclus�o do perfil conforme id informado
	 * 
	 * @param idDoPerfil Integer
	 * @return boolean
	 */
	public boolean deletarPerfilController(Integer idDoPerfil) {
		return dao.deletarPerfil(idDoPerfil);
	}
	
	/**
	 * M�todo deletarPerfilController
	 * 
	 * M�todo realiza a exclus�o do perfil conforme nomeInformado
	 * 
	 * @param idDoPerfil Integer
	 * @return void
	 */
	public boolean deletarPerfilController(String nomeDoPerfil) {
		return dao.deletarPerfil(nomeDoPerfil);
	}
	
	/**
	 * M�todo buscarPerfil
	 * 
	 * M�todo retorna o perfil que possui o id especificado
	 * 
	 * @param id Integer
	 * @return PerfilModel
	 */
	public PerfilModel buscarPerfil(Integer id) {
		return dao.buscarPerfil(id);
	}
	
	/**
	 * M�todo buscarPerfil
	 * 
	 * M�todo retorna o perfil que possui o nome especificado
	 * 
	 * @param nomeDoPerfil String
	 * @return PerfilModel
	 */
	public PerfilModel buscarPerfil(String nomeDoPerfil) {
		return dao.buscarPerfil(nomeDoPerfil);
	}

	/**
	 * M�todo buscarTodosOsPerfis
	 * 
	 * M�todo retorna a lista de perfis criados
	 * 
	 * @return ArrayList<PerfilModel>
	 */
	public ArrayList<PerfilModel> buscarTodosOsPerfis() {
		return dao.buscarTodosOsPerfis();
	}

	/**
	 * M�todo alterarNomePerfilController
	 * 
	 * M�todo realiza a altera��o do nome de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novoNomePerfil String
	 * @return boolean
	 */
	public boolean alterarNomePerfilController(Integer idDoPerfil, String novoNomePerfil) {
		PerfilModel perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setNomeDoPerfil(novoNomePerfil);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
	}

	/**
	 * M�todo alterarDataInicioPerfilController
	 * 
	 * M�todo realiza a altera��o da data de inicio de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataInicio LocalDate
	 * @return boolean
	 */
	public boolean alterarDataInicioPerfilController(Integer idDoPerfil, LocalDate novaDataInicio) {
		PerfilModel perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setInicioValidadePerfil(novaDataInicio);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
		
	}
	/**
	 * M�todo alterarDataFimPerfilController
	 * 
	 * M�todo realiza a altera��o da data fim de validade de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataFim LocalDate
	 * @return boolean
	 */
	public boolean alterarDataFimPerfilController(Integer idDoPerfil, LocalDate novaDataFim) {
		PerfilModel perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setFimValidadePerfil(novaDataFim);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
		
	}
	/**
	 * M�todo alterarStatusPerfilController
	 * 
	 * M�todo realiza a altera��o do status de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param statusPerfil boolean
	 * @return boolean
	 */
	public boolean alterarStatusPerfilController(Integer idDoPerfil, boolean statusPerfil) {
		PerfilModel perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setPerfilAtivo(statusPerfil);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
	}
	
	/**
	 * M�todo alterarStatusPerfilController
	 * 
	 * M�todo realiza a altera��o do status de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param statusPerfil boolean
	 * @return boolean
	 */
	public boolean atualizarPerfilController(Integer idDoPerfil, PerfilModel novoPerfil) {
		PerfilModel perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			return dao.atualizarPerfil(idDoPerfil, novoPerfil);
		} 
		return false;
	}
	
//	/**
//	 * M�todo adicionarPermissaoEmUmPerfil
//	 * 
//	 * M�todo adiciona uma permiss�o a um perfil, com base nos seus respectivos id's
//	 * 
//	 * @param idDoPerfil
//	 * @param idDaPermissao
//	 * @return void
//	 */
//	public void adicionarPermissaoEmUmPerfil(Integer idDoPerfil, Integer idDaPermissao) {
//		PermissaoDAO permissaoDAO = new PermissaoDAO();
//
//		ArrayList<PermissaoModel> listaDePermissoesDoPerfil = dao.buscarPerfil(idDoPerfil)
//				.getListaDePermissoesDoPerfil();
//
//		listaDePermissoesDoPerfil.add(permissaoDAO.buscarPermissao(idDaPermissao));
//	}
//
//	
//	/**
//	 * M�todo deletarPermissaoEmUmPerfil
//	 * 
//	 * M�todo remove uma permiss�o de um perfil, com base nos seus respectivos id's
//	 * 
//	 * @param idDoPerfil
//	 * @param idDaPermissao
//	 * @return void
//	 */
//	public void deletarPermissaoEmUmPerfil(Integer idDoPerfil, Integer idDaPermissao) {
//		PermissaoDAO permissaoDAO = new PermissaoDAO();
//
//		ArrayList<PermissaoModel> listaDePermissoesDoPerfil = dao.buscarPerfil(idDoPerfil)
//				.getListaDePermissoesDoPerfil();
//
//		listaDePermissoesDoPerfil.remove(permissaoDAO.buscarPermissao(idDaPermissao));
//	}
//
//	/**
//	 * M�todo listarPermissoesDeUmPerfil
//	 * 
//	 * M�todo retorna uma lista de permiss�es atribu�das a um perfil
//	 * 
//	 * @param idDoPerfil
//	 * @return ArrayList<PermissaoModel>
//	 */
//	public ArrayList<PermissaoModel> listarPermissoesDeUmPerfil(Integer idDoPerfil) {
//		return dao.buscarPerfil(idDoPerfil).getListaDePermissoesDoPerfil();
//	}
	
}
