package model.acesso;

import java.util.ArrayList;

import db.DBConnection;
import model.interfaces.ICrud;

/**
 * Classe PermissaoDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * permissoes
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */

public class PermissaoDAO implements ICrud<Permissao> {

	public DBConnection db;

	/**
	 * Metodo para conectar com o DB
	 */
	public PermissaoDAO() {
		db = DBConnection.getInstance();
	}

	public void criar(Permissao object) {
		// TODO Auto-generated method stub

	}

	public boolean alterar(Permissao object) {
		return false;

	}

	public boolean deletar(int id) {
		return false;

	}

	public Permissao consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Permissao> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Permissao consultarPorNome(String nome) {
		// TODO
		return null;
	}
}
