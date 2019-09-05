package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Marca
 *
 */
@Entity

public class Marca implements Serializable {
	
	@OneToMany(mappedBy = "marca")
	private List<Automovil> automoviles;

	   
	@Id
	private String codigo;
	private String nombre;
	private static final long serialVersionUID = 1L;

	public Marca() {
		super();
	}   
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
   
}
