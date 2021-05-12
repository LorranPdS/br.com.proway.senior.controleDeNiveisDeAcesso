package model.acesso;

import java.util.ArrayList;

import org.hibernate.Session;

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

	private static UsuarioDAO instance;
	private Session session;

	private UsuarioDAO(Session session) {
		this.session = session;
	}

	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO(DBConnection.getSession());
		}
		return instance;
	}

//	public Usuario getByIndex(Integer id) {
//		Player p = null;
//		try {
//			session.beginTransaction();
//			p = session.find(Player.class, id);
//			session.getTransaction().commit();
//			return p;
//		} catch (Exception e) {
//			session.getTransaction().rollback();
//			e.printStackTrace();
//		}
//		return p;
//	}

	public void criar(Usuario object) {
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public boolean alterar(Usuario object) {
		return false;

	}

	public boolean deletar(int id) {
		return false;
	}

	public Usuario consultarPorId(int id) {
		try {
			session.beginTransaction();
			Usuario u = session.find(Usuario.class, id);
			session.getTransaction().commit();
			return u;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Usuario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Usuario consultarPorLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Perfil> listarPerfis(int idUsuario) {
		return null;
	}

	public ArrayList<Permissao> listarPermissoes(int idUsuario) {
		return null;
	}

	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil) {

	}
}
