package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad producto la cual contiene todo lo relevante a los productos de la plataforma
 * @version 1.0
 */
@Entity
@NamedQueries({
	/**
	 * Consulta la cual permite listar todos los productos registrados en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS, query = "select producto from Producto producto"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran con fecha activa en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS, query = "select p from Producto p where p.fechaLimite >=  :fechaActual"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran activos en la base de datos, y filtrarlos por categoria
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA, query = "select p from Producto p where (p.fechaLimite >=  :fechaActual ) AND (p.categoria =:c)"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos "su fecha ya paso" registrados en la base de datos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS, query = "select p from Producto p where p.fechaLimite <:fechaActual"),
	/**
	 * Consulta la cual nos permite listar todos los productos registrados en la base de datos, por categoria esten o no activos
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_CATEGORIA, query = "select p from Producto p where p.categoria =:c"),
	/**
	 * Consulta la cual nos permite listar los productos que ha insertado cierto usuario
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_USUARIO, query = "select p from Producto p where p.usuario.cedula =:cc"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos en la base de datos, y filtrarlos por categoria
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA, query = "select p from Producto p where (p.fechaLimite <:fechaActual ) AND (p.categoria = :c)"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran activos en la base de datos, y filtrarlos por el usuario creador
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_ACTIVOS_USUARIO, query = "select p from Producto p where (p.fechaLimite >=:fechaActual ) AND (p.usuario.cedula =:cc)"),
	/**
	 * Consulta la cual permite listar todos los productos que se encuentran vencidos en la base de datos, y filtrarlos por usuario creador
	 */
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_VENCIDOS_USUARIO, query = "select p from Producto p where (p.fechaLimite <:fechaActual ) AND (p.usuario.cedula =:cc)")
		
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

	/**
	 * Relaciones de la entidad Producto
	 */
	
	//Relacion de uno a muchos con la entidad de detalle compras
	@OneToMany(mappedBy = "producto")
	private List<DetalleCompra> DetalleCompras;
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de uno a muchos con la entidad de calificaciones
	@OneToMany(mappedBy = "producto")
	private List<Calificacion> Calificaciones;
	
	//Relacion de uno a muchos con la entidad de comentarios
	@OneToMany(mappedBy = "producto")
	private List<Comentario> Comentarios;
	
	//Relacion de uno a muchos con la entidad de favoritos
	@OneToMany(mappedBy = "producto")
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
	@Column(name = "nombre",nullable = false ,length = 15)
	private String nombre;
	
	/**
	 * Descripcion del producto
	 */
	@Column(name = "descripcion",nullable = false,length = 100)
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
	@Column(name = "imagenes")
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