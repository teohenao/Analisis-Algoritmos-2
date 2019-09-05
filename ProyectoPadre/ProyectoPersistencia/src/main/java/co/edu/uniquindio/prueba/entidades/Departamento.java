package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Departamento
 *
 */
@Entity

public class Departamento implements Serializable {
	
	//Relacion de uno a muchos
	//relacion de muchos a uno, debe estar mapeada la relacion de uno a muchos
	//La entidad propietaria en esta relacion es departamento
	@ManyToOne
	private Pais pais;

	   
	@Id
	private String codigo;
	private String nombre;
	private String ubicacion;
	private static final long serialVersionUID = 1L;

	public Departamento() {
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
	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
   
}
