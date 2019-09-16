package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad Usuario, Clase hija de Persona
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Usuario")
@NamedQueries({
	/**
	 * Consulta la cual permite listar todos los usuarios registrados en la base de datos
	 */
	@NamedQuery(name = Usuario.LISTAR_USUARIOS, query = "select USUARIO from Usuario usuario")

})
public class Usuario extends Persona implements Serializable {
	
	//constante que identifica la consulta que lista todos los usuarios
	public static final String LISTAR_USUARIOS = "ListarUsuarios";


	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
   
}
