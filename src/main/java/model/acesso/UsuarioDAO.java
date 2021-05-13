package model.acesso;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

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
		try {
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletar(Usuario object) {
		try {
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
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

	public List<Usuario> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		criteria.from(Usuario.class);
		Query query = session.createQuery(criteria);
		@SuppressWarnings("unchecked")
		List<Usuario> selectedUsuarios = query.getResultList();
		return selectedUsuarios;
	}

	public Usuario consultarPorLogin(String login) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		criteria.select(root);
		Expression loginEx = (Expression) root.get("login");

		criteria.select(root).where(builder.like(loginEx, login + "%"));
		Query query = session.createQuery(criteria);
		return (Usuario) query.getSingleResult();
	}

	public List<Perfil> listarPerfis(int idUsuario) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Perfil> criteriaQuery = criteriaBuilder.createQuery(Perfil.class);
		CriteriaQuery<Usuario> criteriaUsuarioQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
		//criteriaUsuarioQuery.select(usuarioRoot.get());
		
		criteriaQuery.where(criteriaBuilder.equal(usuarioRoot.get(Usuario_.idUsuario), idUsuario));
		
		SetJoin<Usuario, Perfil> perfisDoUsuario = usuarioRoot.join(Answer.collaborators);
		
		CriteriaQuery<Perfil> cq = criteriaQuery.select(perfisDoUsuario);
		
		TypedQuery<Perfil> query = session.createQuery(cq);
		
		return query.getResultList();
	}

	public ArrayList<Permissao> listarPermissoes(int idUsuario) {
		return null;
	}

	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil) {

	}
}
