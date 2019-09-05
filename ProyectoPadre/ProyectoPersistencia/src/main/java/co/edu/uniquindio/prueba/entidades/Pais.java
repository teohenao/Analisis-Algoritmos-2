package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pais
 *
 */
@Entity
public class Pais implements Serializable {
	
	//Relacion de uno a uno con presidente, es necesario mapear una de las dos entidades
	//que esten en la relacion
	//mapped By es para referenciar al propietario de la relacion
	@OneToOne(mappedBy = "pais")
	private Presidente presidente;
	
	//Relacion de uno a muchos y debe tener una lista de la entidad relacionada
	//la relacionn de uno a muchos debe estar mapeada
	@OneToMany(mappedBy = "pais")
	private List<Departamento> departamentos;

	   
	@Id
	private String codigo;
	private String nombre;
	private static final long serialVersionUID = 1L;

	public Pais() {
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
