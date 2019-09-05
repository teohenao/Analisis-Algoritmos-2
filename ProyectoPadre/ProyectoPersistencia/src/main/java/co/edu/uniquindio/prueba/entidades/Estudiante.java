package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Estudiante
 *
 */
@Entity

public class Estudiante implements Serializable {
	
	//Relacion de muchos a muchos con materia,  materia es clase propietaria
	@ManyToMany(mappedBy = "estudiantes")
	private List<Materia> materias;
	

	   
	@Id
	private String cedula;
	private String nota;
	private static final long serialVersionUID = 1L;

	public Estudiante() {
		super();
	}   
	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}   
	public String getNota() {
		return this.nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}
   
}
