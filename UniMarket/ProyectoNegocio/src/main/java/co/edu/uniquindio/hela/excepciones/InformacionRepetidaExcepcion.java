package co.edu.uniquindio.hela.excepciones;

/**
 * Excepcion de no encontrar informacion repetida
 * @param mensaje mensaje a mostrar en la pantalla
 * @author mateo,AnaMaria
 * @version 1.0
 */
public class InformacionRepetidaExcepcion extends Exception {

	private static final long serialVersionUID = 1L;

	public InformacionRepetidaExcepcion(String mensaje) {
		super(mensaje);
	}
	
}
