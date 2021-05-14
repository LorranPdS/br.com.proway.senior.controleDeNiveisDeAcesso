package utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private String loginRemetente = "niveisDeAcessoGoDev@gmail.com";
	private String senhaRemetente = "NiveisDeAcessoGrupo3";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	public Email(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public boolean enviarEmail() {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.connectiontimeout", "1000");
			properties.put("mail.smtp.timeout", "5000");
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true"); // Autoriza��o
			properties.put("mail.smtp.starttls", "true"); // Autentica��o
			properties.put("mail.smtp.host", "smtp.gmail.com"); // Servidor Gmail
			properties.put("mail.smtp.port", "465"); // Porta do servidor
			properties.put("mail.smtp.socketFactory.port", "465"); // Especifica a porta a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conex�o
																								// ao SMTP

			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(loginRemetente, senhaRemetente);
				}
			});

			Address[] toUser = InternetAddress.parse(listaDestinatarios);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(loginRemetente, nomeRemetente));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assuntoEmail);
			message.setText(textoEmail);

			System.out.println("Enviando...");
			Transport.send(message);
			System.out.println("Mensagem enviada com sucesso!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Falha no envio!");
			return false;
		}
		

	}
}
