package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Clase padre de usuario y administrador
 * @version 1.0
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Persona implements Serializable {
	
	/**
	 * Atributos de la clase Persona
	 */
	
	/**
	 * Cedula que identifica a cada persona registrada en la aplicacion 
	 */
	@Id
	@Column(name = "cedula")
	private String cedula;
	
	/**
	 * nombre completo
	 */
	@Column(name = "nombreCompleto")
	private String nombreCompleto;
	
	/**
	 * Correo electronico
	 */
	@Column(name = "email")
	private String email;
	
	/**
	 * Contraseņa con la cual ingresara a la aplicacion
	 */
	@Column(name = "clave")
	private String clave;
	
	/**
	 * Direccion de la persona 
	 */
	@Column(name = "direccion")
	private String direccion;
	
	/**
	 * numero telefonico de la persona registrada
	 */
	@Column(name="numeroTelefonico")
	private String numeroTelefonico;
	
	
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
	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}   
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}   
	public String getNumeroTelefonico() {
		return this.numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cedula == null) ? 0 : cedula.hashCode());
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
		if (cedula == null) {
			if (other.cedula != null)
				return false;
		} else if (!cedula.equals(other.cedula))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Persona [cedula=" + cedula + ", nombreCompleto=" + nombreCompleto + ", email=" + email + ", clave="
				+ clave + ", direccion=" + direccion + ", numeroTelefonico=" + numeroTelefonico + "]";
	}
   
}
