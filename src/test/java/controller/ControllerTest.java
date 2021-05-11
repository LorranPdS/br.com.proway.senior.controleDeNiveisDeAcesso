package controller;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void testEmail() throws Exception {
		Controller c = new Controller();
		c.enviarEmailDeConfirmacaoDeLogin("lorransantospereira16@gmail.com");
	}

}
