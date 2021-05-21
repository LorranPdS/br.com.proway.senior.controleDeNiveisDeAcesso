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
 * Classe PermissaoDAO.
 * 
 * Classe que faz contato direto com o banco de dados.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 */

public class PermissaoDAO implements ICrud<Permissao> {

	private static PermissaoDAO instance;
	private Session session;

	private PermissaoDAO(Session session) {
		this.session = session;
	}

	/**
	 * Verifica se a instancia eh nula, se for ela eh instanciada. Se caso ja
	 * existir so eh retornada.
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
	 * Insere um objeto do tipo {@link Permissao} no banco de dados.
	 * 
	 * @param object Permissao Objeto a ser inserido no banco de dados.
	 */
	public void criar(Permissao object) {
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
	}

	/**
	 * Altera uma Permissao no banco.
	 * 
	 * Recebe um objeto do tipo {@link Permissao} no parametro e atualiza o registro
	 * correspondente que está no banco de dados.
	 * 
	 * @param permissao Permissao Objeto a ser atualizado no banco de dados.
	 * @boolean Retorna true.
	 */
	public boolean alterar(Permissao permissao) {
		session.beginTransaction();
		session.update(permissao);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Deleta um objeto do tipo {@link Permissao}.
	 * 
	 * @param permissao Objeto a ser deletado do banco de dados.
	 */
	public boolean deletar(Permissao permissao) {
		session.beginTransaction();
		session.delete(permissao);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Consulta um objeto do tipo {@link Permissao} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	public Permissao consultarPorId(int id) {
		session.beginTransaction();
		Permissao permissao = session.find(Permissao.class, id);
		session.getTransaction().commit();
		return permissao;
	}

	/**
	 * Consultar uma {@link Permissao} por nome.
	 * 
	 * @param nome String Nome da permissao a ser consultada.
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
	 * Retorna uma lista contendo todos os registros da tabela {@link Permissao}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link Permissao}.
	 */
	@SuppressWarnings("unchecked")
	public List<Permissao> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Permissao> criteria = builder.createQuery(Permissao.class);
		criteria.from(Permissao.class);
		Query query = session.createQuery(criteria);
		List<Permissao> permissoes = query.getResultList();
		return permissoes;
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