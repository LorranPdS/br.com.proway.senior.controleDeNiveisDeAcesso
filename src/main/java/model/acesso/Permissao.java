package model.acesso;

/**
 * Classe Permissao
 * 
 * Recebe os atributos necessarios para instanciar uma permissao, que sera
 * vinculada a um perfil.
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
public class Permissao {

	private Integer id_permissao;
	private String nome_permissao;

	public Permissao(Integer id_permissao, String nome_permissao) {
		this.id_permissao = id_permissao;
		this.nome_permissao = nome_permissao;
	}

	public Integer getId_permissao() {
		return id_permissao;
	}

	public void setId_permissao(Integer id_permissao) {
		this.id_permissao = id_permissao;
	}

	public String getNome_permissao() {
		return nome_permissao;
	}

	public void setNome_permissao(String nome_permissao) {
		this.nome_permissao = nome_permissao;
	}

	@Override
	public String toString() {
		return "Permissao [id_permissao=" + id_permissao + ", nome_permissao=" + nome_permissao + "]";
	}

}
