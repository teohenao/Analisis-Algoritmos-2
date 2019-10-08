package co.edu.uniquindio.hela.utilidades;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Utilidades {
	
	/**
	 * permite mostrar un texto informativo en pantalla
	 * @param titulo subtitulo de la alerta
	 * @param mensaje mensaje principal
	 */
	public static void mostrarMensaje( String titulo, String mensaje ) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("UniMarket");
		alert.setHeaderText(titulo);
		alert.setContentText(mensaje);
		alert.showAndWait();	
	}

}
