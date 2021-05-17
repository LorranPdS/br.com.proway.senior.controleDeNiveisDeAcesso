package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.acesso.PerfilDAO;
import model.acesso.PermissaoDAO;
import model.acesso.UsuarioDAO;

/**
 * Classe DBConnection
 * 
 * Classe responsavel pela conexao com o banco de dados utilizando o Hibernate (DB)
 * 
 * @author Sprint 5
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */
public class DBConnection {
	private static SessionFactory sessionFactory;
	private static Session session;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

	public static Session getSession() {
		getSessionFactory();
		if (session == null)
			session = sessionFactory.openSession();
		return session;
	}

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("Initial SessionFactory creation failed: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static void shutdown() {
		session.close();
		session = null;
		getSessionFactory().close();
		sessionFactory = null;
		UsuarioDAO.shutdown();
		PerfilDAO.shutdown();
		PermissaoDAO.shutdown();
	}
	
	public static void truncateTablesAndRestartSequences() {
		try {
			String sql1 = "TRUNCATE TABLE usuario CASCADE; ALTER SEQUENCE seq_id_usuario RESTART 1;";
			String sql2 = "TRUNCATE TABLE permissao CASCADE; ALTER SEQUENCE seq_id_permissao RESTART 1;";
			String sql3 = "TRUNCATE TABLE perfil CASCADE;  ALTER SEQUENCE seq_id_perfil RESTART 1;";
			String sql4 = "TRUNCATE TABLE perfil_permissao CASCADE;";
			String sql5 = "TRUNCATE TABLE usuario_perfil CASCADE;";
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sql1 + sql2 + sql3 + sql4 + sql5)
					.executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	
}
