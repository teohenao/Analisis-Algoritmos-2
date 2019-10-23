package co.edu.uniquindio.hela.logica;
/**
 * DTO producto
 * @author mateo
 * @version 1.0
 */
public class ConsultaProductosDTO {

	private String nombre;
	private String descripcion;
	
	public ConsultaProductosDTO(String nombre,String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
