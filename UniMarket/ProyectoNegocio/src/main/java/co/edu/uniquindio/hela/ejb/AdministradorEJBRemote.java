package co.edu.uniquindio.hela.ejb;

import java.util.List;

import javax.ejb.Remote;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

@Remote
public interface AdministradorEJBRemote {
	
	public static final String JNDI = "java:global/ProyectoEAR/ProyectoNegocio/AdministradorEJB!co.edu.uniquindio.hela.ejb.AdministradorEJBRemote";

	
	public Usuario registrarUsuario(Usuario usuario) throws InformacionRepetidaExcepcion;
	
	
	public boolean aprobarIngresoAdmin(String cedula, String clave);
	/**
	 * metodo que permite listar todos los usuarios
	 * 
	 * @return lista de usuarios
	 */
	public List<Usuario> listarUsuarios();

	/**
	 * permite eliminar un usuario
	 * 
	 * @param usuario a eliminar
	 * @return true si lo elimino, de lo contrario false
	 */
	public boolean eliminarUsuario(String cedula);
	
	public Usuario buscarUsuarioPorCedula(String cedula);
	
	public Usuario actualizarUsuario(Usuario usuario);

	public boolean buscarPorEmail(String email);
	
	public Administrador buscarAdministradorEnvioCorreo(String cedula) throws InformacionInexistenteExcepcion;	
	
	public List<Producto> listarProductos() ;


}
