package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.dao.PerfilDeUsuarioDAO;
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
	 * Metodo responsavel por atribuir um {@link Perfil} a um {@link Usuario}.
	 * 
	 * @param usuario Usuario {@link Usuario} que recebera um perfil de acesso.
	 * @param perfil  Perfil {@link Perfil} que sera atribuido a um usuario.
	 * @throws Exception - Caso a atribuição do {@link Perfil} ao {@link Usuario}
	 *                   não seja possivel.
	 */
	public void atribuirPerfilAUmUsuario(Usuario usuario, Perfil perfil, LocalDate dataExpiracao) {
		PerfilDeUsuario perfilDeUsuario = new PerfilDeUsuario(usuario, perfil, dataExpiracao, true);
		ligacaoDAO.criar(perfilDeUsuario);
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
	public ArrayList<Permissao> listarPermissoesDeUmUsuario(int idUsuario) {

		Set<Permissao> listaSet = ligacaoDAO.listarPermissoesDeUmUsuario(idUsuario);
		ArrayList<Permissao> permissoes = new ArrayList<Permissao>();
		for (Permissao permissao : listaSet)
			permissoes.add(permissao);
		return permissoes;
	}

	/**
	 * Verifica se o {@link Usuario} 'usuario' possui a {@link Permissao} recebida
	 * no parametro.
	 * 
	 * <p>
	 * Busca todos os registros da tabela {@link PerfilDeUsuario} os quais possuam a
	 * data de expiracao valida. Pega as permissoes desses perfis validos e verifica
	 * se alguma destas eh igual a permissao recebida no parametro.
	 * 
	 * @return Retorna true caso ele possua um perfil ativo que possua a 'permissao'
	 *         recebida no parametro.
	 */
	public Boolean usuarioPossuiPermissaoPara(Usuario usuario, Permissao _permissao) {
		List<PerfilDeUsuario> ligacoes = consultarPorIdDoUsuario(usuario.getIdUsuario());
		for (PerfilDeUsuario ligacao : ligacoes) {
			if (!ligacao.getDataExpiracao().isBefore(LocalDate.now())) {
				List<Permissao> permissoes = ligacao.getPerfil().getPermissoes();
				for (Permissao permissao : permissoes) {
					if (permissao.getNomePermissao() == _permissao.getNomePermissao())
						return true;
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
	 * Chama o metodo listarPerfisAtivosDeUmUsuario para percorrer todos os perfis
	 * ativos do usuario recebido no parametro. Verifica se algum destes perfis eh
	 * igual ao perfil recebido no parametro.
	 * 
	 * @return Retorna true caso encontre um perfil ativo igual ao perfil recebido
	 *         no parametro.
	 */
	public Boolean usuarioPossuiOPerfil(Usuario usuario, Perfil _perfil) {
		List<Perfil> perfis = listarPerfisAtivosDeUmUsuario(usuario.getIdUsuario());
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
	 */
	public Boolean permissaoAtiva(PerfilDeUsuario ligacao) {
		if (ligacao.getAtivo() && ligacao.getDataExpiracao().isAfter(LocalDate.now()))
			return true;
		return false;
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
		return ligacaoDAO.listarPerfisDeUmUsuario(idUsuario);
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
		return ligacaoDAO.listarPerfisAtivosDeUmUsuario(idUsuario);
	}

	/**
	 * Deleta um registro da tabela {@link PerfilDeUsuario} que corresponde ao
	 * 'objeto' recebido no parametro.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser deletado.
	 */
	public boolean deletar(PerfilDeUsuario objeto) {
		return ligacaoDAO.deletar(objeto);
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		ligacaoDAO.deletarTodos();
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
		return ligacaoDAO.alterar(objeto);
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
}
