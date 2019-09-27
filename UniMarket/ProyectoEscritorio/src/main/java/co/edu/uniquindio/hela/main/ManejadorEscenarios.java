package co.edu.uniquindio.hela.main;
import java.io.IOException;

import co.edu.uniquindio.hela.controlador.GestionController;
import co.edu.uniquindio.hela.controlador.ProductosController;
import co.edu.uniquindio.hela.controlador.UsuariosController;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ManejadorEscenarios {

	/**
	 * contenedor prinpipal de la aplicacion
	 */
	private Stage escenario;
	public Parent root ;

	/**
	 * conexion con capa de negocio
	 */
	private AdministradorDelegado administradorDelegado;

	public ManejadorEscenarios() {
		administradorDelegado = AdministradorDelegado.administradorDelegado;
	}
	public AdministradorDelegado getDelegado () {
		return administradorDelegado;
	}


	/**
	 * Se encarga de mostrar la pantalla inicial o Login de unimarket
	 * @param escenario
	 */
	public ManejadorEscenarios(Stage escenario) {
		this.escenario = escenario;
		administradorDelegado = AdministradorDelegado.administradorDelegado;

		try {
			escenario.setTitle("UniMarket");
			Parent root = FXMLLoader.load(getClass().getResource("../vista/InicioSesionAdmin.fxml"));
			Scene scene = new Scene(root);
			escenario.setResizable(false);
			escenario.centerOnScreen();
			escenario.setScene(scene);
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Se encarga de mostrar el escenario del menu principal para el administrador
	 * @param IniciarSesion
	 */
	public void cargarEscenarioMenuAdmin(Button IniciarSesion) {

		administradorDelegado = AdministradorDelegado.administradorDelegado;
		
		try {
			//se carga la interfaz
			FXMLLoader loader = new FXMLLoader();
			
			//Obtener referencia a la Escena del botón         
			escenario = (Stage) IniciarSesion.getScene().getWindow();
			
			//cargar el otro documento, en este caso la segundo pantalla
			Parent root = FXMLLoader.load(getClass().getResource("../vista/Inicio.fxml"));
			GestionController gestionController = (GestionController)loader.getController();
			loader.setController(gestionController);
			Scene scene = new Scene(root);
			escenario.setScene(scene);
			escenario.setResizable(false);
			escenario.centerOnScreen();
			escenario.setTitle("Bienvenido");
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Se encarga de mostrar el escenario de Usuarios registrados en la base de datos para el administrador
	 * @param usuarios
	 */
	public void cargarEscenarioUsuarios(Button usuarios) {

		administradorDelegado = AdministradorDelegado.administradorDelegado;

		try {
			FXMLLoader loader = new FXMLLoader();			
			escenario=(Stage) usuarios.getScene().getWindow();
			root = loader.load(getClass().getResource("../vista/Usuarios.fxml").openStream());
			root.getStylesheets().add(getClass().getResource("../utilidades/styles.css").toString());
			UsuariosController usuariosController = (UsuariosController)loader.getController();
			loader.setController(usuariosController);
			Scene scene = new Scene(root);
			escenario.setScene(scene);
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Se encarga de mostrar el escenario de Productos registrados en la base de datos para el administrador
	 * @param Productos
	 */
	public void cargarEscenarioProductos(Button productos) {

		administradorDelegado = AdministradorDelegado.administradorDelegado;

		try {
			FXMLLoader loader = new FXMLLoader();			
			escenario=(Stage) productos.getScene().getWindow();
			root = loader.load(getClass().getResource("../vista/Productos.fxml").openStream());
			root.getStylesheets().add(getClass().getResource("../utilidades/styles.css").toString());
			ProductosController productosController = (ProductosController)loader.getController();
			loader.setController(productosController);
			Scene scene = new Scene(root);
			escenario.setScene(scene);
			escenario.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



}
