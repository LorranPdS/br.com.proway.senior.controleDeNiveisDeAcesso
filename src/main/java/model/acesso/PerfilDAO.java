package model.acesso;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import controller.ConverterDataDB;
import db.DBConnection;
import model.interfaces.InterfacePerfilDAO;

/**
 * Classe PerfilDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de perfis
 * 
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 */

public class PerfilDAO implements InterfacePerfilDAO{
	
	public DBConnection db;
	
	private ArrayList<PerfilModel> listaDePerfisCriados = new ArrayList<PerfilModel>();
	
	public PerfilDAO() {
		db = DBConnection.getInstance();
	}
	
	/**
	 * M�todo criarPerfil
	 * 
	 * M�todo respons�vel por criar um perfil vazio no banco de dados conforme
	 * atribuitos associadados
	 * 
	 * @param idDoPerfil Integer
	 * @param nomeDoPerfil String
	 * @return PerfilModel 
	 * 
	 */
	public boolean criarPerfilVazio(String nomeDoPerfil) {
		String insertPerfil = "INSERT INTO perfilTabela(nomePerfil, dataInicio, dataFim, status) "
				+ "values('" + nomeDoPerfil +"', '" + 
				LocalDate.of(1970, 1, 1).toString() + "', '" + 
				LocalDate.of(2200, 1, 1).toString() + "', " + 
				true + ");";
		
		try {
			db.executeUpdate(insertPerfil);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**
	 * M�todo deletarPerfil
	 * 
	 * M�todo respons�vel por deletar um perfil existente no banco de dados a partir do
	 * id informado
	 * 
	 * @param idDoPerfil Integer
	 * @return boolean
	 * 
	 */
	public boolean deletarPerfil(Integer idDoPerfil) {
		String deletarPerfil = "DELETE from perfilTabela where idPerfil="+ idDoPerfil+";";
		try {
			db.executeUpdate(deletarPerfil);
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * M�todo atualizar
	 * 
	 * M�todo respons�vel por atualizar um perfil existente no banco de dados a partir do
	 * id informado
	 * 
	 * @param idDoPerfil Integer
	 * @return boolean
	 * 
	 */
	public boolean atualizarPerfil(Integer idDoPerfil, PerfilModel perfil) {
		String deletarPerfil = "UPDATE perfilTabela set nomeperfil= '" + perfil.getNomeDoPerfil()+ "', dataInicio='"
				+ perfil.getInicioValidadePerfil().toString() + "', dataFim= '" + 
				perfil.getFimValidadePerfil() + "', status= "+ perfil.isPerfilAtivo()+" where idPerfil="+ idDoPerfil+";";
		try {
			db.executeUpdate(deletarPerfil);
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * M�todo buscarPerfil
	 * 
	 * M�todo respons�vel por buscar, atrav�s do id, um perfil dentro de uma
	 * lista de Perfis. Se o perfil existe, retorna o mesmo. Se n�o, retorna
	 * nulo.
	 * 
	 * @param idDoPerfil Integer
	 * @return PerfilModel
	 */
	public PerfilModel buscarPerfil(Integer idDoPerfil) {
		for (PerfilModel perfilModel : listaDePerfisCriados) {
			if (perfilModel.getIdDoPerfil() == idDoPerfil) {
				return perfilModel;
			}
		}
		return null;
	}

	/**
	 * M�todo lerListaDePerfisCriados
	 * 
	 * Retorna a lista de perfis criados
	 *  
	 * @return ArrayList<PerfilModel>
	 */
	public ArrayList<PerfilModel> lerListaDePerfisCriados() {
		return listaDePerfisCriados;
	}
	
	/**
	 * M�todo alterarPerfil
	 * 
	 * M�todo procura um perfil, com base no seu id, e altera o seu nome
	 * 
	 * @param idDoPerfil Integer
	 * @param novoNomeDoPerfil String
	 * @return PerfilModel
	 */
	public PerfilModel alterarNomePerfil(Integer idDoPerfil, String novoNomeDoPerfil) {
		PerfilModel perfilAlterado = this.buscarPerfil(idDoPerfil);

		if (perfilAlterado != null) {

			perfilAlterado.setNomeDoPerfil(novoNomeDoPerfil);
			return perfilAlterado;
		}
		return null;
	}
	
	/**
	 * M�todo alterarInicioValidadePerfil.
	 * 
	 * M�todo procura um perfil, com base no seu id, e altera a sua data de in�cio de validade.
	 *
	 * 
	 * @param idDoPerfil Integer
	 * @param novoInicioValidadePerfil LocalDate
	 * @return PerfilModel
	 */
	public PerfilModel alterarInicioValidadePerfil(Integer idDoPerfil, LocalDate novoInicioValidadePerfil) {
		PerfilModel perfilAlterado = this.buscarPerfil(idDoPerfil);
		
		if(perfilAlterado != null) {
			perfilAlterado.setInicioValidadePerfil(novoInicioValidadePerfil);
			return perfilAlterado;
		}
		return null;	
	}
	
	/**
	 * M�todo alterarFimValidadePerfil.
	 * 
	 * M�todo procura um perfil, com base no seu id, e altera a sua data de fim de validade.
	 *
	 * 
	 * @param idDoPerfil Integer
	 * @param novoFimValidadePerfil LocalDate
	 * @return PerfilModel
	 */
	public PerfilModel alterarFimValidadePerfil(Integer idDoPerfil, LocalDate novoFimValidadePerfil) {
		PerfilModel perfilAlterado = this.buscarPerfil(idDoPerfil);
		
		if(perfilAlterado != null) {
			perfilAlterado.setFimValidadePerfil(novoFimValidadePerfil);
			return perfilAlterado;
		}
		return null;	
	}
	
	/**
	 * M�todo alterarPerfilAtivo.
	 * 
	 * M�todo procura um perfil, com base no seu id, e altera o seu estado de ativo
	 *
	 * 
	 * @param idDoPerfil Integer
	 * @param novoEstadoAtivo boolean
	 * @return PerfilModel
	 */
	public PerfilModel alterarPerfilAtivo(Integer idDoPerfil, boolean novoEstadoAtivo) {
		PerfilModel perfilAlterado = this.buscarPerfil(idDoPerfil);
		
		if(perfilAlterado != null) {
			perfilAlterado.setPerfilAtivo(novoEstadoAtivo);
			return perfilAlterado;
		}
		return null;	
	}
	
}
