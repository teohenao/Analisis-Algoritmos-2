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
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.JOptionPane;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
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
	 * @throws InformacionInexistenteExcepcion 
	 */
	@FXML
	void RecuperarContrasena(ActionEvent event) throws MessagingException, InformacionInexistenteExcepcion {
		Administrador admin = delegado.buscarAdministradorEnvioCorreo(txtAdministrador.getText());
		if(txtAdministrador.getText().trim()=="") {
			JOptionPane.showMessageDialog(null,"El campo de cedula no puede ser vacio");
		}else if(admin == null) {
			JOptionPane.showMessageDialog(null,"la cedula no coincide con un administrador");
		}else {
			envioEmail(admin);
		}
		
	}


	
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 

	public static void envioEmail(Administrador u) throws AddressException, MessagingException {
 
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(u.getEmail()));
		generateMailMessage.setSubject("Ingreso UniMarket..");
		String emailBody = "hola "+u.getNombreCompleto()+" se nos informo que perdio su contraseña no se asuste." + "<br><br> Su contraseña es: "+u.getClave() +"  <br>Feliz dia y no sea tan olvidadizo :D";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
 
		transport.connect("smtp.gmail.com", "distrifacilarmenia@gmail.com", "41925469");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
	

	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {

	}

	
	

}
