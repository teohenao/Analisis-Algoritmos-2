package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;


import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comentario
 *
 */
@Entity

public class Comentario implements Serializable {
	
	/**
	 * Relaciones de la entidad Comentario
	*/
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
		
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	private int id;
	
	@Column(name = "comentario",nullable = false)
	private String comentario;
	
	private static final long serialVersionUID = 1L;

	public Comentario() {
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
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
   
}
