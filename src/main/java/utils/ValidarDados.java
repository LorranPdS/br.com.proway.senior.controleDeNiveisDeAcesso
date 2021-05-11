package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidarDados {
		
	/**
	 * Verifica se os endere�os de email foram cadastrados corretamente ou se
	 * possuem caracteres especiais.
	 * 
	 * A vari�vel expression relaciona os caracteres que ser�o buscados dentro da
	 * vari�vel email. O m�todo matcher() � empregado para procurar um padr�o na
	 * string, retornando um objeto Matcher que cont�m informa��es sobre a pesquisa
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

	public static boolean validarSenha(String senha) {
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
