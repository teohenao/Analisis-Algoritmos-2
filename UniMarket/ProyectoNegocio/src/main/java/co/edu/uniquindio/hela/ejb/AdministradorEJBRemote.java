package co.edu.uniquindio.hela.ejb;

import java.util.List;

import javax.ejb.Remote;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

@Remote
public interface AdministradorEJBRemote {
	
	public static final String JNDI = "java:global/ProyectoEAR/ProyectoNegocio/AdministradorEJB!co.edu.uniquindio.hela.ejb.AdministradorEJBRemote";

	
	public Usuario registrarUsuario(Usuario usuario) throws InformacionRepetidaExcepcion;
	
	/**
	 * Metodo que permite aprobar el ingreso del administrador a unimarket
	 * @param cedula
	 * @param clave
	 * @return True si el ingreso es aprobado
	 */
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
	/**
	 * Metodo que permite buscar un usuario por cedula
	 * @param cedula
	 * @return Usuario
	 */
	public Usuario buscarUsuarioPorCedula(String cedula);
	/**
	 * Metodo que permite actualizar la informacion de un usuario 
	 * @param usuario
	 * @return Usuario 
	 */
	public Usuario actualizarUsuario(Usuario usuario);
	/**
	 * Metodo que permite buscar un email, para rectificar que no se repita
	 * @param email
	 * @return Boolean si ya existe un email
	 */
	public boolean buscarPorEmail(String email);
	/**
	 * Metodo que permite obtener el administrador al cual se le enviara el correo de recuperacion de clave
	 * @param cedula
	 * @return Administrador
	 * @throws InformacionInexistenteExcepcion
	 */
	public Administrador buscarAdministradorEnvioCorreo(String cedula) throws InformacionInexistenteExcepcion;	
	/**
	 * Metodo que permite listar todos los productos de unimarket
	 * @return List Productos
	 */
	public List<Producto> listarProductos() ;
	/**
	 * Metodo que permite listar todos los productos activos de unimarket
	 * @return List Productos Activos
	 */
	public List<Producto> listarProductosActivos();
	/**
	 * Metodo que permite listar todos los productos vencidos de unimarket
	 * @return List Productos Vencidos
	 */
	public List<Producto> listarProductosVencidos();
	/**
	 * Metodo que permite listar todos los productos vencidos por categoria
	 * @param categoria
	 * @return List Productos Vencidos Categoria
	 */
	public List<Producto> listarProductosVencidosCategoria(String categoria);
	/**
	 * Metodo que permite listar todos los productos activos por categoria
	 * @param categoria
	 * @return List Productos Activos Categoria
	 */
	public List<Producto> listarProductosActivosCategoria(String categoria);
	/**
	 * Metodo que permite listar todos los productos por categoria
	 * @param categoria
	 * @return List Productos Categoria
	 */
	public List<Producto> listarProductosCategoria(String categoria);
	/**
	 * Metodo que permite listar los productos por filtrado de nombre o palabras similares en el nombre
	 * @param nombreProducto
	 * @return List Productos Nombre
	 */
	public List<Producto> listarProductosNombre(String nombreProducto);
	/**
	 * Metodo que permite listar todos los productos registrados por determinado usuario
	 * @param ccUsuario
	 * @return list Productos Usuario
	 */
	public List<Producto> listarProductosUsuario(String ccUsuario);
	/**
	 * Metodo que permite listar todos los comentarios de un producto
	 * @param idProducto
	 * @return List Comentarios Producto
	 */
	public List<Comentario> listarComentariosProducto(int idProducto);
	/**
	 * Metodo que permite encontrar la calificacion final de un producto
	 * @param idProducto
	 * @return double, calificacion
	 */
	public double calificacionFinalProducto(int idProducto);
	/**
	 * Metodo que permite obtener si existen calificaciones para un producto
	 * @param id
	 * @return true si el producto tiene calificaciones
	 */
	public Boolean listarCalificacionesProducto(int id);
	/**
	 * Metodo que permite listar todas las imagenes de un producto
	 * @param id
	 * @return list Imagenes Producto
	 */
	public List<Producto> listarImageneProducto(int id);
	/**
	 * Metodo que permite aprobar el ingreso de un usuario a unimarket
	 * @param cedula
	 * @param clave
	 * @return true si la clave coincide con el usuario existente
	 */
	public boolean aprobarIngresoUser(String cedula, String clave); 

	
	public List<Producto> listarProductosNombreActivos(String nombreProducto);
	public List<Favorito> listarFavoritosUsuario(String cedula);

}
