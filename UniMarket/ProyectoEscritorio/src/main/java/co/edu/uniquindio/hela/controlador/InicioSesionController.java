package co.edu.uniquindio.hela.controlador;
import javax.swing.JOptionPane;
import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.main.Gmail;
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



	@FXML
	void IniciarSesion(ActionEvent event) {

		String usuario = txtAdministrador.getText();
		String clave = txtClave.getText();

		if(delegado!=null) {

			if(delegado.aprobarIngresoAdmin(usuario, clave)) {
				manejador.cargarEscenarioMenuAdmin(btnIniciarSesion);
				
			} else {
				JOptionPane.showMessageDialog(null,"Datos incorrectos o no tiene permisos de administrador :D");
			}
		}
	}

	@FXML
	void RecuperarContrasena(ActionEvent event) {
		Administrador administrador = new Administrador();
		try {
			administrador = delegado.buscarAdministradorEnvioCorreo(txtAdministrador.getText());
			
	    	
	    	if(administrador!=null) {
	    		
	    			Gmail.getInstancia().enviarcorreo("mateohenao743@gmail.com","anamaria95", administrador.getEmail(), 
	    					"HERBARIO"+"\n\n"+"Tu solicitud para recuperación de contraseña ah sido exitosa"+"\n"
	    					 +"Nueva Contraseña: "+administrador.getClave(),
	    					"RECUPERACIÓN CONTRASEÑA");
	    			JOptionPane.showMessageDialog(null, "Hemos notificado a tu direccón de correo electronico tu nueva Contraseña");
	    		
	    	}
		} catch (InformacionInexistenteExcepcion e) {
    		Utilidades.mostrarMensaje("ERROR", "EL ADMINISTRADOR NO EXISTE");
		}catch (Exception e) {
    		Utilidades.mostrarMensaje("ERROR", "EL ADMINISTRADOR NO EXISTE");
		}

	
	}


	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {
		// TODO Auto-generated method stub

	}


}
