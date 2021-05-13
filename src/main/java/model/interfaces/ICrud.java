package model.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sprint 3
 * @author Lucas Ivan, lucas.ivan@senior.com.br
 * @author Sarah Brito, sarah.brito@senior.com.br
 */
public interface ICrud<T> {

	public void criar(T object);

	public boolean alterar(T object);

	public boolean deletar(T object);

	public T consultarPorId(int id);

	public List<T> listar();

}
