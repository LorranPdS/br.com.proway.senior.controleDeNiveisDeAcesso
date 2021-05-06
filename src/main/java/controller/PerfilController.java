package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import model.acesso.PerfilDAO;
import model.acesso.Perfil;
import model.acesso.PermissaoDAO;
import model.acesso.Permissao;

/**
 * Classe PerfilController
 * 
 * Classe responsavel pelas validações e verificacoes das entradas e saidas
 * 
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Vitor Nathan Goncalves, vitor.goncalves@senior.com.br
 * @author Elton Francisco de Oliveira, elton.oliveira@senior.com.br
 * @author Vitor Andre Gehrke, vitor.gehrke@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
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
	 * Metodo criarPerfilVazioController
	 * 
	 * Metodo responsavel pela criacao do perfil vazio, verificando previamente se o
	 * mesmo ja existe na lista de perfis cadastrados.
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
	 * Método criarPerfilVazioTemporarioController
	 * 
	 * Método responsavel pela criação do perfil vazio e temporario, verificando previamente se o
	 * mesmo ja existe na lista de perfis cadastrados.
	 * 
	 * @param idDoPerfil   Integer
	 * @param nomeDoPerfil String
	 * @return boolean
	 */
	public boolean criarPerfilVazioTemporarioController(Perfil perfilModel) {

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
	 * Metodo deletarPerfilController
	 * 
	 * Metodo realiza a exclusao do perfil conforme id informado
	 * 
	 * @param idDoPerfil Integer
	 * @return boolean
	 */
	public boolean deletarPerfilController(Integer idDoPerfil) {
		return dao.deletarPerfil(idDoPerfil);
	}
	
	/**
	 * Metodo deletarPerfilController
	 * 
	 * Metodo realiza a exclusao do perfil conforme nome Informado
	 * 
	 * @param nomeDoPerfil String
	 * @return void
	 */
	public boolean deletarPerfilController(String nomeDoPerfil) {
		return dao.deletarPerfil(nomeDoPerfil);
	}
	
	/**
	 * Metodo buscarPerfil
	 * 
	 * Metodo retorna o perfil que possui o id especificado
	 * 
	 * @param id Integer
	 * @return PerfilModel
	 */
	public Perfil buscarPerfil(Integer id) {
		return dao.buscarPerfil(id);
	}
	
	/**
	 * Metodo buscarPerfil
	 * 
	 * Metodo retorna o perfil que possui o nome especificado
	 * 
	 * @param nomeDoPerfil String
	 * @return PerfilModel
	 */
	public Perfil buscarPerfil(String nomeDoPerfil) {
		return dao.buscarPerfil(nomeDoPerfil);
	}

	/**
	 * Metodo buscarTodosOsPerfis
	 * 
	 * Metodo retorna a lista de perfis criados no banco de dados
	 * 
	 * @return ArrayList<PerfilModel>
	 */
	public ArrayList<Perfil> buscarTodosOsPerfis() {
		return dao.buscarTodosOsPerfis();
	}

	/**
	 * Metodo alterarNomePerfilController
	 * 
	 * Metodo realiza a alteração do nome de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novoNomePerfil String
	 * @return boolean
	 */
	public boolean alterarNomePerfilController(Integer idDoPerfil, String novoNomePerfil) {
		Perfil perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setNomeDoPerfil(novoNomePerfil);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
	}

	/**
	 * Metodo alterarDataInicioPerfilController
	 * 
	 * Metodo realiza a alteracao da data de inicio de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataInicio LocalDate
	 * @return boolean
	 */
	public boolean alterarDataInicioPerfilController(Integer idDoPerfil, LocalDate novaDataInicio) {
		Perfil perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setInicioValidadePerfil(novaDataInicio);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
		
	}
	/**
	 * Metodo alterarDataFimPerfilController
	 * 
	 * Metodo realiza a alteração da data fim de validade de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataFim LocalDate
	 * @return boolean
	 */
	public boolean alterarDataFimPerfilController(Integer idDoPerfil, LocalDate novaDataFim) {
		Perfil perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setFimValidadePerfil(novaDataFim);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
		
	}
	/**
	 * Metodo alterarStatusPerfilController
	 * 
	 * Metodo realiza a alteracao do status de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param statusPerfil boolean
	 * @return boolean
	 */
	public boolean alterarStatusPerfilController(Integer idDoPerfil, boolean statusPerfil) {
		Perfil perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			perfilModel.setPerfilAtivo(statusPerfil);
			return dao.atualizarPerfil(idDoPerfil, perfilModel);
		} 
		return false;
	}
	
	/**
	 * Metodo atualizarPerfilController
	 * 
	 * Metodo realiza a atualizacao completa de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novoPerfil PerfilModel
	 * @return boolean
	 */
	public boolean atualizarPerfilController(Integer idDoPerfil, Perfil novoPerfil) {
		Perfil perfilModel = this.buscarPerfil(idDoPerfil);
		if(!(perfilModel == null)) {
			return dao.atualizarPerfil(idDoPerfil, novoPerfil);
		} 
		return false;
	}	
}
