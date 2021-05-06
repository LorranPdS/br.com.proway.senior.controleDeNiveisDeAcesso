package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.acesso.HashSenha;

public class ValidarDados {
	
	/**
	 * Criptografia para senha.
	 * 
	 * Neste metodo esta sendo utilizado uma API do java "BigInteger" para gerar um
	 * algoritmo para realizar a HASH da senha utilizando criptografia SHA-512.
	 *
	 * @param String senha
	 * @return valorCodificado
	 */
	public static String senhaDoUsuario(String senha) {
		String valorCodificado = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(senha.getBytes("utf8"));
			valorCodificado = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valorCodificado;
	}
	
	/**
	 * Recebe a String correspondente à senha real do usuário e retorna o valor codificado (hash) dessa senha.
	 * 
	 * @param senha String
	 * @return String 
	 */
	public String converterSenhaEmHashSenha(String senha) {
		return HashSenha.senhaDoUsuario(senha);
	}
	
	/**
	 * Compara a hash da senha fornecida pelo usuário com a hash salva.
	 * 
	 * @param usuarioID Integer
	 * @param senha String
	 * @return boolean
	 */
	public boolean verificarHashSenha(Integer usuarioID, String senha) {		
	
		if(this.converterSenhaEmHashSenha(senha).equals(daoUsuario.buscarUsuario(usuarioID).getHashSenhaDoUsuario())) {
			return true;
		}
		return false;
	}	
	
	/**
	 * Verifica se os endereços de email foram cadastrados corretamente ou se
	 * possuem caracteres especiais.
	 * 
	 * A variável expression relaciona os caracteres que serão buscados dentro da
	 * variável email. O método matcher() é empregado para procurar um padrão na
	 * string, retornando um objeto Matcher que contém informações sobre a pesquisa
	 * realizada.
	 * 
	 * @param String email
	 * @return isValidaEmail
	 * 
	 */	
	public static boolean validarEmail(String email) {
		boolean emailValido = false;
		if (email != null && email.length() > 0) {
			String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				emailValido = true;
			}
		}
		return emailValido;
	}
	
	/**
	 * Verifica se a senha corresponde aos pre requisitos da expressao. 
	 *
	 * @param String senha
	 * @return boolean
	 */
	
	public boolean validarSenha(String senha) {
		boolean senhaValida = false;
		if (senha != null && senha.length() > 0) {
			String expressao = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,24}";
			Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(senha);
			if (matcher.matches()) {
				senhaValida = true;
			}
		}
		return senhaValida;
	}

}
