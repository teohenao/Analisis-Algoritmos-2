package co.edu.uniquindio.hela.modelo;

import co.edu.uniquindio.hela.ejb.AdministradorEJBRemote;
import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase que permite el uso de los metodos de negocio en la aplicacion de escritorio de unimarket
 * @author mateo,AnaMaria
 * @version 1.0
 */
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
	
	/**
	 * Metodo que permite registrar un usuario en unimarket
	 * @param usuario
	 * @return Usuario registrado
	 * @throws InformacionRepetidaExcepcion
	 */
	public Usuario registrarUsuario(Usuario usuario) throws InformacionRepetidaExcepcion {
		return administradorEJB.registrarUsuario(usuario);
	}
	/**
	 * Metodoq que permite aprobar el ingreso de un administrador a unimarket
	 * @param cedula
	 * @param clave
	 * @return true si el administrador puede ingresar
	 */
	public boolean aprobarIngresoAdmin(String cedula, String clave) {
		return administradorEJB.aprobarIngresoAdmin(cedula, clave);
	}
	/**
	 * Metodo que permite listar los usuarios de unimarket
	 * @return List usuarios
	 */
	public List<Usuario> listarUsuarios(){
		return administradorEJB.listarUsuarios();
	}
	/**
	 * Metodo que permite eliminar un usuario de unimarket
	 * @param cedula
	 * @return true si el usuario se pudo eliminar
	 */
	public boolean eliminarUsuario(String cedula){
		return administradorEJB.eliminarUsuario(cedula);
	}

	/**
	 * Metodo que permite buscar un usuario por medio de la cedula 
	 * @param cedula
	 * @return Usuario eliminaro
	 */
	public Usuario buscarUsuarioPorCedula(String cedula) {
		return administradorEJB.buscarUsuarioPorCedula(cedula);
	}
	/**
	 * Metodo que permite actualizar un usuario de unimarket
	 * @param usuario
	 * @return Usuario actualizado
	 */
	public Usuario actualizarUsuario(Usuario usuario){
		return administradorEJB.actualizarUsuario(usuario);
	}
	/**
	 * Metodo que permite listar los productos de unimarket
	 * @return list Productos
	 */
	public List<Producto> listarProductos() {
		return administradorEJB.listarProductos();
	}

	/**
	 * Metodo que permite listar los productos activos de unimarket
	 * @return list Productos activos
	 */
	public List<Producto> listarProductosActivos() {
		return administradorEJB.listarProductosActivos();
	}
	/**
	 * Metodo que permite listar los productos vencidos de unimarket
	 * @return lista productos vencidos
	 */
	public List<Producto> listarProductosVencidos() {
		return administradorEJB.listarProductosVencidos();
	}
	/**
	 * Metodo que permite listar los productos vencidos de unimarket por categoria
	 * @param categoria
	 * @return List productos Vencidos Categoria
	 */
	public List<Producto> listarProductosVencidosCategoria(String categoria) {
		return administradorEJB.listarProductosVencidosCategoria(categoria);
	}
	/**
	 * Metodo que permite listar los productos activos por categoria
	 * @param categoria
	 * @return List productos activos categoria
	 */
	public List<Producto> listarProductosActivosCategoria(String categoria) {
		return administradorEJB.listarProductosActivosCategoria(categoria);
	}
	
	/**
	 * Metodo que permite listar productos por categoria
	 * @param categoria
	 * @return list ProductosCategoria
	 */
	public List<Producto> listarProductosCategoria(String categoria) {
		return administradorEJB.listarProductosCategoria(categoria);
	}
	
	/**
	 * Metodo que permite listar productos por nombre
	 * @param nombreProducto
	 * @return list productos nombre
	 */
	public List<Producto> listarProductosNombre(String nombreProducto) {
		return administradorEJB.listarProductosNombre(nombreProducto);
	}
	
	/**
	 * Metodo que permite listar productos por usuario
	 * @param ccUsuario
	 * @return list productos usuario
	 */
	public List<Producto> listarProductosUsuario(String ccUsuario) {
		return administradorEJB.listarProductosUsuario(ccUsuario);
	}

	/**
	 * Metodo que permite listar los comentarios de un producto
	 * @param idProducto
	 * @return List comentarios
	 */
	public List<Comentario> listarComentariosProducto(int idProducto) {
		return administradorEJB.listarComentariosProducto(idProducto);
	}

	/**
	 * Metodo que permite obtener la calificacion promedio de un producto
	 * @param idProducto
	 * @return Calificacion final producto
	 */
	public double calificacionFinalProducto(int idProducto) {
		return administradorEJB.calificacionFinalProducto(idProducto);
	}
	
	/**
	 * Metodo que permite determinar si un producto tiene calificaciones o no
	 * @param id
	 * @return true si el producto tiene calificaciones
	 */
	public Boolean listarCalificacionesProducto(int id) {
		return administradorEJB.listarCalificacionesProducto(id);
	}

	/**
	 * Metodo que permite listar las imagenes de un producto
	 * @param id
	 * @return list Imagenes del producto
	 */
	public List<Producto> listarImageneProducto(int id) {
		return administradorEJB.listarImageneProducto(id);
	}

	/**
	 * Metodo que permite buscar un administrador por medio de la cedula
	 * @param cedula
	 * @return Administrador al que pertenece esa cedula
	 */
	public Administrador buscarAdministradorPorCedula(String cedula) {
		return administradorEJB.buscarAdministradorPorCedula(cedula);
	}
	/**
	 * Metodo que permite el envio de correos al administrador
	 * @param p
	 * @return true, si el envio se pudo realizar de manera correcta
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public Boolean envioEmail(Persona p) throws AddressException, MessagingException {
		return administradorEJB.envioEmail(p);
	}
	
	

	




}
