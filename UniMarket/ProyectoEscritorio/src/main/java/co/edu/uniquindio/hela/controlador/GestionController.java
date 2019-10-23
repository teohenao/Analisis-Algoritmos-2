package co.edu.uniquindio.hela.controlador;

import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Controlador de GestionAdmin el cual se encarga de redireccionar al administrador al area que desee
 * @author mateo,AnaMaria
 * @version 1.0
 */
public class GestionController implements Initializable {
	
	@FXML
	private Button btnSalir;

	@FXML
	private Button btnProductos;

	@FXML
	private Button btnUsuarios;
	
	@FXML
	private Label labelNombre;
	

	private ManejadorEscenarios manejador = new ManejadorEscenarios();

	/**
	 * Action para el boton Usuarios, el cual se encarga de direccionar al administrador a la vista de usuarios
	 * @param event
	 */
	@FXML
	void usuarios(ActionEvent event) {
		manejador.cargarEscenarioUsuarios(btnUsuarios);
	}

	/**
	 * Action para el boton Productos el cual se encarga de redireccionar al administrador a la vista de productos
	 * @param event
	 */
	@FXML
	void productos(ActionEvent event) {
		manejador.cargarEscenarioProductos(btnProductos);
	}

	/**
	 * Action para el boton Salir el cual se encarga de cerrar la aplicacion
	 * @param event
	 */
	@FXML
	void salir(ActionEvent event) {
	Stage stage = (Stage) btnSalir.getScene().getWindow();
	stage.close();
		
	}


	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {
	}

}
