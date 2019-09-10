package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity

public class Producto implements Serializable {
	
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String descripcion;
	private int disponibilidad;
	private Categoria categoria;
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

	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", disponibilidad="
				+ disponibilidad + "]";
	}
   
	
}
