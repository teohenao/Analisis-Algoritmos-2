package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Compras
 *
 */
@Entity

public class Compra implements Serializable {
	
	/**
	 * Relaciones de la entidad Compra
	 */
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	   
	@Id
	private int ref;
	private static final long serialVersionUID = 1L;

	public Compra() {
		super();
	}   
	public int getRef() {
		return this.ref;
	}

	public void setRef(int ref) {
		this.ref = ref;
	}
   
}
