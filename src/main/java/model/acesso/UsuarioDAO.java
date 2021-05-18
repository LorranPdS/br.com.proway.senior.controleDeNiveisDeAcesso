package model.acesso;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import db.DBConnection;
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
	 * Finalização da instância do Singleton.
	 * 
	 * Método responsável por finalizar a conexão de um banco de dados.
	 * @since Sprint 4&5.
	 */
	public static void shutdown() {
		instance = null;
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
	 * @throws Exception - Caso o {@link Usuario} não seja alterado no banco de dados.
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
	 * @throws Exception - Caso o {@link Usuario} não seja deletado no banco de dados.
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
	 * @throws Exception - Caso o {@link Usuario} não seja encontrado no banco de dados pelo seu Id.
	 * @since Sprint 4&5.
	 */
	public Usuario consultarPorId(int id) {
		try {
			Transaction tx = session.beginTransaction();
			Usuario usuario = session.find(Usuario.class, id);
			tx.commit();
			return usuario;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
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
	public Usuario consultarPorLogin(String login) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);

		criteria.select(root);
		Expression loginEx = (Expression) root.get("login");

		criteria.select(root).where(builder.like(loginEx, login + "%"));
		Query query = session.createQuery(criteria);
		try {
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Lista de todos os {@link Perfil} de um {@link Usuario}.
	 * 
	 * Método responsável por trazer uma lista de objetos do tipo {@link Perfil}.
	 * 
	 * @param int - idUsuario
	 * @return List<Perfil> - listaPerfil
	 */
	public List<Perfil> listarPerfis(int idUsuario) {
		Usuario usuario = consultarPorId(idUsuario);
		List<Perfil> listaPerfil = new ArrayList<>();
		for (UsuarioPerfil usuarioPerfil : usuario.getPerfis()) {
			System.out.println(usuarioPerfil.toString());
			listaPerfil.add(usuarioPerfil.getPerfil());
		}
		return listaPerfil;
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
	public List<Permissao> listarPermissoes(int idUsuario) {
		List<Perfil> listaDePerfisDoUsuario = listarPerfis(idUsuario);

		List<Permissao> todasAsPermissoesDoUsuario = new ArrayList<Permissao>();
		for (Perfil perfil : listaDePerfisDoUsuario) {
			List<Permissao> permissoesDessePerfil = perfil.getPermissoes();
			for (Permissao permissao : permissoesDessePerfil) {
				if (!todasAsPermissoesDoUsuario.contains(permissao)) {
					todasAsPermissoesDoUsuario.add(permissao);
				}
			}
		}
		return todasAsPermissoesDoUsuario;
	}
 
	/**
	 * Atribui um {@link Perfil} a um {@link Usuario}.
	 * 
	 * Método responsável por atribuir um {@link Perfil} a um {@link Usuario}.
	 * 
	 * @param UsuarioPerfil - usuarioPerfil
	 * @throws Exception - Caso a atribuição do {@link Perfil} ao {@link Usuario} não seja possivel.
	 * @since Sprint 4&5
	 */
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
	

	public void removerPerfilDeUmUsuario(int idPerfil, int idUsuario) {
		String sql1 = "delete from usuario_perfil where id_perfil = '"+idPerfil+"' and id_usuario= '"+idUsuario+"';";
		try {
			DBConnection.getSession().beginTransaction();
			DBConnection.getSession()
					.createSQLQuery(sql1).executeUpdate();
			DBConnection.getSession().getTransaction().commit();
		} catch (Exception e) {
			DBConnection.getSession().getTransaction().rollback();
			e.printStackTrace();
    }
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
}
