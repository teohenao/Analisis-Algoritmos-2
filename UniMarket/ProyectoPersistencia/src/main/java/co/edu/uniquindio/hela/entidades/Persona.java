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
@NamedQueries({
	/**
	 * Consulta que nos permite listar todas las peronsas registradas en la base de datos, tanto admin como usuarios
	 */
	@NamedQuery(name = Persona.LISTAR_PERSONAS, query = "select p from Persona p"),
	/**
	 * Consulta que nos permite obtener la persona a la cual le pertenecen el email y clave
	 */
	@NamedQuery(name = Persona.OBTENER_PERSONA_EMAIL_CLAVE, query = "select p from Persona p where p.email =:email and p.clave =:clave")

})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public class Persona implements Serializable {

	//constante que identifica la consulta que lista todas las personas registradas
	public static final String LISTAR_PERSONAS = "ListarPersonas";
	//constante que identifica la consulta que obtiene persona por email y clave
	public static final String OBTENER_PERSONA_EMAIL_CLAVE = "ObtenerPersonaEmailClave";


	/**
	 * Atributos de la clase Persona
	 */

	/**
	 * Cedula que identifica a cada persona registrada en la aplicacion 
	 */
	@Id
	@Column(name = "cedula",length = 11,nullable = false,updatable = false)
	private String cedula;

	/**
	 * nombre completo
	 */
	@Column(name = "nombreCompleto",length = 50,nullable = false)
	private String nombreCompleto;

	/**
	 * Correo electronico
	 */
	@Column(name = "email",length = 20,nullable = false)
	private String email;

	/**
	 * Contrase�a con la cual ingresara a la aplicacion
	 */
	@Column(name = "clave",length = 10,nullable = false)
	private String clave;

	/**
	 * Direccion de la persona 
	 */
	@Column(name = "direccion",length = 20,nullable = false)
	private String direccion;

	/**
	 * numero telefonico de la persona registrada
	 */
	@Column(name="numeroTelefonico",length = 14,nullable = false)
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