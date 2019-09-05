package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Mateo Henao R
 * Entity implementation class for Entity: Persona
 *
 */
@Entity

public class Persona implements Serializable {


	@Id
	private String cedula;
	//restricciones o caracteristicas del campo en la table
	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;
	private String apellido;

	//campo de fecha el cual usa un atributo temporal que es necesario para capturar la fecha con TIMESTAMP
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaNacimiento;

	//enum de genero de la persona	
	private Genero genero;

	//ElementCollectionse crea una tabla auxiliar por la cantidad de numeros que guarda
	@ElementCollection
	//el Map es para asignar una clave los atributos de una lista	
	private Map <String, String> numerosTelefono;


	private static final long serialVersionUID = 1L;



	public Persona() {
		super();
	}   
	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	//	Generar HastCode and Equals


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	public Map<String, String> getNumerosTelefono() {
		return numerosTelefono;
	}
	public void setNumerosTelefono(Map<String, String> numerosTelefono) {
		this.numerosTelefono = numerosTelefono;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}




}
