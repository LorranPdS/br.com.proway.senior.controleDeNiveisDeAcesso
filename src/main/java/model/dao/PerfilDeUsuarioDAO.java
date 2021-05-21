package model.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.DBConnection;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class PerfilDeUsuarioDAO {

	private static PerfilDeUsuarioDAO instance;
	private Session session;

	private PerfilDeUsuarioDAO(Session session) {
		this.session = session;
	}

	/**
	 * Verifica se a instancia e nula, se for ela e instanciada. Se caso ja existir
	 * so e retornada.
	 * 
	 * @return instance
	 */
	public static PerfilDeUsuarioDAO getInstance() {
		if (instance == null) {
			instance = new PerfilDeUsuarioDAO(DBConnection.getSession());
		}
		return instance;
	}

	/**
	 * Atribui um {@link Perfil} a um {@link Usuario}.
	 * 
	 * Método responsável por atribuir um {@link Perfil} a um {@link Usuario}.
	 * 
	 * @param PerfilDeUsuario - usuarioPerfil
	 * @throws Exception - Caso a atribuição do {@link Perfil} ao {@link Usuario}
	 *                   não seja possivel.
	 * @since Sprint 4&5
	 */
	public void atribuirPerfilAUmUsuario(PerfilDeUsuario perfilDeUsuario) {
		try {
			session.beginTransaction();
			session.save(perfilDeUsuario);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	public ArrayList<PerfilDeUsuario> consultarPorIdDoUsuario(Integer id) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PerfilDeUsuario> criteria = builder.createQuery(PerfilDeUsuario.class);
		Root<PerfilDeUsuario> root = criteria.from(PerfilDeUsuario.class);

		Expression idUsuario = (Expression) root.get("usuario");
		criteria.select(root).where(builder.equal(idUsuario, id));
		Query<PerfilDeUsuario> query = session.createQuery(criteria);
		List<PerfilDeUsuario> lista = query.getResultList();
		return (ArrayList<PerfilDeUsuario>) lista;
	}

	public ArrayList<PerfilDeUsuario> consultarPorIdDoPerfil(Integer id) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PerfilDeUsuario> criteria = builder.createQuery(PerfilDeUsuario.class);
		Root<PerfilDeUsuario> root = criteria.from(PerfilDeUsuario.class);

		Expression idPerfil = (Expression) root.get("perfil_id");
		criteria.select(root).where(builder.equal(idPerfil, id));
		Query<PerfilDeUsuario> query = session.createQuery(criteria);
		List<PerfilDeUsuario> lista = query.getResultList();
		return (ArrayList<PerfilDeUsuario>) lista;
	}

	/**
	 * Lista de todas as {@link Permissao} de um {@link Usuario}.
	 * 
	 * Método responsável por trazer uma lista de objetos do tipo {@link Permissao}.
	 * 
	 * @param idUsuario
	 * @return List<Permissao> todasAsPermissoesDoUsuario.
	 * @since Sprint 4&5.
	 */
	public Set<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Perfil> listaDePerfisDoUsuario = PerfilDeUsuarioDAO.getInstance().listarPerfisDeUmUsuario(idUsuario);

		Set<Permissao> todasAsPermissoesDoUsuario = new HashSet<>();
		for (Perfil perfil : listaDePerfisDoUsuario) {
			todasAsPermissoesDoUsuario.addAll(perfil.getPermissoes());
		}
		return todasAsPermissoesDoUsuario;
	}

	/**
	 * Lista de todos os {@link Perfil} de um {@link Usuario}.
	 * 
	 * Método responsável por trazer uma lista de objetos do tipo {@link Perfil}.
	 * 
	 * @param int - idUsuario
	 * @return List<Perfil> - listaPerfil
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		List<PerfilDeUsuario> lista = consultarPorIdDoUsuario(idUsuario);
		List<Perfil> perfis = new ArrayList<>();
		for (PerfilDeUsuario ligacao : lista) {
			perfis.add(ligacao.getPerfil());
		}
		return perfis;
	}
	
	public boolean deletar(PerfilDeUsuario objeto) {
		try {
			Transaction tx = session.beginTransaction();
			session.delete(objeto);
			tx.commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		Transaction transacao = session.beginTransaction();
		session.createSQLQuery("DELETE FROM perfildeusuario").executeUpdate();
		transacao.commit();
	}
}
