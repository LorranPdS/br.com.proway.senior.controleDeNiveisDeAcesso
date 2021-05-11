package model.acesso;

import java.util.ArrayList;

import db.DBConnection;
import model.interfaces.ICrud;

/**
 * Classe UsuarioDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * usuarios.
 * 
 * @author Sprint 3
 * @author David Willian, david.oliveira@senior.com.br
 * @author Leonardo Pereira, leonardo.pereira@senior.com.br
 * @author Vitor Peres, vitor.peres@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */

public class UsuarioDAO implements ICrud<Usuario> {

	public DBConnection db;
	private static UsuarioDAO instance;

	private UsuarioDAO() {
		db = DBConnection.getInstance();
	}
	
	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}
	
	public void criar(Usuario object) {
		// TODO Auto-generated method stub

	}

	public void alterar(Usuario object) {
		// TODO Auto-generated method stub

	}

	public void deletar(int id) {
		// TODO Auto-generated method stub

	}

	public Usuario consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Usuario consultarPorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}
}
