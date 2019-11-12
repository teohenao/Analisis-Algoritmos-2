package co.edu.uniquindio.hela.controlador;

import javax.mail.MessagingException;
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
			if(delegado.envioEmail(admin)) {
				Utilidades.mostrarMensaje("Envio del correo exitoso", "por favor ingrese a su correo electronico para recuperar la clave");
			}else{
				Utilidades.mostrarMensaje("Fallo el envio de correo", "por favor despida el programador");
				 }
		}
		
	}


	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {

	}

}
