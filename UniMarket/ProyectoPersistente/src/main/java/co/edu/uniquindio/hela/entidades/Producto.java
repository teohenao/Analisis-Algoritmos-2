package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * @author Mateo Henao R, Ana Maria Latorre
 * Entidad producto la cual contiene todo lo relevante a los productos de la plataforma
 * @version 1.0
 */
@Entity
@NamedQueries({
	/**
	 * Consulta la cual permite listar los productos por medio del nombre 
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_NOMBRE, query = "select producto from Producto producto where producto.nombre LIKE :nombre "),
	/**
	 * Consulta la cual permite listar los productos por medio del nombre que se encuentren activos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_NOMBRE_ACTIVOS, query = "select producto from Producto producto where producto.nombre LIKE :nombre AND  producto.fechaLimite >=  :fechaActual AND producto.disponibilidad > 0"),
	
	/**
	 * Consulta la cual permite listar todos los productos registrados en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS, query = "select producto from Producto producto"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran con fecha activa en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS, query = "select p from Producto p where p.fechaLimite >=  :fechaActual AND p.disponibilidad > 0"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran activos en la base de datos, y filtrarlos por categoria
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA, query = "select p from Producto p where (p.fechaLimite >=  :fechaActual ) AND (p.categoria =CONCAT ('',:c,'') AND (p.disponibilidad > 0))"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos "su fecha ya paso" registrados en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS, query = "select p from Producto p where p.fechaLimite <:fechaActual"),
	/**
	 * Consulta la cual nos permite listar todos los productos registrados en la base de datos, por categoria esten o no activos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_CATEGORIA, query = "select p from Producto p where p.categoria = CONCAT ('',:c,'')"),
	/**
	 * Consulta la cual nos permite listar los productos que ha insertado cierto usuario
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_USUARIO, query = "select p from Producto p where p.usuario.cedula =:cc"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos en la base de datos, y filtrarlos por categoria
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA, query = "select p from Producto p where (p.fechaLimite <:fechaActual ) AND (p.categoria = CONCAT ('',:c,''))"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran activos en la base de datos, y filtrarlos por el usuario creador
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS_USUARIO, query = "select p from Producto p where (p.fechaLimite >=:fechaActual ) AND (p.usuario.cedula =:cc) AND p.disponibilidad > 0"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos en la base de datos, y filtrarlos por usuario creador
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS_USUARIO, query = "select p from Producto p where (p.fechaLimite <:fechaActual ) AND (p.usuario.cedula =:cc)"),
	/**
	 * Consulta la cual permite listar la calificacion final de cada producto, y los productos que no tienen calificaciones por igual
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_CON_SIN_CALIFICACIONES, query = "SELECT producto.id , AVG(calificacion.valor) FROM Producto producto LEFT JOIN Calificacion calificacion ON calificacion.producto.id = producto.id GROUP BY producto.id"),
	/**
	 * Consulta DTO que permite listar los productos por nombre y categoria
	 */
	@NamedQuery(name = Producto.CONSULTA_DTO_PRODUCTO , query = "select new co.edu.uniquindio.hela.logica.ConsultaProductosDTO(producto.nombre,producto.descripcion) from Producto producto"),
	/**
	 * Consulta que permite contar los productos por categorias en unimarket
	 */
	@NamedQuery(name = Producto.CONTAR_PRODUCTOS_CATEGORIAS, query = "select COUNT(p.categoria) from Producto p GROUP BY p.categoria"),
	/**
	 * Consulta que permitte listar los productos que no tienen comentarios en unimarket
	 */
	@NamedQuery(name = Producto.PRODUCTOS_SIN_COMENTARIOS, query ="select p from Producto p WHERE p.Comentarios IS EMPTY"),
	/**
	 * Consulta que permite listar las imagenes de determinado producto
	 */
	@NamedQuery(name = Producto.LISTAR_IMAGENES_PRODUCTO, query ="select p.imagenes from Producto p where p.id =:id "),
	/**
	 * Consulta que permite determinar cantidad de productos registrados por usuarios
	 */
	@NamedQuery(name = Producto.CANTIDAD_PRODUCTOS_USUARIO,query = "select new co.edu.uniquindio.hela.logica.ConsultaCantidadProductosUsuarioDTO(p.usuario.cedula,p.usuario.email),COUNT(p.usuario.cedula) from Producto p group by p.usuario.cedula"),
	/**
	 * Consulta que permite determinar la categoria que tiene mas productos registrados en unimarket
	 */
	@NamedQuery(name = Producto.CATEGORIA_MAS_PRODUCTOS, query = "select MAX(p.categoria),p.nombre from Producto p"),
	/**
	 * Consulta que permite determinar el producto que tiene el precio mas caro en unimarket
	 */
	@NamedQuery(name = Producto.PRODUCTO_PRECIO_MAS_ALTO,query = "select MAX(p.precio),p.nombre from Producto p ")
})
public class Producto implements Serializable {

	/**
	 * Constantes que identifican las consultas de Producto
	 */
	//Constante que identifica la consulta que lista todos los productos registrados
	public static final String LISTAR_PRODUCTOS = "ListarProductos";
	//Constante que identifica la consulta que lista todos los productos que se encuentran con fecha activa en la plataforma
	public static final String LISTAR_PRODUCTOS_ACTIVOS = "ListarProductosActivos";
	//Constante que identifica la consulta que lista todos los productos vencidos de la base de datos
	public static final String LISTAR_PRODUCTOS_VENCIDOS = "ListarProductosVencidos";
	//Constante que identifica la consulta que lista todos los productos de cierta categoria
	public static final String LISTAR_PRODUCTOS_CATEGORIA = "ListarProductosCategoria";
	//Constante que identifica la consulta que lista todos los productos activos de cierta categoria
	public static final String LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA = "ListarProductosActivosCategoria";
	//Constante que identifica la consulta que lista todos los productos vencidos de cierta categoria
	public static final String LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA = "ListarProductosVencidosCategoria";
	//Constante que identifica la consulta que lista todos los productos de determinado usuario
	public static final String LISTAR_PRODUCTOS_USUARIO = "ListarProductosUsuario";
	//Constante que identifica la consulta que lista todos los productos activos de cierto Usuario
	public static final String LISTAR_PRODUCTOS_ACTIVOS_USUARIO = "ListarProductosActivosUsuario";
	//Constante que identifica la consulta que lista todos los productos vencidos de cierto Usuario
	public static final String LISTAR_PRODUCTOS_VENCIDOS_USUARIO = "ListarProductosVencidosUsuario";
	//Constante que identifica la consulta que lista productos por medio de nombre o similares en nombre
	public static final String LISTAR_PRODUCTOS_NOMBRE = "ListarProductosNombres";
	//Constante que identifica la consulta que lista los productos que tienen o no calificaciones
	public static final String LISTAR_PRODUCTOS_CON_SIN_CALIFICACIONES = "ListarProductosConSinCalificaciones"; 
	//Constante que identifica la consulta DTO que lista productos por nombre y categoria
	public static final String CONSULTA_DTO_PRODUCTO = "ConsultaDtoProducto";
	//Constante que identifica la consulta que cuenta los productos por categoria en unimarket
	public static final String CONTAR_PRODUCTOS_CATEGORIAS = "ContarProductosCategorias";
	//Constante que identifica la consulta que lista los productos que no tienen comentarios en unimarket
	public static final String PRODUCTOS_SIN_COMENTARIOS = "ProductosSinComentarios";
	//Constante que identifica la consulta que lista todas las imagenes de un producto
	public static final String LISTAR_IMAGENES_PRODUCTO = "ListarImagenesProducto";
	//Constante que identifica la consulta que cuenta los productos por usuario
	public static final String CANTIDAD_PRODUCTOS_USUARIO = "CantidadProductosUsuario";
	//Constante que identifica la consulta de identificar la categoria con mas productos
	public static final String CATEGORIA_MAS_PRODUCTOS = "CategoriaMasProductos";
	//Constante que identifica la consulta que encuentra el producto con el precio mas alto
	public static final String PRODUCTO_PRECIO_MAS_ALTO = "ProductoPrecioMasAlto";	
	//Constante que identifica la consulta que lista productos por medio de nombre o similares en nombre que se encuentren activos
	public static final String LISTAR_PRODUCTOS_NOMBRE_ACTIVOS = "ListarProductosNombresActivos";


	/**
	 * Relaciones de la entidad Producto
	 */

	//Relacion de uno a muchos con la entidad de detalle compras
	@OneToMany(mappedBy = "producto", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<DetalleCompra> DetalleCompras;

	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;

	//Relacion de uno a muchos con la entidad de calificaciones
	@OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
	private List<Calificacion> Calificaciones;

	//Relacion de uno a muchos con la entidad de comentarios
	@OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
	private List<Comentario> Comentarios;

	//Relacion de uno a muchos con la entidad de favoritos
	@OneToMany(mappedBy = "producto",cascade = CascadeType.ALL)
	private List<Favorito> Favoritos;

	/**
	 * Atributos de Producto
	 */

	/**
	 * Id autoincrementable el cual identifica cada producto registrado en la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false,updatable = false)
	private int id;

	/**
	 * Nombre del producto
	 */
	@Column(name = "nombre",nullable = false ,length = 30)
	private String nombre;

	/**
	 * Descripcion del producto
	 */
	@Column(name = "descripcion",nullable = false,length = 200)
	private String descripcion;

	/**
	 * Disponibilidad del producto
	 */
	@Column(name = "disponibilidad",nullable = false,length = 4)
	private int disponibilidad;

	/**
	 * Categorida del producto
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "categoria",nullable = false)
	private Categoria categoria;

	/**
	 * Precio del producto
	 */
	@Column(name = "precio",nullable = false,length = 10)
	private Double precio;

	/**
	 * Fecha limite del producto, la cual define si esta activo o no
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaLimite")
	private Date fechaLimite;

	/**
	 * Lista de imagenes de un producto, Crea tabla con todas las imagenes registradas y su relacion con el producto
	 */
	@ElementCollection
	private List<String> imagenes = new ArrayList<String>();


	private static final long serialVersionUID = 1L;

	public Producto() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}   
	public int getDisponibilidad() {
		return this.disponibilidad;
	}




	public List<DetalleCompra> getDetalleCompras() {
		return DetalleCompras;
	}
	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		DetalleCompras = detalleCompras;
	}
	public List<String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Calificacion> getCalificaciones() {
		return Calificaciones;
	}
	public void setCalificaciones(List<Calificacion> calificaciones) {
		Calificaciones = calificaciones;
	}
	public List<Comentario> getComentarios() {
		return Comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		Comentarios = comentarios;
	}
	public List<Favorito> getFavoritos() {
		return Favoritos;
	}
	public void setFavoritos(List<Favorito> favoritos) {
		Favoritos = favoritos;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Producto [DetalleCompras=" + DetalleCompras + ", usuario=" + usuario + ", Calificaciones="
				+ Calificaciones + ", Comentarios=" + Comentarios + ", Favoritos=" + Favoritos + ", id=" + id
				+ ", nombre=" + nombre + ", descripcion=" + descripcion + ", disponibilidad=" + disponibilidad
				+ ", categoria=" + categoria + ", precio=" + precio + ", fechaLimite=" + fechaLimite + ", imagenes="
				+ imagenes + "]";
	}





}
