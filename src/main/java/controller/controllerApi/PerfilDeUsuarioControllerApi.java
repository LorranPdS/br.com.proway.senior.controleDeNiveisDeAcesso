package controller.controllerApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/perfildeusuario")
public class PerfilDeUsuarioControllerApi {
	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();

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
	@PostMapping("/atribuirPerfilAUmUsuario/usuario/{idUsuario}/perfil/{idPerfil}")
	public boolean atribuirPerfilAUmUsuario(@PathVariable Integer idUsuario, @PathVariable Integer idPerfil,
			@RequestBody String dataExpiracao) {
		try {
			return controller.atribuirPerfilAUmUsuario(idUsuario, idPerfil,
					LocalDate.parse(dataExpiracao, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
	@SuppressWarnings("unused")
	@GetMapping("/consultar/usuario/id/{id}")
	public ResponseEntity<List<PerfilDeUsuarioDTO>> consultarPorIdDoUsuario(@PathVariable("id") Integer id) {
		ArrayList<PerfilDeUsuario> listaModel = controller.consultarPorIdDoUsuario(id);
		List<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		if (listaDTO == null)
			return new ResponseEntity<List<PerfilDeUsuarioDTO>>(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok(listaDTO);

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
	@SuppressWarnings("unused")
	@GetMapping("/consultar/perfil/id/{id}")
	public ResponseEntity<List<PerfilDeUsuarioDTO>> consultarPorIdDoPerfil(@PathVariable("id") Integer id) {
		ArrayList<PerfilDeUsuario> listaModel = controller.consultarPorIdDoPerfil(id);
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		if (listaDTO == null)
			return new ResponseEntity<List<PerfilDeUsuarioDTO>>(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok(listaDTO);
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
	@SuppressWarnings("unused")
	@GetMapping("/listar/permissao/usuario/id/{id}/")
	public ResponseEntity<List<PermissaoDTO>> listarPermissoesDeUmUsuario(@PathVariable("id") int id) {
		List<Permissao> listaModel = controller.listarPermissoesDeUmUsuario(id);
		ArrayList<PermissaoDTO> listaDTO = new ArrayList<PermissaoDTO>();
		for (Permissao permissao : listaModel) {
			listaDTO.add(new PermissaoDTO(permissao));
		}
		if (listaDTO == null)
			return new ResponseEntity<List<PermissaoDTO>>(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok(listaDTO);
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
	@SuppressWarnings("unused")
	@GetMapping("/listar/perfil/usuario/id/{id}")
	public ResponseEntity<List<PerfilDTO>> listarPerfisDeUmUsuario(@PathVariable("id") int id) {
		List<Perfil> listaModel = controller.listarPerfisDeUmUsuario(id);
		ArrayList<PerfilDTO> listaDTO = new ArrayList<PerfilDTO>();
		for (Perfil perfil : listaModel) {
			listaDTO.add(new PerfilDTO(perfil));
		}
		if (listaDTO == null)
			return new ResponseEntity<List<PerfilDTO>>(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok(listaDTO);
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
	@DeleteMapping("/deletar/id/{id}")
	public boolean deletar(@PathVariable("id") int id) {
		return controller.deletar(id);
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
	@PutMapping("/alterar/{id}")
	public boolean alterar(@PathVariable("id") int id, @RequestBody PerfilDeUsuario objeto) {
		return controller.alterar(id, objeto);
	}

	/**
	 * Consulta um objeto do tipo {@link PerfilDeUsuario} no banco de dados pelo id.
	 * 
	 * @param id int Id do objeto a ser consultado.
	 */
	@GetMapping("/consultar/id/{id}")
	public PerfilDeUsuarioDTO consultarPorId(@PathVariable("id") int id) {
		return new PerfilDeUsuarioDTO(controller.consultarPorId(id));
	}

	/**
	 * Retorna uma lista contendo todos os registros da tabela
	 * {@link PerfilDeUsuario}.
	 * 
	 * @return List Lista contendo objetos do tipo {@link PerfilDeUsuario}.
	 */
	@SuppressWarnings("unused")
	@GetMapping("/listar")
	public ResponseEntity<List<PerfilDeUsuarioDTO>> listar() {
		List<PerfilDeUsuario> listaModel = controller.listar();
		ArrayList<PerfilDeUsuarioDTO> listaDTO = new ArrayList<PerfilDeUsuarioDTO>();
		for (PerfilDeUsuario ligacao : listaModel) {
			listaDTO.add(new PerfilDeUsuarioDTO(ligacao));
		}
		if (listaDTO == null)
			return new ResponseEntity<List<PerfilDeUsuarioDTO>>(HttpStatus.NOT_FOUND);

		return ResponseEntity.ok(listaDTO);
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
	@GetMapping("/usuarioPossui/permissao/id/{idPermissao}/usuario/id/{idUsuario}")
	public boolean usuarioPossuiPermissaoPara(@PathVariable("idUsuario") Integer idUsuario,
			@PathVariable("idPermissao") Integer idPermissao) {
		try {
			return controller.usuarioPossuiPermissaoPara(idUsuario, idPermissao);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
	@GetMapping("/usuarioPossui/perfil/id/{idPerfil}/usuario/id/{idUsuario}")
	public boolean usuarioPossuiOPerfil(@PathVariable("idUsuario") int idUsuario,
			@PathVariable("idPerfil") int idPerfil) {
		try {
			return controller.usuarioPossuiOPerfil(idUsuario, idPerfil);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Valida a ligacao verificando se a mesma esta ativa e possui data de expiracao
	 * posterior a data atual.
	 * 
	 * @param ligacao PerfilDeUsuario Ligacao entre usuario e perfil a ser validada.
	 * @return True caso a ligacao esteja ativa e com data posterior a data atual.
	 * @throws Exception
	 */
	@GetMapping("/estaAtivo/id/{id}")
	public boolean permissaoAtiva(@PathVariable("id") int id) {
		PerfilDeUsuario ligacao = controller.consultarPorId(id);
		try {
			return controller.permissaoAtiva(ligacao);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
