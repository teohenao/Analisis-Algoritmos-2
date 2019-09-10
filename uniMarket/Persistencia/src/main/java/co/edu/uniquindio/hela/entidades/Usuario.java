package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@DiscriminatorValue("Usuario")
public class Usuario extends Persona implements Serializable {
	
	/**
	 * Relaciones de la entidad usuario
	 */
	
	//Relacion de uno a muchos con la entidad de compra
	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;
	
	//Relacion de uno a muchos con la entidad de producto
	@OneToMany(mappedBy = "usuario")
	private List<Producto>productos;
	
	//Relacion de uno a muchos con la entidad calificacion
	@OneToMany(mappedBy = "usuario")
	private List<Calificacion>calificaciones;
	
	//Relacion de uno a muchos con la entidad comentario
	@OneToMany(mappedBy = "usuario")
	private List<Comentario>comentarios;
	
	//Relacion de uno a muchos con la entidad comentario
	@OneToMany(mappedBy = "usuario")
	private List<Favorito>favoritos;
	
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
   
}
