package controller;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import db.DBConnection;
import model.acesso.PerfilDeUsuario;
import model.acesso.UsuarioPerfilId;
import model.dao.PerfilDAO;
import model.dao.UsuarioDAO;
import model.entidades.Perfil;
import model.entidades.Usuario;

public class PerfilDeUsuarioControllerTest {

	PerfilDeUsuarioController controller = new PerfilDeUsuarioController();
	
	@Test
	public void testAtribuirPerfilAUmUsuario() {
		Usuario usuario = new Usuario("thiago@gmail.com", "admin");
		UsuarioDAO.getInstance().criar(usuario);
		
		Perfil perfil = PerfilDAO.getInstance().consultarPorNome("Vendedor");
		controller.atribuirPerfilAUmUsuario(usuario, perfil);
		assertEquals()

	}

	@Test
	public void testGRemoverPerfilDeUmUsuarioEListarPerfis() {
		Usuario usuario = new Usuario("UsuarioTesteDeRemocaoDePerfil@gmail.com", "senha6");
		UsuarioDAO.getInstance().criar(usuario);
		Perfil perfil1 = new Perfil("PerfilAtribuido3");
		PerfilDAO.getInstance().criar(perfil1);
		LocalDate dateDeExpiracao = LocalDate.of(2021, 05, 19);
		LocalDate dateDeExpiracao2 = LocalDate.of(2021, 05, 17);

		Integer idUsuario = usuario.getIdUsuario();
		UsuarioPerfilId PK1 = new UsuarioPerfilId(idUsuario, perfil1.getIdPerfil());
		PerfilDeUsuario UP1 = new PerfilDeUsuario(PK1, usuario, perfil1, dateDeExpiracao);
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(UP1);

		DBConnection.shutdown();

		Perfil perfil2 = new Perfil("PerfilAtribuido4");
		PerfilDAO.getInstance().criar(perfil2);
		usuario = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeRemocaoDePerfil@gmail.com");
		idUsuario = usuario.getIdUsuario();
		UsuarioPerfilId PK2 = new UsuarioPerfilId(idUsuario, perfil2.getIdPerfil());
		PerfilDeUsuario UP2 = new PerfilDeUsuario(PK2, usuario, perfil2, dateDeExpiracao2);
		UsuarioDAO.getInstance().atribuirPerfilAUmUsuario(UP2);

		DBConnection.shutdown();

		UsuarioDAO.getInstance().removerPerfilDeUmUsuario(perfil1.getIdPerfil(), usuario.getIdUsuario());
		DBConnection.shutdown();
		usuario = UsuarioDAO.getInstance().consultarPorLogin("UsuarioTesteDeRemocaoDePerfil@gmail.com");
		
		assertFalse(usuario.getPerfis().contains(UP1));
	}

}
