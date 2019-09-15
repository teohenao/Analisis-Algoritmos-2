package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;


import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comentario
 *
 */
@Entity
@NamedQueries({
	
	@NamedQuery(name = Comentario.LISTAR_COMENTARIOS_PRODUCTO, query = "select c from Comentario c WHERE c.producto.id = :id")
	
})
public class Comentario implements Serializable {
	
	public static final String LISTAR_COMENTARIOS_PRODUCTO = "ListarComentariosProducto";

	
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
		Comentario other = (Comentario) obj;
		if (id != other.id)
			return false;
		return true;
	}
   
}
