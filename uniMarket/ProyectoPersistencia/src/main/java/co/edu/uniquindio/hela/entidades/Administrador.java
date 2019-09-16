package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad Administrador que hereda los atributos de una persona
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Administrador")
@NamedQueries({
	/**
	 *	Consulta que nos permite listar todos los administradores de la base de datos
	 */
	@NamedQuery(name = Administrador.LISTAR_ADMINISTRADORES, query = "select ADMINISTRADOR from Administrador administrador")

})
public class Administrador extends Persona implements Serializable {
	
	//Constante que identifica la consulta de listar todos los usuarios
	public static final String LISTAR_ADMINISTRADORES = "ListarAdministradores";

	
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}
   
}
