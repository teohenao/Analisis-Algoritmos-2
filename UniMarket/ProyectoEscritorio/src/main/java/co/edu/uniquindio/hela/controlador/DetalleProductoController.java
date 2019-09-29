package co.edu.uniquindio.hela.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DetalleProductoController implements Initializable{
	
	public int idProductoMostrar;


	@FXML
	private Label labelPrueba;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void detallesProducto(int id) {
		idProductoMostrar = id;
		System.out.println("este es depues de ejecutar la funcion "+id);
	}

	
	
	

	
	
	
}
