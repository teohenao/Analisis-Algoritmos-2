package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrador
 *
 */
@Entity
@DiscriminatorValue("Administrador")
@NamedQueries({
	
	@NamedQuery(name = Administrador.LISTAR_ADMINISTRADORES, query = "select ADMINISTRADOR from Administrador administrador")

})
public class Administrador extends Persona implements Serializable {
	
	public static final String LISTAR_ADMINISTRADORES = "ListarAdministradores";

	
	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}
   
}
