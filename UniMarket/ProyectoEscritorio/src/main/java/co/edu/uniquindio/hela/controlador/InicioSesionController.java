package co.edu.uniquindio.hela.controlador;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de InicioSesion de unimarket
 * @author mateo
 * @version 1.0
 */
public class InicioSesionController implements Initializable {

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private PasswordField txtClave;

	@FXML
	private Label labelUsuario;

	@FXML
	private Hyperlink recuperarContrasena;

	@FXML
	private Button btnRegistrarse;

	@FXML
	private TextField txtAdministrador;

	@FXML
	private Label LabelContrasena;


	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	private AdministradorDelegado delegado = manejador.getDelegado();


	/**
	 * Action para el boton iniciarSesion del administrador
	 * @param event
	 */
	@FXML
	void IniciarSesion(ActionEvent event) {

		String usuario = txtAdministrador.getText();
		String clave = txtClave.getText();

		if(delegado.aprobarIngresoAdmin(usuario, clave)) {
			manejador.cargarEscenarioMenuAdmin(btnIniciarSesion);

		} else {
			JOptionPane.showMessageDialog(null,"Datos incorrectos o usted no tiene permisos de administrador");
		}

	}

	/**
	 * Accion para el btn RecuperarContraseña, el cual consiste en enviar un correo
	 * @param event
	 * @throws MessagingException
	 */
	@FXML
	void RecuperarContrasena(ActionEvent event) throws MessagingException {
		enviarEmail("mateohr880@gmail.com");
	}



	/*
	 * enviar email---------------------------------------------------------------------------------------------
	 */
	public static void enviarEmail(String recepient) throws MessagingException {
		System.out.println("preparando mensaje");
		Properties properties = new Properties();

		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.port","587");

		String myAccountEmail = "distrifacilarmenia@gmail.com";
		String password = "41925469";

		Session session = Session.getInstance(properties,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(myAccountEmail, password);

			}
		});
		Message message = prepareMessage(session,myAccountEmail,recepient);
		Transport.send(message);
		System.out.println("mensaje enviado");
	}
	private static Message prepareMessage(Session session,String myAccountEmail,String recepient) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient) );
			message.setSubject("my first email java papa");
			message.setText("hey mundo");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {

	}


}
