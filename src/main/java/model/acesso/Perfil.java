package model.acesso;

/**
 * Classe Perfil
 * 
 * Define os atributos necessarios para instanciar um perfil, o que e
 * constitui­do por permissoes.
 * 
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 * 
 * @author Sprint 4
 * @author Elton Oliveira, elton.oliveira@senior.com.br
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Thiago Barbieri, thiago.barbieri@senior.com.br
 * @author Vitor Goncalves, vitor.goncalves@senior.com.br
 * @author Vitor Gehrke, vitor.gehrke@senior.com.br
 */
public class Perfil {

	private Integer id_perfil;
	private String nome_perfil;
	
	public Perfil(Integer id_perfil, String nome_perfil) {
		this.id_perfil = id_perfil;
		this.nome_perfil = nome_perfil;
	}

	public Integer getId_perfil() {
		return id_perfil;
	}

	public void setId_perfil(Integer id_perfil) {
		this.id_perfil = id_perfil;
	}

	public String getNome_perfil() {
		return nome_perfil;
	}

	public void setNome_perfil(String nome_perfil) {
		this.nome_perfil = nome_perfil;
	}

	@Override
	public String toString() {
		return "Perfil [id_perfil=" + id_perfil + ", nome_perfil=" + nome_perfil + "]";
	}
}
