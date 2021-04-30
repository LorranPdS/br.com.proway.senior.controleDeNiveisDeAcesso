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
	public boolean criarPerfilVazioController(Integer idDoPerfil, String nomeDoPerfil) {

		if (dao.lerListaDePerfisCriados().size() == 0) {
			dao.criarPerfilVazio(idDoPerfil, nomeDoPerfil);
			return true;
		} else {
			if (dao.buscarPerfil(idDoPerfil) == null) {
				dao.criarPerfilVazio(idDoPerfil, nomeDoPerfil);
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
	 * @return void
	 */
	public void deletarPerfilController(Integer idDoPerfil) {
		dao.deletarPerfil(idDoPerfil);
	}

	/**
	 * M�todo lerListaDePerfisCriados
	 * 
	 * M�todo retorna a lista de perfis criados
	 * 
	 * @return ArrayList<PerfilModel>
	 */
	public ArrayList<PerfilModel> lerListaDePerfisCriados() {
		return dao.lerListaDePerfisCriados();
	}

	/**
	 * M�todo alterarNomePerfilController
	 * 
	 * M�todo realiza a altera��o do nome de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novoNomePerfil String
	 * @return void
	 */
	public void alterarNomePerfilController(Integer idDoPerfil, String novoNomePerfil) {
		dao.alterarNomePerfil(idDoPerfil, novoNomePerfil);
	}

	/**
	 * M�todo alterarFimValidadePerfilController
	 * 
	 * M�todo realiza a altera��o da data de fim da validade de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataFimValidade LocalDate
	 * @return void
	 */
	public void alterarFimValidadePerfilController(Integer idDoPerfil, LocalDate novaDataFimValidade) {
		dao.alterarFimValidadePerfil(idDoPerfil, novaDataFimValidade);
	}
	
	/**
	 * M�todo alterarPerfilAtivoController
	 * 
	 * M�todo realiza a altera��o do estado de atividade de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novoEstado boolean
	 * @return void
	 */
	public void alterarPerfilAtivoController(Integer idDoPerfil, boolean novoEstado) {
		dao.alterarPerfilAtivo(idDoPerfil, novoEstado);
	}
	
	/**
	 * M�todo alterarInicioValidadePerfilController
	 * 
	 * M�todo realiza a altera��o da data de inicio da validade de um perfil conforme id informado
	 * 
	 * @param idDoPerfil     Integer
	 * @param novaDataInicioValidade LocalDate
	 * @return void
	 */
	public void alterarInicioValidadePerfilController(Integer idDoPerfil, LocalDate novaDataInicioValidade) {
		dao.alterarInicioValidadePerfil(idDoPerfil, novaDataInicioValidade);
	}
	
	/**
	 * M�todo adicionarPermissaoEmUmPerfil
	 * 
	 * M�todo adiciona uma permiss�o a um perfil, com base nos seus respectivos id's
	 * 
	 * @param idDoPerfil
	 * @param idDaPermissao
	 * @return void
	 */
	public void adicionarPermissaoEmUmPerfil(Integer idDoPerfil, Integer idDaPermissao) {
		PermissaoDAO permissaoDAO = new PermissaoDAO();

		ArrayList<PermissaoModel> listaDePermissoesDoPerfil = dao.buscarPerfil(idDoPerfil)
				.getListaDePermissoesDoPerfil();

		listaDePermissoesDoPerfil.add(permissaoDAO.buscarPermissao(idDaPermissao));
	}

	/**
	 * M�todo deletarPermissaoEmUmPerfil
	 * 
	 * M�todo remove uma permiss�o de um perfil, com base nos seus respectivos id's
	 * 
	 * @param idDoPerfil
	 * @param idDaPermissao
	 * @return void
	 */
	public void deletarPermissaoEmUmPerfil(Integer idDoPerfil, Integer idDaPermissao) {
		PermissaoDAO permissaoDAO = new PermissaoDAO();

		ArrayList<PermissaoModel> listaDePermissoesDoPerfil = dao.buscarPerfil(idDoPerfil)
				.getListaDePermissoesDoPerfil();

		listaDePermissoesDoPerfil.remove(permissaoDAO.buscarPermissao(idDaPermissao));
	}

	/**
	 * M�todo listarPermissoesDeUmPerfil
	 * 
	 * M�todo retorna uma lista de permiss�es atribu�das a um perfil
	 * 
	 * @param idDoPerfil
	 * @return ArrayList<PermissaoModel>
	 */
	public ArrayList<PermissaoModel> listarPermissoesDeUmPerfil(Integer idDoPerfil) {
		return dao.buscarPerfil(idDoPerfil).getListaDePermissoesDoPerfil();
	}
}
