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
import model.interfaces.ICrud;

/**
 * Classe PerfilDeUsuario.
 * 
 * <p>
 * Faz contato com o banco de dados para consulta e insercao de dados.
 * 
 * <p>
 * Classe representando a tabela de ligacao entre {@link Usuario} e
 * {@link Perfil). Perfis atrelados a um usuario podem expirar com o tempo
 * através do campo data_expiracao.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 */
public class PerfilDeUsuarioDAO implements ICrud<PerfilDeUsuario> {

	// alterar
	// consultar por id

	private static PerfilDeUsuarioDAO instance;
	private Session session;

	private PerfilDeUsuarioDAO(Session session) {
		this.session = session;
	}

	/**
	 * Verifica se a instancia eh nula, se for ela eh instanciada. Se caso ja
	 * existir so eh retornada.
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
	 * Cadastra um objeto do tipo {@link PerfilDeUsuario} no banco de dados.
	 * 
	 * @param perfilDeUsuario PerfilDeUsuario Objeto a ser cadastrado.
	 */
	public void criar(PerfilDeUsuario perfilDeUsuario) {
		session.beginTransaction();
		session.save(perfilDeUsuario);
		session.getTransaction().commit();
	}

	/**
	 * Consulta um {@link PerfilDeUsuario} pelo valor da coluna 'usuario_id'.
	 * 
	 * <p>
	 * Retorna uma lista do tipo {@link PerfilDeUsuario} que possui o 'usuario_id'
	 * igual 'id' recebi do parametro.
	 * 
	 * @param id Integer Id do objeto a ser consultado.
	 * @return ArrayList Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	@SuppressWarnings("rawtypes")
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

	/**
	 * Consulta um {@link PerfilDeUsuario} pelo valor da coluna 'perfil_id'.
	 * 
	 * <p>
	 * Retorna uma lista do tipo {@link PerfilDeUsuario} que possui o 'perfil_id'
	 * igual 'id' recebi do parametro.
	 * 
	 * @param id Integer Id do objeto a ser consultado.
	 * @return ArrayList Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<PerfilDeUsuario> consultarPorIdDoPerfil(Integer id) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PerfilDeUsuario> criteria = builder.createQuery(PerfilDeUsuario.class);
		Root<PerfilDeUsuario> root = criteria.from(PerfilDeUsuario.class);

		Expression idPerfil = (Expression) root.get("perfil");
		criteria.select(root).where(builder.equal(idPerfil, id));
		Query<PerfilDeUsuario> query = session.createQuery(criteria);
		List<PerfilDeUsuario> lista = query.getResultList();
		return (ArrayList<PerfilDeUsuario>) lista;
	}

	/**
	 * Retorna todas as {@link Permissao} de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado. Percorre todos os
	 * {@link Perfil} do {@link Usuario}, pega todas as {@link Permissao} de cada
	 * {@link Perfil} e adiciona em uma lista.
	 * 
	 * <p>
	 * Observacao: as {@link Permissao} nao se repetem na lista.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todas as {@link Permissao} do {@link Usuario}.
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
	 * Retorna todas as {@link Perfil} de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario}.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	public List<Perfil> listarPerfisDeUmUsuario(int idUsuario) {
		List<PerfilDeUsuario> lista = consultarPorIdDoUsuario(idUsuario);
		List<Perfil> perfis = new ArrayList<>();
		for (PerfilDeUsuario ligacao : lista) {
			perfis.add(ligacao.getPerfil());
		}
		return perfis;
	}

	/**
	 * Deleta um registro da tabela {@link PerfilDeUsuario} que corresponde ao
	 * 'objeto' recebido no parametro.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser deletado.
	 */
	public boolean deletar(PerfilDeUsuario objeto) {
		session.beginTransaction();
		session.delete(objeto);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		Transaction transacao = session.beginTransaction();
		session.createSQLQuery("DELETE FROM perfildeusuario").executeUpdate();
		transacao.commit();
	}

	/**
	 * 
	 * Altera um PerfilDeUsuario no banco de dados.
	 * 
	 * Recebe um objeto do tipo {@link PerfilDeUsuario} no parametro e atualiza o
	 * registro correspondente que está no banco de dados.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser atualizado no banco de dados.
	 * @boolean Retorna true.
	 */
	public boolean alterar(PerfilDeUsuario objeto) {
		session.beginTransaction();
		session.update(objeto);
		session.getTransaction().commit();
		return true;
	}

	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	public PerfilDeUsuario consultarPorId(int id) {
		session.beginTransaction();
		PerfilDeUsuario objeto = session.find(PerfilDeUsuario.class, id);
		session.getTransaction().commit();
		return objeto;
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PerfilDeUsuario> listar() {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PerfilDeUsuario> criteria = builder.createQuery(PerfilDeUsuario.class);
		criteria.from(PerfilDeUsuario.class);
		Query query = session.createQuery(criteria);
		List<PerfilDeUsuario> permissoes = query.getResultList();
		return permissoes;
	}
}
