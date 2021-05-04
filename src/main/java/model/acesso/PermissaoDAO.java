package model.acesso;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBConnection;
import model.interfaces.InterfacePermissaoDAO;

/**
 * Classe PermissaoDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de permiss�es
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Gon�alves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */

public class PermissaoDAO implements InterfacePermissaoDAO {

	public DBConnection db;

	/**
	 * M�todo para conectar com o DB
	 */
	public PermissaoDAO() {
		db = DBConnection.getInstance();
	}

	/**
	 * M�todo criarPermissao
	 * 
	 * M�todo respons�vel por inserir uma permissao no banco de dados conforme
	 * atributos associados
	 * 
	 * @param nomeDaPermissao String
	 * @return boolean
	 * 
	 */
	public boolean criarPermissao(String nomeDaPermissao) {
		String insertPermissao = "INSERT INTO permissoesTabela(nome) values('" + nomeDaPermissao + "');";

		try {
			db.executeUpdate(insertPermissao);
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * M�todo deletarPermissao
	 * 
	 * M�todo respons�vel por deletar uma permissao existente no banco de dados a
	 * partir do id informado
	 * 
	 * @param idDaPermissao Integer
	 * @return boolean
	 * 
	 */
	public boolean deletarPermissao(Integer idDaPermissao) {
		String deletarPermissao = "DELETE from permissoesTabela where idPermissao=" + idDaPermissao + ";";
		
		try {
			db.executeUpdate(deletarPermissao);
			return true;
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * M�todo deletarPermissao
	 * 
	 * M�todo respons�vel por deletar uma permissao existente no banco de dados a partir do
	 * nome da permissao
	 * 
	 * @param idDaPermissao Integer
	 * @return boolean
	 * 
	 */
	public boolean deletarPermissao(String nome){
		String deletarPermissao = "DELETE from permissoesTabela where nome='"+ nome +"';";
		try {
			db.executeUpdate(deletarPermissao);
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * M�todo buscarPermissao
	 * 
	 * M�todo respons�vel por buscar, atrav�s do id, uma permiss�o dentro do banco
	 * de dados. Se a permissao existe, retorna a mesma. Se n�o, retorna nulo.
	 * 
	 * @param idDaPermissao Integer
	 * @return PermissaoModel
	 * 
	 */
	public PermissaoModel buscarPermissao(Integer idDaPermissao) {
		ArrayList<String> resultado = new ArrayList<String>();
		String selecionarPermissao = "SELECT * from permissoesTabela where idPermissao=" + idDaPermissao + ";";
		
		try {
			ResultSet rs = db.executeQuery(selecionarPermissao);
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalColunas = rsmd.getColumnCount();
			
			if (rs.next()) {
				for (int i = 1; i <= totalColunas; i++) {
					resultado.add(rs.getString(i));
				}
			}
			return new PermissaoModel(Integer.parseInt(resultado.get(0)), resultado.get(1));
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * M�todo buscarPermissao
	 * 
	 * M�todo respons�vel por buscar, atrav�s do nome, uma permiss�o dentro do banco de dados. 
	 * Se a permissao existe, retorna a mesma. Se n�o, retorna nulo.
	 * 
	 * @param nomeDaPermissao String
	 * @return PermissaoModel
	 */
	public PermissaoModel buscarPermissao(String nomeDaPermissao) {
		ArrayList<String> resultado = new ArrayList<String>();
		String selecionarPermissao = "SELECT * from permissoesTabela where nome='"+ nomeDaPermissao+"';";
		try {
			ResultSet rs = db.executeQuery(selecionarPermissao);
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalColunas = rsmd.getColumnCount();
			if(rs.next()) {
				for (int i = 1; i <= totalColunas; i ++) {
					resultado.add(rs.getString(i));
				}
			}
			if(!resultado.isEmpty()) {
				return new PermissaoModel(Integer.parseInt(resultado.get(0)), resultado.get(1));
			} else {
				return null;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * M�todo buscarTodasAsPermiss�es.
	 * 
	 * M�todo respons�vel por buscar todas as permiss�es dentro do banco de dados. 
	 * Se as permissoes existem, retorna uma lista. Se n�o, retorna nulo.
	 * 
	 * @param idDaPermissao Integer
	 * @return ArrayList<PermissaoModel>
	 * 
	 */
	public ArrayList<PermissaoModel> buscarTodasAsPermissoes() {
		ArrayList<PermissaoModel> resultado = new ArrayList<PermissaoModel>();
		String selecionarPermissao = "SELECT * from permissoesTabela;";
		
		try {
			ResultSet rs = db.executeQuery(selecionarPermissao);
			ResultSetMetaData rsmd = rs.getMetaData();
			int totalColunas = rsmd.getColumnCount();
			
			while (rs.next()) {
				ArrayList<String> linha = new ArrayList<String>();
				
				for (int i = 1; i <= totalColunas; i++) {
					linha.add(rs.getString(i));
				}
				
				PermissaoModel pm = new PermissaoModel(Integer.parseInt(linha.get(0)), linha.get(1));
				resultado.add(pm);
			}
			return resultado;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * M�todo atualizarPermissao.
	 * 
	 * Atualiza uma permissao no banco de dados.
	 * 
	 * @param idPermissao Integer
	 * @param novaPermissao PermissaoModel
	 * @return boolean
	 * 
	 */
	public boolean atualizarPermissao(Integer idPermissao, PermissaoModel novaPermissao) {
		String atualizar = "UPDATE permissoesTabela set nome='" + novaPermissao.getNomeDaPermissao()
				+ "' where idPermissao =" + idPermissao + ";";
		try {
			db.executeUpdate(atualizar);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
