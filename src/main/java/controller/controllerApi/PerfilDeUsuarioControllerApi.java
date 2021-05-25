package controller.controllerApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import controller.controllers.PerfilDeUsuarioController;
import model.dto.PerfilDTO;
import model.dto.PerfilDeUsuarioDTO;
import model.dto.PermissaoDTO;
import model.entidades.Perfil;
import model.entidades.PerfilDeUsuario;
import model.entidades.Permissao;
import model.entidades.Usuario;

/**
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
@RestController
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
	@PostMapping("/atribuirPerfilAUmUsuario")
	public void atribuirPerfilAUmUsuario(@RequestBody Usuario usuario, @RequestBody Perfil perfil,
			LocalDate dataExpiracao) {
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
	@GetMapping("/consultarPorIdDoUsuario/{id}")
	public ArrayList<PerfilDeUsuarioDTO> consultarPorIdDoUsuario(@PathVariable("id") Integer id) {
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
	@GetMapping("/consultarPorIdDoPerfil/{id}")
	public ArrayList<PerfilDeUsuarioDTO> consultarPorIdDoPerfil(@PathVariable("id") Integer id) {
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
	@GetMapping("/listarPermissoesDeUmUsuario/{id}")
	public ArrayList<PermissaoDTO> listarPermissoesDeUmUsuario(@PathVariable("id") int id) {
		List<Permissao> listaModel = controller.listarPermissoesDeUmUsuario(id);
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
	@GetMapping("/listarPerfisDeUmUsuario/{id}")
	public List<PerfilDTO> listarPerfisDeUmUsuario(@PathVariable("id") int id) {
		List<Perfil> listaModel = controller.listarPerfisDeUmUsuario(id);
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
	 * @param id int Id do objeto a ser deletado.
	 */
	@DeleteMapping("/deletar/{id}")
	public boolean deletar(@PathVariable("id") int id) {
		return controller.deletar(id);
	}

	/**
	 * Deleta todos os registros da tabela {@link PerfilDeUsuario}.
	 */
	@DeleteMapping("/deletarTodos")
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
	 * @param id     int Id do objeto a ser alterado.
	 * @param objeto PerfilDeUsuario Objeto a ser atualizado no banco de dados.
	 * @boolean Retorna true.
	 */
	@PutMapping("/alterar/{id}")
	public boolean alterar(@PathVariable("id") int id, @RequestBody PerfilDeUsuario objeto) {
		return controller.alterar(id, objeto);
	}

	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	@GetMapping("/consultarPorId/{id}")
	public PerfilDeUsuarioDTO consultarPorId(@PathVariable("id") int id) {
		return new PerfilDeUsuarioDTO(controller.consultarPorId(id));
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	@GetMapping("/listar")
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
	@GetMapping("/usuarioPossuiPermissaoPara")
	public boolean usuarioPossuiPermissaoPara(int idUsuario, int idPermissao) {
		return controller.usuarioPossuiPermissaoPara(idUsuario, idPermissao);
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
	@GetMapping("/usuarioPossuiOPerfil")
	public boolean usuarioPossuiOPerfil(int idUsuario, int idPermissao) {
		return controller.usuarioPossuiOPerfil(idUsuario, idPermissao);
	}

	/**
	 * Valida a ligacao verificando se a mesma esta ativa e possui data de expiracao
	 * posterior a data atual.
	 * 
	 * @param ligacao PerfilDeUsuario Ligacao entre usuario e perfil a ser validada.
	 * @return True caso a ligacao esteja ativa e com data posterior a data atual.
	 */
	@GetMapping("/permissaoAtiva")
	public boolean permissaoAtiva(PerfilDeUsuario ligacao) {
		return controller.permissaoAtiva(ligacao);
	}

	/**
	 * Retorna todos os registros que possuem 'ativo' igual a true.
	 * 
	 * @return List<PerfilDeUsuario>
	 */
	@GetMapping("/listarTodasLigacoesAtivas")
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
	 * 
	 * @param id int Id do objeto a ser desativado.
	 * @return
	 */
	@PutMapping("/desativar/{id}")
	public boolean desativar(@PathVariable("id") int id) {
		return controller.desativar(id);
	}
}
