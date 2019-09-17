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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
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
   
}
