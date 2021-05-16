package model.acesso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

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

	public void criar(Usuario usuario) {
		try {
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public boolean alterar(Usuario usuario) {
		try {
			session.beginTransaction();
			session.update(usuario);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletar(Usuario usuario) {
		try {
			session.beginTransaction();
			session.delete(usuario);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuario consultarPorLogin(String login) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		criteria.select(root);
		Expression loginEx = (Expression) root.get("login");

		criteria.select(root).where(builder.like(loginEx, login + "%"));
		Query query = session.createQuery(criteria);
		return  (Usuario) query.getSingleResult();
	}

	public List<Perfil> listarPerfis(int idUsuario) {
		Usuario usuario = consultarPorId(idUsuario);

		List<Perfil> listaPerfil = new ArrayList<>();
		for (UsuarioPerfil usuarioPerfil : usuario.getPerfis()) {
			listaPerfil.add(usuarioPerfil.getPerfil());
		}
		
		return listaPerfil;
	}

	public List<Permissao> listarPermissoes(int idUsuario) {
		List<Perfil> listaDePerfisDoUsuario = listarPerfis(idUsuario);
		
		List<Permissao> todasAsPermissoesDoUsuario = new ArrayList<Permissao>();
		for (Perfil perfil : listaDePerfisDoUsuario) {
			List<Permissao> permissoesDessePerfil = perfil.getPermissoes();
			todasAsPermissoesDoUsuario.addAll(permissoesDessePerfil);
		}
		
		return todasAsPermissoesDoUsuario;
	}

	public void atribuirPerfilAUmUsuario(UsuarioPerfil usuarioPerfil) {
		try {
			session.beginTransaction();
			session.save(usuarioPerfil);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
