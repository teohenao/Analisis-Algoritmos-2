package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS, query = "select producto from Producto producto"),
	@NamedQuery(name = Producto.LISTAR_PRODUCTOS_CATEGORIA, query = "select p from Producto p where p.categoria like CONCAT('', :c, '')")
	
})
public class Producto implements Serializable {
	
	public static final String LISTAR_PRODUCTOS = "ListarProductos";
	public static final String LISTAR_PRODUCTOS_CATEGORIA = "ListarProductosCategoria";

	
	/**
	 * Relaciones de la entidad Producto
	 */
	
	//Relacion de uno a muchos con la entidad de compra
	@OneToMany(mappedBy = "producto")
	private List<Compra> compras;
	
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

	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	private int id;
	
	@Column(name = "nombre",nullable = false)
	private String nombre;
	
	@Column(name = "descripcion",nullable = false)
	private String descripcion;
	
	@Column(name = "disponibilidad",nullable = false)
	private int disponibilidad;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "categoria",nullable = false)
	private Categoria categoria;
	
	@Column(name = "precio",nullable = false)
	private Double precio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaLimite")
	private Date fechaLimite;
	
	
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
	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
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
		return "Producto [compras=" + compras + ", usuario=" + usuario + ", Calificaciones=" + Calificaciones
				+ ", Comentarios=" + Comentarios + ", Favoritos=" + Favoritos + ", id=" + id + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", disponibilidad=" + disponibilidad + ", categoria=" + categoria
				+ ", precio=" + precio + ", fechaLimite=" + fechaLimite + ", imagenes=" + imagenes + "]";
	}
	
	
	
	
	
}
