package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Calificacion
 *
 */
@Entity

public class Calificacion implements Serializable {
	
	/**
	 * Relaciones de la entidad Calificacion
	 */
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false, unique = true)
	private int id;
	
	@Column(name = "valor")
	private int valor;
	
	private static final long serialVersionUID = 1L;

	public Calificacion() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
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
		Calificacion other = (Calificacion) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Calificacion [usuario=" + usuario + ", producto=" + producto + ", id=" + id + ", valor=" + valor + "]";
	}
	
   
}
