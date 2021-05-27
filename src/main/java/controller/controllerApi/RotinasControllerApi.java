package controller.controllerApi;

import org.springframework.web.bind.annotation.RestController;

import controller.controllers.RotinasController;
import model.entidades.PerfilDeUsuario;

/**
 * Classe que possui metodos que serao executados com uma frequencia
 * pre-determinada.
 * 
 * @author Janaina Mai <b>janaina.mai@senior.com</b> - Sprint 6
 *
 */
@RestController
public class RotinasControllerApi {
	RotinasController controller = new RotinasController();

	/**
	 * 
	 * Desativa todas as permissoes expiradas.
	 * 
	 * Lista todos os registros da tabela {@link PerfilDeUsuario} e seta 'ativo'
	 * como 'false' aqueles que possuem a data de expiracao anterior a data atual.
	 * 
	 * @return Integer Quantidade de permissoes expiradas deletadas.
	 */
	public int desativarTodasPermissoesExpiradas() {
		return controller.desativarTodasPermissoesExpiradas();
	}
}
