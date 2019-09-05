package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Materia
 *
 */
@Entity

public class Materia implements Serializable {
	
	//Materia hace de propietaria de la relacion de muchos a muchos con estudiante
	@ManyToMany
	private List<Estudiante> estudiantes;

	   
	@Id
	private String codigo;
	private String nombre_materia;
	private static final long serialVersionUID = 1L;

	public Materia() {
		super();
	}   
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}   
	public String getNombre_materia() {
		return this.nombre_materia;
	}

	public void setNombre_materia(String nombre_materia) {
		this.nombre_materia = nombre_materia;
	}
   
}
