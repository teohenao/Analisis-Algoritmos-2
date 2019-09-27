package co.edu.uniquindio.hela.modelo;

import co.edu.uniquindio.hela.ejb.AdministradorEJBRemote;
import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class AdministradorDelegado {

public static AdministradorDelegado administradorDelegado = instancia();
	
	public AdministradorEJBRemote administradorEJB;
	
	public AdministradorDelegado() {
		try {
			administradorEJB = (AdministradorEJBRemote) new InitialContext().lookup(AdministradorEJBRemote.JNDI);
		} catch (NamingException e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Permite devolver una unica instancia de delegado
	 * 
	 * @return instancia unica del delegado
	 */
	private static AdministradorDelegado instancia() {

		if (administradorDelegado == null) {
			administradorDelegado = new AdministradorDelegado();
			return administradorDelegado;
		}
		return administradorDelegado;
	}
	

	public Usuario registrarUsuario(Usuario usuario) throws InformacionRepetidaExcepcion {
		return administradorEJB.registrarUsuario(usuario);
	}


	public boolean aprobarIngresoAdmin(String cedula, String clave) {
		return administradorEJB.aprobarIngresoAdmin(cedula, clave);
	}

	public List<Usuario> listarUsuarios(){
		return administradorEJB.listarUsuarios();
	}
	public boolean eliminarUsuario(String cedula){
		return administradorEJB.eliminarUsuario(cedula);
	}

	public Usuario buscarUsuarioPorCedula(String cedula) {
		return administradorEJB.buscarUsuarioPorCedula(cedula);
	}

	public Usuario actualizarUsuario(Usuario usuario){
		return administradorEJB.actualizarUsuario(usuario);
	}

	public boolean buscarPorEmail(String email) {
		return administradorEJB.buscarPorEmail(email);
	}

	public Administrador buscarAdministradorEnvioCorreo(String cedula) throws InformacionInexistenteExcepcion {
		return administradorEJB.buscarAdministradorEnvioCorreo(cedula);
	}

	public List<Producto> listarProductos() {
		return administradorEJB.listarProductos();
	}

	




}
