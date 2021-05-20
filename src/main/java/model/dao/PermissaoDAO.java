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
import model.entidades.Permissao;
import model.interfaces.ICrud;

/**
 * Classe PermissaoDAO
 * 
 * Classe que implementa a interface que se relaciona com o banco de dados de
 * permissoes.
 * 
 * @author Sprint 5
 * @author Gabriel Simon, gabrielsimon775@gmail.com
 * @author Jonata Caetano, jonatacaetano88@gmail.com
 * @author Lucas Grijó, rksgrijo@gmail.com
 * @author Lorran, lorransantospereira@yahoo.com.br
 * @author Thiago, thiagoluizbarbieri@gmail.com
 */

public class PermissaoDAO implements ICrud<Permissao> {

	private static PermissaoDAO instance;
	private Session session;

	private PermissaoDAO(Session session) {
		this.session = session;
	}

	/**
	 * Verifica se a instancia e nula, se for ela e instanciada. Se caso ja existir
	 * so e retornada.
	 * 
	 * @return instance
	 */
	public static PermissaoDAO getInstance() {
		if (instance == null) {
			instance = new PermissaoDAO(DBConnection.getSession());
		}
		return instance;
	}

	/**
	 * Persiste uma Permissao no banco.
	 * 
	 * @param object
	 */
	public void criar(Permissao object) {
		Transaction tx = session.beginTransaction();
		session.save(object);
		tx.commit();
	}

	/**
	 * Altera uma Permissao no banco.
	 * 
	 * @param object
	 */
	public boolean alterar(Permissao object) {
			Transaction tx = session.beginTransaction();
			session.update(object);
			tx.commit();
			return true;
	}

	/**
	 * Deleta uma permissao no banco.
	 * 
	 * @param object
	 */
	public boolean deletar(Permissao object) {
			Transaction tx = session.beginTransaction();
			session.delete(object);
			tx.commit();
			return true;
	}

	/**
	 * Consulta uma Permissao por ID no banco.
	 * 
	 * @param id
	 */
	public Permissao consultarPorId(int id) {
			Transaction tx = session.beginTransaction();
			Permissao u = session.find(Permissao.class, id);
			tx.commit();
			return u;
	}

	/**
	 * Retorna uma lista de permissoes.
	 * 
	 * @return listaPermissoes
	 */
	public List<Permissao> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		criteria.from(Permissao.class);
		Query query = session.createQuery(criteria);
		@SuppressWarnings("unchecked")
		List<Permissao> listaPermissoes = query.getResultList();
		return listaPermissoes;
	}

	/**
	 * Consulta o nome da permissao pelo nome.
	 * 
	 * @param nome
	 * @return Permissao
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Permissao consultarPorNome(String nome) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		Root<Permissao> root = criteria.from(Permissao.class);

		criteria.select(root);
		Expression nomeEX = (Expression) root.get("nomePermissao");

		criteria.select(root).where(builder.like(nomeEX, nome + "%"));
		Query query = session.createQuery(criteria);
		return (Permissao) query.getSingleResult();
	}

	/**
	 * Deleta todos os registros da tabela {@link Permissao}.
	 */
	public void deletarTodos() {
		Transaction transacao = session.beginTransaction();
		session.createSQLQuery("DELETE FROM permissao").executeUpdate();
		transacao.commit();
	}

}
