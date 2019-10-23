package co.edu.uniquindio.hela.controlador;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import co.edu.uniquindio.hela.utilidades.Utilidades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador de InicioSesion de unimarket
 * @author mateo,AnaMaria
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
	 * Accion para el btn RecuperarContraseña, el cual consiste en enviar un correo al administrador
	 * @param event
	 * @throws MessagingException
	 * @throws InformacionInexistenteExcepcion 
	 */
	@FXML
	void RecuperarContrasena(ActionEvent event) throws MessagingException, InformacionInexistenteExcepcion {
		Administrador admin = delegado.buscarAdministradorPorCedula(txtAdministrador.getText());
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
	/**
	 * Metodo que permite enviar un correo electronico
	 * @param u, administrador al cual se desea enviar el email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void envioEmail(Administrador u) throws AddressException, MessagingException {
 
		System.out.println("\n 1st ===> configurando propiedades de mailServer..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Propiedades de mailServer configuradas..");
 
		System.out.println("\n\n 2nd ===> enviando email Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(u.getEmail()));
		generateMailMessage.setSubject("Ingreso UniMarket..");
		String emailBody = "hola "+u.getNombreCompleto()+" se nos informo que perdio su contraseña no se asuste." + "<br><br> Su contraseña es: "+u.getClave() +"  <br>Feliz dia y no sea tan olvidadizo :D";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session creado satisfactoriamente..");
 
		System.out.println("\n\n 3rd ===> Obteniendo session y enviando email");
		Transport transport = getMailSession.getTransport("smtp");
 
		transport.connect("smtp.gmail.com", "distrifacilarmenia@gmail.com", "41925469");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
		Utilidades.mostrarMensaje("Envio del correo exitoso", "por favor ingrese a su correo electronico para recuperar la clave");
	}
	

	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {

	}

}
