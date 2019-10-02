package co.edu.uniquindio.hela.modelo;

import co.edu.uniquindio.hela.ejb.AdministradorEJBRemote;
import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Comentario;
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

	public List<Producto> listarProductosActivos() {
		return administradorEJB.listarProductosActivos();
	}

	public List<Producto> listarProductosVencidos() {
		return administradorEJB.listarProductosVencidos();
	}

	public List<Producto> listarProductosVencidosCategoria(String categoria) {
		return administradorEJB.listarProductosVencidosCategoria(categoria);
	}

	public List<Producto> listarProductosActivosCategoria(String categoria) {
		return administradorEJB.listarProductosActivosCategoria(categoria);
	}

	public List<Producto> listarProductosCategoria(String categoria) {
		return administradorEJB.listarProductosCategoria(categoria);
	}

	public List<Producto> listarProductosNombre(String nombreProducto) {
		return administradorEJB.listarProductosNombre(nombreProducto);
	}

	public List<Producto> listarProductosUsuario(String ccUsuario) {
		return administradorEJB.listarProductosUsuario(ccUsuario);
	}

	public List<Comentario> listarComentariosProducto(int idProducto) {
		return administradorEJB.listarComentariosProducto(idProducto);
	}

	public double calificacionFinalProducto(int idProducto) {
		return administradorEJB.calificacionFinalProducto(idProducto);
	}

	public Boolean listarCalificacionesProducto(int id) {
		return administradorEJB.listarCalificacionesProducto(id);
	}

	public List<Producto> listarImageneProducto(int id) {
		return administradorEJB.listarImageneProducto(id);
	}
	
	

	




}
