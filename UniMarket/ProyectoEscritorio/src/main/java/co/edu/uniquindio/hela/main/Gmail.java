package co.edu.uniquindio.hela.main;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Gmail {
private static final Gmail instancia = new Gmail();
	
	public static Gmail getInstancia() {
		return instancia;
	}

	public boolean enviarcorreo(String de, String clave, String para, String mensaje, String asunto) {
		boolean enviado = false;
		try {
			//587 ò 465 port 
			//select p, c.valor From producto p left join p.calificaciones c
			String host = "smtp.gmail.com";

			Properties prop = System.getProperties();

			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.user", "distrifacilarmenia@gmail.com");
			prop.put("mail.smtp.password", "41925469");
			prop.put("mail.smtp.port", "465");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.ssl.trust", "*");

			Session session = Session.getDefaultInstance(prop, null);
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(de));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
			message.setSubject(asunto);
			message.setText(mensaje);

			System.out.print(message);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, de, clave);

			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			enviado = true;
			System.out.print(enviado);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return enviado;

	}

}
