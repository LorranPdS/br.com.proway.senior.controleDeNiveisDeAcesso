package controller.controllerApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controller.controllers.PerfilDeUsuarioController;
import model.dto.PerfilDTO;
import model.dto.PerfilDeUsuarioDTO;
import model.dto.PermissaoDTO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

public class PerfilDeUsuarioControllerApi {
	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();

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
		controller.atribuirPerfilAUmUsuario(usuario, perfil, dataExpiracao);
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
	public ArrayList<PerfilDeUsuarioDTO> consultarPorIdDoUsuario(Integer id) {
		ArrayList<PerfilDeUsuario> listaModel = controller.consultarPorIdDoUsuario(id);
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		return listaDTO;
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
	public ArrayList<PerfilDeUsuarioDTO> consultarPorIdDoPerfil(Integer id) {
		ArrayList<PerfilDeUsuario> listaModel = controller.consultarPorIdDoPerfil(id);
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		return listaDTO;
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
	public ArrayList<PermissaoDTO> listarPermissoesDeUmUsuario(int idUsuario) {
		List<Permissao> listaModel = controller.listarPermissoesDeUmUsuario(idUsuario);
		ArrayList<PermissaoDTO> listaDTO = new ArrayList<PermissaoDTO>();
		for (Permissao permissao : listaModel) {
			listaDTO.add(new PermissaoDTO(permissao));
		}
		return listaDTO;
	}

	/**
	 * Retorna todas os {@link Perfil}s de um {@link Usuario}.
	 * <p>
	 * Recebe o id do {@link Usuario} a ser consultado e retorna todos os
	 * {@link Perfil} deste {@link Usuario}.
	 * 
	 * @param idUsuario int Id do {@link Usuario} a ser consultado.
	 * @return List Lista contendo todos os {@link Perfil} do {@link Usuario}.
	 */
	public List<PerfilDTO> listarPerfisDeUmUsuario(int idUsuario) {
		List<Perfil> listaModel = controller.listarPerfisDeUmUsuario(idUsuario);
		ArrayList<PerfilDTO> listaDTO = new ArrayList<PerfilDTO>();
		for (Perfil perfil : listaModel) {
			listaDTO.add(new PerfilDTO(perfil));
		}
		return listaDTO;
	}

	/**
	 * Deleta um registro da tabela {@link PerfilDeUsuario} que corresponde ao
	 * 'objeto' recebido no parametro.
	 * 
	 * @param objeto PerfilDeUsuario Objeto a ser deletado.
	 */
	public boolean deletar(PerfilDeUsuario objeto) {
		return controller.deletar(objeto);
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	public void deletarTodos() {
		controller.deletarTodos();
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
		return controller.alterar(objeto);
	}

	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	public PerfilDeUsuarioDTO consultarPorId(int id) {
		return new PerfilDeUsuarioDTO(controller.consultarPorId(id));
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	public List<PerfilDeUsuarioDTO> listar() {
		List<PerfilDeUsuario> listaModel = controller.listar();
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		return listaDTO;
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
	public boolean usuarioPossuiPermissaoPara(Usuario usuario, Permissao _permissao) {
		return controller.usuarioPossuiPermissaoPara(usuario, _permissao);
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
	public boolean usuarioPossuiOPerfil(Usuario usuario, Perfil _perfil) {
		return controller.usuarioPossuiOPerfil(usuario, _perfil);
	}

	/**
	 * Valida a ligacao verificando se a mesma esta ativa e possui data de expiracao
	 * posterior a data atual.
	 * 
	 * @param ligacao PerfilDeUsuario Ligacao entre usuario e perfil a ser validada.
	 * @return True caso a ligacao esteja ativa e com data posterior a data atual.
	 */
	public boolean permissaoAtiva(PerfilDeUsuario ligacao) {
		return controller.permissaoAtiva(ligacao);
	}
	
	

	/**
	 * Retorna todos os registros que possuem 'ativo' igual a true.
	 * 
	 * @return List<PerfilDeUsuario>
	 */
	public List<PerfilDeUsuarioDTO> listarTodasLigacoesAtivas() {
		List<PerfilDeUsuario> listaModel = controller.listarTodasLigacoesAtivas();
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		return listaDTO;
	}
	
	/**
	 * Seta o 'ativo' do registro 'ligacao' como false.
	 * @param ligacao
	 * @return
	 */
	public boolean desativar(PerfilDeUsuario ligacao) {
		return controller.desativar(ligacao);
	}
}
