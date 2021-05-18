package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Classe HashSenha
 * 
 * Classe responsavel pela conversao da senha em hashSenha.
 * 
 * @author Sprint 5
 * 
 * @author TODO
 * 
 */

public class HashSenha {

	/**
	 * Criptografia para senha.
	 * 
	 * Neste metodo esta sendo utilizado uma API do java para gerar um algoritmo
	 * para realizar a HASH da senha utilizando criptografia PBKDF2 com SHA1.
	 *
	 * @param login
	 * @param String senhaNaoCriptografada
	 * @return senhaCriptografada
	 */
	public static String criptografarSenha(String login, String senhaNaoCriptografada) {
		try {

			byte[] salt = criptografarSalt(login);

			KeySpec spec = new PBEKeySpec(senhaNaoCriptografada.toCharArray(), salt, 1000000, 256);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();

			Base64.Encoder enc = Base64.getEncoder();
			return enc.encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		} catch (InvalidKeySpecException e) {
			e.getMessage();
		}
		return null;
	}

	/**
	 * Criptografia para Salt.
	 * 
	 * Neste metodo esta sendo utilizado uma API do java "BigInteger" para gerar um
	 * algoritmo para realizar a HASH do Salt utilizando criptografia SHA-512.
	 *
	 * @param String login
	 * @return saltCriptografado
	 */
	private static byte[] criptografarSalt(String login) {
		String stringDoSalt = "O login desse usuário é: " + login + "!";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(stringDoSalt.getBytes("utf8"));
			String saltCriptografado = String.format("%064x", new BigInteger(1, digest.digest()));
			return saltCriptografado.getBytes();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
