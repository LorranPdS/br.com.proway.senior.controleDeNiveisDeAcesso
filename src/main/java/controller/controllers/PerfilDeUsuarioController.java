package controller.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.dao.PerfilDAO;
import model.dao.PerfilDeUsuarioDAO;
import model.dao.PermissaoDAO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * Classe que faz contato com o {@link PerfilDeUsuarioDAO}.
 * 
 * @author Daniella Lira <b>daniella.lira@senior.com</b> - Sprint 6
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6 *
 */
public class PerfilDeUsuarioController {

	PerfilDeUsuarioDAO ligacaoDAO = PerfilDeUsuarioDAO.getInstance();

	/**
	 * Atribui um {@link Perfil} a um {@link Usuario}.
	 * 
	 * <p>
	 * Verifica se existe um {@link Usuario} e {@link Perfil} com o id informado, se
	 * existir, verifica se a data de expiraca eh posterior a data atual, se for,
	 * atribui o perfil ao usuario informados. Caso usuario e perfil nao existam ou
	 * data nao seja valida, retorna uma excessao.
	 * 
	 * @param usuario Usuario {@link Usuario} que recebera um perfil de acesso.
	 * @param perfil  Perfil {@link Perfil} que sera atribuido a um usuario.
	 * @throws Exception - Caso usuario e perfil nao existam ou data nao seja
	 *                   valida.
	 * @return boolean Retorna true caso as validacoes passem, retorna false caso as
	 *         validacoes nao passem.
	 */
	public boolean atribuirPerfilAUmUsuario(int idUsuario, int idPerfil, LocalDate dataExpiracao) throws Exception {
		UsuarioController usuarioController = new UsuarioController();
		Usuario usuario = usuarioController.consultarUsuario(idUsuario);

		PerfilController perfilController = new PerfilController();
		Perfil perfil = perfilController.consultarPerfil(idPerfil);

		if (usuario == null && perfil == null)
			throw (new Exception("O usuário ou perfil não foi encontrado no banco de dados."));

		if (dataExpiracao.isBefore(LocalDate.now()))
			throw (new Exception("A data de expiração não pode ser anterior a data atual."));

		PerfilDeUsuario perfilDeUsuario = new PerfilDeUsuario(usuario, perfil, dataExpiracao, true);
		ligacaoDAO.criar(perfilDeUsuario);
		return true;
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
	public ArrayList<PerfilDeUsuario> consultarPorIdDoUsuario(Integer id) {
		return ligacaoDAO.consultarPorIdDoUsuario(id);
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
	public ArrayList<PerfilDeUsuario> consultarPorIdDoPerfil(Integer id) {
		return ligacaoDAO.consultarPorIdDoPerfil(id);
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
	public List<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Perfil> listaDePerfisDoUsuario = this.listarPerfisDeUmUsuario(idUsuario);

		Set<Permissao> todasAsPermissoesDoUsuario = new HashSet<>();
		for (Perfil perfil : listaDePerfisDoUsuario) {
			todasAsPermissoesDoUsuario.addAll(perfil.getPermissoes());
		}

		List<Permissao> permissoes = new ArrayList<Permissao>();
		for (Permissao permissao : todasAsPermissoesDoUsuario) {
			permissoes.add(permissao);
		}
		return permissoes;

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
	 * Verifica se o {@link Usuario} 'usuario' possui a {@link Permissao} recebida
	 * no parametro.
	 * 
	 * <p>
	 * Verifica se existe um usuario e permissao com os id's informados. Se nao
	 * existir, retorna uma excessao. Se existir prossegue.
	 * 
	 * <p>
	 * Busca todos os registros da tabela {@link PerfilDeUsuario} os quais possuam a
	 * data de expiracao valida. Pega as permissoes desses perfis validos e verifica
	 * se alguma destas eh igual a permissao recebida no parametro.
	 * 
	 * @param idUsuario   int
	 * @param idPermissao int
	 * @return Retorna true caso ele possua um perfil ativo que possua a 'permissao'
	 *         recebida no parametro.
	 * @throws Exception
	 */
	public boolean usuarioPossuiPermissaoPara(Integer idUsuario, Integer idPermissao) throws Exception {
		UsuarioController usuarioController = new UsuarioController();
		if (usuarioController.consultarUsuario(idUsuario) == null)
			throw (new Exception("O usuário não existe."));

		List<PerfilDeUsuario> ligacoes = consultarPorIdDoUsuario(idUsuario);

		if (ligacoes.size() == 0)
			throw new Exception("O usuário não possui perfis.");

		Permissao _permissao = PermissaoDAO.getInstance().consultarPorId(idPermissao);

		if (_permissao == null)
			throw new Exception("A permissão informada não existe.");

		if (ligacoes.size() > 0) {
			for (PerfilDeUsuario ligacao : ligacoes) {
				if (!ligacao.getDataExpiracao().isBefore(LocalDate.now())) {
					List<Permissao> permissoes = ligacao.getPerfil().getPermissoes();
					for (Permissao permissao : permissoes) {
						if (permissao.getNomePermissao() == _permissao.getNomePermissao())
							return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Verifica se o {@link Usuario} 'usuario' possui o {@link Perfil} recebido no
	 * parametro.
	 * 
	 * <p>
	 * Verifica se existe um usuario e perfil com os id's informados. Se nao
	 * existir, retorna uma excessao. Se existir prossegue.
	 * 
	 * <p>
	 * Chama o metodo listarPerfisAtivosDeUmUsuario para percorrer todos os perfis
	 * ativos do usuario recebido no parametro. Verifica se algum destes perfis eh
	 * igual ao perfil recebido no parametro.
	 * 
	 * @param idUsuario int
	 * @param idPerfil  int
	 * @return Retorna true caso encontre um perfil ativo igual ao perfil recebido
	 *         no parametro.
	 * @throws Exception
	 */
	public boolean usuarioPossuiOPerfil(int idUsuario, int idPerfil) throws Exception {
		UsuarioController usuarioController = new UsuarioController();
		if (usuarioController.consultarUsuario(idUsuario) == null)
			throw (new Exception("O usuário não existe."));

		PerfilController perfilController = new PerfilController();
		if (perfilController.consultarPerfil(idPerfil) == null)
			throw (new Exception("O perfil não existe."));

		List<Perfil> perfis = listarPerfisAtivosDeUmUsuario(idUsuario);
		if (perfis.size() == 0)
			return false;

		Perfil _perfil = PerfilDAO.getInstance().consultarPorId(idPerfil);
		for (Perfil perfil : perfis) {
			if (perfil.getNomePerfil() == _perfil.getNomePerfil())
				return true;
		}
		return false;
	}

	/**
	 * Valida a ligacao verificando se a mesma esta ativa e possui data de expiracao
	 * posterior a data atual.
	 * 
	 * @param ligacao PerfilDeUsuario Ligacao entre usuario e perfil a ser validada.
	 * @return True caso a ligacao esteja ativa e com data posterior a data atual.
	 * @throws Exception
	 */
	public boolean permissaoAtiva(PerfilDeUsuario ligacao) throws Exception {
		if (consultarPorId(ligacao.getId()) == null)
			throw (new Exception("Não encontramos um registro com o id do objeto informado."));

		if (ligacao.getAtivo() && ligacao.getDataExpiracao().isAfter(LocalDate.now()))
			return true;
		return false;
	}

	/**
	 * Retorna todas as {@link Perfil} ativos de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario} que possua ativo igual a true.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	public List<Perfil> listarPerfisAtivosDeUmUsuario(int idUsuario) {
		List<PerfilDeUsuario> lista = consultarPorIdDoUsuario(idUsuario);
		List<Perfil> perfis = new ArrayList<>();
		for (PerfilDeUsuario ligacao : lista) {
			if (ligacao.getAtivo())
				perfis.add(ligacao.getPerfil());
		}
		return perfis;
	}

	/**
	 * Retorna todos os registros que possuem 'ativo' igual a true.
	 * 
	 * @return List<PerfilDeUsuario>
	 */
	public List<PerfilDeUsuario> listarTodasLigacoesAtivas() {
		List<PerfilDeUsuario> listaCompleta = this.listar();
		List<PerfilDeUsuario> listaRegistrosAtivos = new ArrayList<PerfilDeUsuario>();
		for (PerfilDeUsuario ligacao : listaCompleta) {
			if (ligacao.getAtivo())
				listaRegistrosAtivos.add(ligacao);
		}
		return listaRegistrosAtivos;
	}

	/**
	 * Deleta um registro da tabela {@link PerfilDeUsuario} que corresponde ao
	 * 'objeto' recebido no parametro.
	 * 
	 * <p>
	 * Verifica se existe um registro com o id informado. Se existir, deleta e
	 * retorna true. Se nao existir, retorna false.
	 * 
	 * @param id int Id do objeto a ser deletado.
	 * @return boolean
	 */
	public boolean deletar(int id) {
		PerfilDeUsuario objeto = consultarPorId(id);
		if (objeto == null)
			return false;
		ligacaoDAO.deletar(objeto);
		return true;
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		ligacaoDAO.deletarTodos();
	}

	/**
	 * Altera um PerfilDeUsuario no banco de dados.
	 * 
	 * <p>
	 * Recebe um objeto do tipo {@link PerfilDeUsuario} no parametro e atualiza o
	 * registro correspondente que esta no banco de dados caso exista um objeto com
	 * o id informado. Caso exista retorna true, caso nao exista, retorna false.
	 * 
	 * @param id     int Id do objeto a ser alterado.
	 * @param objeto PerfilDeUsuario Objeto a ser atualizado no banco de dados.
	 * @return boolean
	 */
	public boolean alterar(int id, PerfilDeUsuario novo) {
		PerfilDeUsuario objeto = consultarPorId(id);
		if (objeto == null)
			return false;

		objeto.setPerfil(novo.getPerfil());
		objeto.setUsuario(novo.getUsuario());
		objeto.setDataExpiracao(novo.getDataExpiracao());
		objeto.setAtivo(novo.getAtivo());

		ligacaoDAO.alterar(objeto);
		return true;
	}

	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	public PerfilDeUsuario consultarPorId(int id) {
		return ligacaoDAO.consultarPorId(id);
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	public List<PerfilDeUsuario> listar() {
		return ligacaoDAO.listar();
	}

	/**
	 * Desativa a ligacao entre um {@link Usuario} e um {@link Perfil}.
	 * 
	 * <p>
	 * Seta o 'ativo' do 'objeto' como false e atualiza no banco de dados.
	 * 
	 * @param id int Id do registro a ser desativado.
	 * @return True caso encontre o registro no banco, false caso não encontre.
	 */
	public boolean desativar(int id) {
		PerfilDeUsuario objeto = consultarPorId(id);
		if (id == 0)
			return false;
		if (this.consultarPorId(id) != null) {
			objeto.setAtivo(false);
			this.alterar(id, objeto);
			return true;
		}
		return false;
	}

}
