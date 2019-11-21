package co.edu.uniquindio.hela.logica;
/**
 * Clase DTO que nos permite trabajar con consultas que arrojan objetos
 * @author Mateo Henao R, Ana Maria Latorre
 *
 */
public class TopProductosDTO {

	private int id;
	private String nombre;

	public TopProductosDTO(int id,String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "ConsultaDto [id=" + id + ", nombre=" + nombre + "]";
	}





}
