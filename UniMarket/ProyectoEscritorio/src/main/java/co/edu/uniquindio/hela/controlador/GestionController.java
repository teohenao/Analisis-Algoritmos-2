package co.edu.uniquindio.hela.controlador;

import com.sun.xml.rpc.encoding.Initializable;
import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;

import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GestionController implements Initializable {


	@FXML
	private Button btnSalir;

	@FXML
	private Button btnProductos;

	@FXML
	private Button btnUsuarios;
	
	@FXML
	private Label labelNombre;
	
	@FXML
	private Button btnCerrarSesion;

	private ManejadorEscenarios manejador = new ManejadorEscenarios();

	@FXML
	void usuarios(ActionEvent event) {
		manejador.cargarEscenarioUsuarios(btnUsuarios);
	}


	@FXML
	void productos(ActionEvent event) {
		manejador.cargarEscenarioProductos(btnProductos);
	}

	@FXML
	void salir(ActionEvent event) {
	Stage stage = (Stage) btnSalir.getScene().getWindow();
	stage.close();
		
	}

	@FXML
	void cerrarSesion(ActionEvent event) {

	}

	public void setManejador(ManejadorEscenarios manejador) {
		this.manejador = manejador;
	}

	@Override
	public void initialize(InternalTypeMappingRegistry arg0) throws Exception {
		// TODO Auto-generated method stub

	}

}
