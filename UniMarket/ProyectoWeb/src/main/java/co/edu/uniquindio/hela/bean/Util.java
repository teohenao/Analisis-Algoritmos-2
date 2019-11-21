package co.edu.uniquindio.hela.bean;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Clase Util que almacena lo que puede ocasionar malas practicas
 * @author mateo,AnaMaria
 * @version 1.0
 */
public class Util {
	
	public static void mostrarMensaje(Severity tipo, String mensaje) {
		FacesMessage faceMsg = new FacesMessage(tipo, mensaje, mensaje);
		FacesContext.getCurrentInstance().addMessage(null, faceMsg);
	}

}
