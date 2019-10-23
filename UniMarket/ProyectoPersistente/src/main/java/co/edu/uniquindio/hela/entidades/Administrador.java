package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R,AnaMaria
 * Entidad Administrador que hereda los atributos de una persona
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Administrador")
@NamedQueries({
	/**
	 *	Consulta que nos permite listar todos los administradores de la base de datos
	 */
	@NamedQuery(name = Administrador.LISTAR_ADMINISTRADORES, query = "select ADMINISTRADOR from Administrador administrador"),
	/**
	 * Consulta que nos permite buscar un administrador por cedula
	 */
	@NamedQuery(name = Administrador.BUSCAR_POR_CEDULA, query = "select administrador from Administrador administrador where administrador.cedula = :cedula"),
	/**
	 * Consulta que nos permite obtener administrador al cual le pertenecen el email y clave
	 */
	@NamedQuery(name = Administrador.OBTENER_ADMIN_EMAIL_CLAVE, query = "select admin from Administrador admin where admin.email=:email and admin.clave =:clave"),
	/**
	 * Consulta que nos pemrite contar la cantidad de administradores registrados en unimarket
	 */
	@NamedQuery(name = Administrador.CONTAR_ADMINISTRADORES, query = "select count(a) from Administrador a"),


})
public class Administrador extends Persona implements Serializable {

	/**
	 * Constantes de identificacion de consultas 
	 */

	//Constante que identifica la consulta de listar todos los usuarios
	public static final String LISTAR_ADMINISTRADORES = "ListarAdministradores";
	//Constante que identifica la consulta de obtener el registro de un administrador por email y clave
	public static final String OBTENER_ADMIN_EMAIL_CLAVE = "ObtenerAdminEmailClave";
	//Constante que identifica la consulta de obtener un administrador por medio de la cedula
	public static final String BUSCAR_POR_CEDULA = "BuscarPorCedula";
	//Constante que identifica la consulta que cuenta los administradores registrados  en unimarket
	public static final String CONTAR_ADMINISTRADORES = "ContarAdministradores";





	private static final long serialVersionUID = 1L;

	public Administrador() {
		super();
	}

}
