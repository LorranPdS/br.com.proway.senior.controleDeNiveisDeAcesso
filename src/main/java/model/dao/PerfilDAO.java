package model.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import db.DBConnection;
import model.entidades.Perfil;
import model.entidades.Permissao;
import model.interfaces.ICrud;

/**
 * Classe PerfilDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * perfis
 * 
 * @author Simon gabrielsimon775@gmail.com
 * @author Jonata Caetano jonatacaetano88@gmail.com
 * @author Lucas Grijo rksgrijo@gmail.com
 * @author Lorran lorransantospereira@yahoo.com.br
 * @author Thiago thiagoluizbarbieri@gmail.com
 * @since Sprint 4&5
 */

public class PerfilDAO implements ICrud<Perfil> {

	private static PerfilDAO instance;
	private Session session;

	private PerfilDAO(Session session) {
		this.session = session;
	}

	/**
	 * Conexao com banco de dados.
	 * 
	 * Conexao de um banco de dados realizada atraves de um Singleton.
	 * 
	 * @return instance.
	 * @since Sprint 4&5.
	 */
	public static PerfilDAO getInstance() {
		if (instance == null) {
			instance = new PerfilDAO(DBConnection.getSession());
		}
		return instance;
	}

	/**
	 * Criacao de um {@link Perfil}.
	 * 
	 * Metodo responsavel por criar um objeto do tipo {@link Perfil} em um banco de
	 * dados.
	 * 
	 * @param Perfil - perfil
	 * @throws Exception - Caso o {@link Perfil} n�o seja salvo no banco de dados.
	 * @since Sprint 4&5.
	 */
	public void criar(Perfil perfil) {
		try {
			Transaction tx = session.beginTransaction();
			session.save(perfil);
			tx.commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}

	}

	/**
	 * Altera um {@link Perfil} existente.
	 * 
	 * Metodo respons�vel por alterar um objeto do tipo {@link Perfil} salvo em um
	 * banco de dados.
	 * 
	 * @param Perfil - perfil
	 * @return boolean
	 * @throws Exception - Caso o {@link Perfil} n�o seja alterado no banco de dados.
	 * @since Sprint 4&5.
	 */
	public boolean alterar(Perfil perfil) {
		try {
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(perfil);
			tx.commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deleta um {@link Perfil} do banco de dados.
	 * 
	 * Metodo responsavel por deletar um objeto do tipo {@link Perfil} existente em
	 * um banco de dados.
	 * 
	 * @param Perfil - perfil
	 * @return boolean
	 * @throws Exception - Caso o {@link Perfil} nao seja deletado no banco de dados.
	 * @since Sprint 4&5.
	 */
	public boolean deletar(Perfil perfil) {
		try {
			Transaction tx = session.beginTransaction();
			session.delete(perfil);
			tx.commit();
			return true;

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Consulta {@link Perfil} por Id.
	 * 
	 * Metodo respons�vel por consultar um objeto do tipo {@link Perfil} atrav�s de
	 * seu Id existente em um banco de dados.
	 * 
	 * @param int - id
	 * @return Perfil
	 * @throws Exception - Caso o {@link Perfil} nao seja encontrado no banco de dados pelo seu Id.
	 * @since Sprint 4&5.
	 */
	public Perfil consultarPorId(int id) {
		try {
			Transaction tx = session.beginTransaction();
			Perfil perfilEncontrado = session.find(Perfil.class, id);
			tx.commit();
			return perfilEncontrado;
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lista de todos os {@link Perfil}.
	 * 
	 * Metodo responsavel por trazer uma lista de objetos do tipo {@link Perfil}
	 * existente do banco de dados.
	 * 
	 * @return List<Perfil>
	 * @since Sprint 4&5
	 */
	public List<Perfil> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Perfil> criteria = builder.createQuery(Perfil.class);

		criteria.from(Perfil.class);

		Query query = session.createQuery(criteria);

		@SuppressWarnings("unchecked")
		List<Perfil> selecionarPerfis = query.getResultList();

		return selecionarPerfis;
	}

	/**
	 * Consulta de um {@link Perfil} pelo nome.
	 * 
	 * Metodo responsavel por consultar um objeto do tipo {@link Perfil} atrav�s de
	 * seu nome existente do banco de dados.
	 * 
	 * @param String - nome_perfil
	 * @return Perfil
	 * @since Sprint 4&5.
	 */
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

	/**
	 * Lista todas as {@link Permissao} de um {@link Perfil}.
	 * 
	 * Responsavel por recuperar do banco de dados todas as {@link Permissao} que 
	 * um {@link Perfil} possui.
	 * 
	 * @param idPerfil int
	 * @return List<Permissao>
	 */
	public List<Permissao> listarPermissoes(int idPerfil) {
		Perfil p = consultarPorId(idPerfil);
		return p.getPermissoes();
	}

	/**
	 * Atribui {@link Permissao} a um {@link Perfil}
	 * 
	 * Responsavel por atribuir uma {@link Permissao} a um {@link Perfil} e registrar 
	 * no banco de dados.
	 * 
	 * @param perfil
	 * @param permissao
	 */
	public void atribuirPermissaoAUmPerfil(Perfil perfil, Permissao permissao) {
		perfil.setPermissoes(permissao);
		alterar(perfil);
	}
	
	/**
	 * Deleta todos os registros da tabela {@link Perfil}.
	 */
	public void deletarTodos() {

		Transaction transacao2 = session.beginTransaction();
		session.createSQLQuery("DELETE FROM perfil_permissao").executeUpdate();
		transacao2.commit();
		
		
		Transaction transacao = session.beginTransaction();
		session.createSQLQuery("DELETE FROM perfil").executeUpdate();
		transacao.commit();
	}
}
