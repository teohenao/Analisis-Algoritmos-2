package co.edu.uniquindio.hela.excepciones;

/**
 * Excepcion cuando no se encuentra una entidad con la informacion dada
 * @param mensaje
 * @author mateo,AnaMaria
 * @version 1.0
 */
public class InformacionInexistenteExcepcion extends Exception{

	
	private static final long serialVersionUID = 1L;

	public InformacionInexistenteExcepcion(String mensaje) {
		super(mensaje);
		
	}
		
}
