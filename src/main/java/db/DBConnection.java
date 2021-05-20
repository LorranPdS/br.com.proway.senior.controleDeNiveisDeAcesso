package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.dao.PerfilDAO;
import model.dao.PermissaoDAO;
import model.dao.UsuarioDAO;

/**
 * Classe DBConnection
 * 
 * Classe responsavel pela conexao com o banco de dados utilizando o Hibernate
 * (DB).
 * 
 * @author Sprint 5
 * @author Gabriel Simon gabrielsimon775@gmail.com
 * @author Jonata Caetano jonatacaetano88@gmail.com
 * @author Lucas Grijó rksgrijo@gmail.com
 * @author Lorran lorransantospereira@yahoo.com.br
 * @author Thiago thiagoluizbarbieri@gmail.com
 */
public class DBConnection {
	private static SessionFactory sessionFactory;
	private static Session session;

	/**
	 * Verifica se a sessionFactory e nula.
	 * 
	 * @return sessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

	/**
	 * Verifica se a session e nula e retorna a session.
	 * 
	 * @return session
	 * @since Sprint 5
	 */
	public static Session getSession() {
		getSessionFactory();
		if (session == null)
			session = sessionFactory.openSession();
		return session;
	}

	/**
	 * Retorna uma nova configuracao do hibernate.
	 */
	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Initial SessionFactory creation failed: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Transforma a instancia em nula.
	 */
	public static void shutdown() {
		session.close();
		session = null;
		getSessionFactory().close();
		sessionFactory = null;
		UsuarioDAO.shutdown();
		PerfilDAO.shutdown();
		PermissaoDAO.shutdown();
	}

	/**
	 * Reinicia a sequencia do ID do banco para 1.
	 */
	public static void truncateTablesAndRestartSequences() {
		try {
			String sql1 = "TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;";
			String sql2 = "TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_permissao RESTART 1;";
			String sql3 = "TRUNCATE TABLE perfil CASCADE;  ALTER SEQUENCE seq_id_perfil RESTART 1;";
			String sql4 = "TRUNCATE TABLE perfil_permissao CASCADE;";
			String sql5 = "TRUNCATE TABLE usuario_perfil CASCADE;";
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession().createSQLQuery(sql1 + sql2 + sql3 + sql4 + sql5).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}

}
