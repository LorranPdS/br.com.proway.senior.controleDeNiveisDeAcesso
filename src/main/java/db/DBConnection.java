package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.acesso.PerfilDeUsuario;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class DBConnection {
private static SessionFactory sessionFactory;
	
	private static String senha = "admin";
	/**
	 * Define a senha a ser utilizada na conexao com o banco de dados.
	 * Por padrao a senha esta definida como "admin"
	 * @param pass - Novo password
	 */
	public static void setSenha(String novaSenha) {senha = novaSenha;}

	/**
	 * Exclui a instancia da Factory de Session para que possamos instancia-la novamente.
	 */
	public static void limparFactory() {sessionFactory = null;}

	private static Session session;

	private static SessionFactory construirSessaoFactory() {
		try {
			return new Configuration().setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
					.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/grupo3")
					.setProperty("hibernate.connection.username", "postgres")
					.setProperty("hibernate.connection.password", senha)
					.setProperty("hibernate.jdbc.time_zone", "UTC")
					.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
					.setProperty("hibernate.show_sql", "true")
					.setProperty("hibernate.format_sql", "false")
					.setProperty("hibernate.hbm2ddl.auto", "update")
					.setProperty("hibernate.connection.autocommit", "true").addAnnotatedClass(Usuario.class)
					.setProperty("hibernate.connection.autocommit", "true").addAnnotatedClass(Perfil.class)
					.setProperty("hibernate.connection.autocommit", "true").addAnnotatedClass(Permissao.class)
					.setProperty("hibernate.connection.autocommit", "true").addAnnotatedClass(PerfilDeUsuario.class)
					.buildSessionFactory();
		} catch (Throwable e) {
			System.err.println("A criação da SessionFactory inicial falhou: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = construirSessaoFactory();
		return sessionFactory;
	}

	public static Session getSession() {
		getSessionFactory();
		if (session == null)
			session = sessionFactory.openSession();
		return session;
	}
}
