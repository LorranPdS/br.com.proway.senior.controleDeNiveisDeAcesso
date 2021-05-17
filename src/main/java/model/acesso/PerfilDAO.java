package model.acesso;

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

	public void criar(Perfil perfil) {
		try {
			session.beginTransaction();
			session.save(perfil);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	public boolean alterar(Perfil perfil) {
		try {
			session.beginTransaction();
			session.saveOrUpdate(perfil);
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletar(Perfil perfil) {
		try {
			session.beginTransaction();
			session.delete(perfil);
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	public Perfil consultarPorId(int id) {
		try {
			session.beginTransaction();
			Perfil perfilEncontrado = session.find(Perfil.class, id);
			return perfilEncontrado;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	public List<Perfil> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);

		criteria.from(Perfil.class);

		Query query = session.createQuery(criteria);

		@SuppressWarnings("unchecked")
		List<Perfil> selecionarPerfis = query.getResultList();

		return selecionarPerfis;
	}

	@SuppressWarnings("unchecked")
	public Perfil consultarPorNome(String nome_perfil) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);
		Root<Perfil> root = criteria.from(Perfil.class);
		criteria.select(root);
		@SuppressWarnings("rawtypes")
		Expression nomeEx = (Expression) root.get("nomePerfil");
		criteria.select(root).where(builder.like(nomeEx, nome_perfil));

		Query query = session.createQuery(criteria);
		return (Perfil) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Permissao> listarPermissoes(int idPerfil) {
		Perfil p = consultarPorId(idPerfil);
		return p.getPermissoes();
	}

	public void atribuirPermissaoAUmPerfil(Perfil perfil, Permissao permissao) {
		System.out.println("PERMISSAO " + permissao.getIdPermissao());
		System.out.println("NO PERFIL  " + perfil.getIdPerfil());
		//Permissao permissao = PermissaoDAO.getInstance().consultarPorId(idPermissao);
		perfil.setPermissoes(permissao);
		alterar(perfil);
	}

}
