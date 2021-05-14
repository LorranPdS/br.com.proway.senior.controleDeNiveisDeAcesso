package model.acesso;

import java.util.ArrayList;

import org.hibernate.Session;

import db.DBConnection;
import model.interfaces.ICrud;

/**
 * Classe PerfilDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * perfis
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

public class PerfilDAO implements ICrud<Perfil> {

	private static PerfilDAO instance;
	private Session session;

	private PerfilDAO(Session session) {
		this.session = session;
	}

	public static PerfilDAO getInstance() {
		if (instance == null) {
			instance = new PerfilDAO(DBConnection.getSession());
		}
		return instance;
	}

	public void criar(Perfil object) {
		// TODO Auto-generated method stub

	}

	public boolean alterar(Perfil object) {
		return false;
	}

	public boolean deletar(Perfil object) {
		return false;
	}

	public Perfil consultarPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Perfil> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Perfil consultarPorNome(String nome) {
		// TODO
		return null;
	}
	
	public ArrayList<Permissao> listarPermissoes(int idPerfil){
		return null;
	}
	
	public void atribuirPermissaoAUmPerfil(Permissao permissao, Perfil perfil) {

	}

}
