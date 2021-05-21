package model.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import db.DBConnection;
import model.entidades.Usuario;
import model.interfaces.ICrud;

/**
 * Classe UsuarioDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * usuarios.
 * 
 * @author Simon gabrielsimon775@gmail.com
 * @author Jonata Caetano jonatacaetano88@gmail.com
 * @author Lucas Grijó rksgrijo@gmail.com
 * @author Lorran lorransantospereira@yahoo.com.br
 * @author Thiago thiagoluizbarbieri@gmail.com
 * @since Sprint 4&5
 * 
 */
public class UsuarioDAO implements ICrud<Usuario> {

	private static UsuarioDAO instance;
	private Session session;

	private UsuarioDAO(Session session) {
		this.session = session;
	}

	/**
	 * Conexão com banco de dados.
	 * 
	 * Conexão de um banco de dados realizada atráves de um Singleton.
	 * 
	 * @return instance.
	 * @since Sprint 4&5.
	 */
	public static UsuarioDAO getInstance() {
		if (instance == null) {
			instance = new UsuarioDAO(DBConnection.getSession());
		}
		return instance;
	}

	/**
	 * Criação de um {@link Usuario}.
	 * 
	 * Método responsável por criar um objeto do tipo {@link Usuario} em um banco de
	 * dados.
	 * 
	 * @param Usuario - usuario
	 * @throws Exception - Caso o {@link Usuario} não seja salvo no banco de dados.
	 * @since Sprint 4&5.
	 */
	public void criar(Usuario usuario) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(usuario);
			tx.commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Altera um {@link Usuario} existente.
	 * 
	 * Método responsável por alterar um objeto do tipo {@link Usuario} salvo em um
	 * banco de dados.
	 * 
	 * @param Usuario - usuario
	 * @return boolean
	 * @throws Exception - Caso o {@link Usuario} não seja alterado no banco de
	 *                   dados.
	 * @since Sprint 4&5.
	 */
	public boolean alterar(Usuario usuario) {
		try {
			Transaction tx = session.beginTransaction();
			session.update(usuario);
			tx.commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deleção de um {@link Usuario} em um banco de dados.
	 * 
	 * Método responsável por deletar um objeto do tipo {@link Usuario} existente em
	 * um banco de dados.
	 * 
	 * @param Usuario - usuario
	 * @return boolean
	 * @throws Exception - Caso o {@link Usuario} não seja deletado no banco de
	 *                   dados.
	 * @since Sprint 4&5.
	 */
	public boolean deletar(Usuario usuario) {
		try {
			Transaction tx = session.beginTransaction();
			session.delete(usuario);
			tx.commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Consulta {@link Usuario} por Id.
	 * 
	 * Método responsável por consultar um objeto do tipo {@link Usuario} através de
	 * seu Id existente em um banco de dados.
	 * 
	 * @param int - id
	 * @return Usuario
	 * @throws Exception - Caso o {@link Usuario} não seja encontrado no banco de
	 *                   dados pelo seu Id.
	 * @since Sprint 4&5.
	 */
	public Usuario consultarPorId(int id) {
		Transaction tx = session.beginTransaction();
		Usuario usuario = session.find(Usuario.class, id);
		tx.commit();
		return usuario;
	}

	/**
	 * Lista de todos os {@link Usuario}.
	 * 
	 * Método responsável por trazer uma lista de objetos do tipo {@link Usuario}
	 * existente do banco de dados.
	 * 
	 * @return List<Usuario>
	 * @since Sprint 4&5
	 */
	public List<Usuario> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		criteria.from(Usuario.class);
		Query query = session.createQuery(criteria);
		@SuppressWarnings("unchecked")
		List<Usuario> selectedUsuarios = query.getResultList();
		return selectedUsuarios;
	}

	/**
	 * Consulta de um {@link Usuario} pelo seu login.
	 * 
	 * Método responsável por consultar um objeto do tipo {@link Usuario} através de
	 * seu login existente do banco de dados.
	 * 
	 * @param String - login
	 * @return Usuario
	 * @since Sprint 4&5.
	 */
	@SuppressWarnings("rawtypes")
	public Usuario consultarPorLogin(String loginASerConsultado) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		Expression login = (Expression) root.get("login");
		loginASerConsultado = loginASerConsultado.trim();
		criteria.select(root).where(builder.like(login, loginASerConsultado));
		Query query = session.createQuery(criteria);
		Usuario usuario = (Usuario) query.getSingleResult();
		return usuario;
	}

	public Usuario verificarCodigoDeConfirmacao(String login, Integer codigoDeConfirmacao) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		criteria.select(root);
		Expression loginEx = (Expression) root.get("login");
		Expression codigoEx = (Expression) root.get("ultimoCodigo2FA");

		criteria.select(root).where(builder.like(loginEx, login + "%"));
		criteria.select(root).where(builder.equal(codigoEx, codigoDeConfirmacao));
		Query query = session.createQuery(criteria);
		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;

		}
	}

	/**
	 * Deleta todos os registros da tabela {@link Usuario}.
	 */
	public void deletarTodos() {
		Transaction transacao = session.beginTransaction();
		session.createSQLQuery("DELETE FROM usuario").executeUpdate();
		transacao.commit();

	}

}
