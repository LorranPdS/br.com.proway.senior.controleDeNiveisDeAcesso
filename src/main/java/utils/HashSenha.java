package utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Classe HashSenha
 * 
 * Classe responsavel pela conversao da senha em hashSenha
 * 
 * Sprint 3
 * @author Leonardo Pereira, leonardo.pereira@senior.com.br
 * 
 */

public class HashSenha {

	
	/**
	 * Criptografia para senha.
	 * 
	 * Neste metodo esta sendo utilizado uma API do java "BigInteger" para gerar um
	 * algoritmo para realizar a HASH da senha utilizando criptografia SHA-512.
	 *
	 * @param String senhaNaoCriptografada
	 * @return senhaCriptografada
	 */
	public static String criptografarSenha(String senhaNaoCriptografada) {
		String senhaCriptografada = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(senhaNaoCriptografada.getBytes("utf8"));
			senhaCriptografada = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return senhaCriptografada;
	}

}
