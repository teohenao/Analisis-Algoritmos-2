package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@DiscriminatorValue("Usuario")
@NamedQueries({
	
	@NamedQuery(name = Usuario.LISTAR_USUARIOS, query = "select USUARIO from Usuario usuario")

})
public class Usuario extends Persona implements Serializable {
	
	public static final String LISTAR_USUARIOS = "ListarUsuarios";


	
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
   
}
