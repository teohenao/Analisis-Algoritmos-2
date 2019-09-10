package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Calificacion
 *
 */
@Entity

public class Calificacion implements Serializable {
	
	/**
	 * Relaciones de la entidad Calificacion
	 */
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	   
	@Id
	private int id;
	private static final long serialVersionUID = 1L;

	public Calificacion() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
   
}