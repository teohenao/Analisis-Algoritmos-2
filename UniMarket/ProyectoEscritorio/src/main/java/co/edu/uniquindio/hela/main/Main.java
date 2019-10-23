package co.edu.uniquindio.hela.main;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Clase main que inicializa la aplicacion de escritorio
 * @author mateo,AnaMaria
 * @version 1.0
 */
public class Main extends Application{
	
	public static void main (String[] args) {
		launch(args);
	}
	
	/**
	 * Se carga y edita el escenario inicial de la app
	 * @param primaryStage escenario de a la aplicacion
	 * @throws Exception en caso de no poder cargar el escenario
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		new ManejadorEscenarios(primaryStage);
		
	}

}
