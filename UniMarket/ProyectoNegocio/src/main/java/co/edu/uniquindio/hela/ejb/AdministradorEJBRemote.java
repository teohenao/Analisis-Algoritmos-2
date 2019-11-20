package co.edu.uniquindio.hela.ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Compra;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

/**
 * 
 * @author mateo
 *
 */
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
	 * Metodo que permite obtener el administrador al cual se le enviara el correo de recuperacion de clave
	 * @param cedula
	 * @return Administrador
	 * @throws InformacionInexistenteExcepcion
	 */
	public Administrador buscarAdministradorPorCedula(String cedula);	
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
	/**
	 * Metodo que permite listar los productos por nombre que se encuentren activos
	 * @param nombreProducto
	 * @return List Producto
	 */
	public List<Producto> listarProductosNombreActivos(String nombreProducto);
	/**
	 * Metodo que permite listar los favoritos de un usuario
	 * @param cedula
	 * @return List Favoritos
	 */
	public List<Favorito> listarFavoritosUsuario(String cedula);
	/**
	 * Metodo que permite actualizar los productos de unimarket
	 * @param producto
	 * @return ProductoActualizado
	 */
	public Producto actualizarProducto(Producto producto);
	/**
	 * Metodo que permite obtener un producto por medio del id 
	 * @param idProducto
	 * @return Producto 
	 */
	public Producto obtenerProductoId(int id);
	/**
	 * Metodo que permite comentar un producto
	 * @param c
	 * @return Comentario
	 */
	public Comentario comentarProducto(Comentario c,Usuario u);
	/**
	 * Metodo que permite listar los 3 productos mas vendidos de unimarket
	 * @return List Object productos mas vendidos
	 */
	public List<Object[]> listaTopVendidos();
	/*
	 * Metodo que permite enviar correos a una persona
	 * @return true si el correo se pudo enviar correctamente
	 */
	public Boolean envioEmail(Persona p,String mensaje) throws AddressException, MessagingException;
	
	public Boolean registrarProducto(Producto producto);
	
	public Boolean eliminarFavorito(String cedula,int id);
	public Favorito registrarFavorito(Favorito f,Usuario u);
	public Boolean esFavorito(String cedula, int id);
	public Boolean registrarDetalleCompra(DetalleCompra dc);
	public Boolean registrarCompra(Compra c);
	public List<Compra> listarComprasUsuario(Usuario u);
	public Calificacion obtenerCalificacionProductoUsuario(Producto p,Usuario u);
	public void registrarCalificacion(Calificacion c);
}
