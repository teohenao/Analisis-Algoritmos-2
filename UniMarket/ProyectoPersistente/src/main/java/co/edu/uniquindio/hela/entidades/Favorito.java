package co.edu.uniquindio.hela.entidades;

import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R, Ana Maria Latorre
 * Entidad Favorito la cual contiene todo lo relevante a los productos favoritos de un usuario
 * @version 1.0
 */
@Entity
@NamedQueries({
	/**
	 * Consulta la cual nos permite listar todos los productos favoritos de un usuario
	 */
	@NamedQuery(name = Favorito.LISTAR_FAVORITOS_USUARIO, query = "select f from Favorito f WHERE f.usuario.cedula = :cc "),
	/**
	 * Consulta la cual nos permite listar todos los productos favoritos de un usuario, con los productos activos
	 */
	@NamedQuery(name = Favorito.LISTAR_FAVORITOS_USUARIO_ACTIVOS, query = "select f from Favorito f WHERE (f.usuario.cedula = :cc) AND (f.producto.fechaLimite >=:fechaActual ) "),
	/**
	 * Consulta la cual nos permite listar todos los productos favoritos de un usuario, con los productos vencidos
	 */
	@NamedQuery(name = Favorito.LISTAR_FAVORITOS_USUARIO_VENCIDOS, query = "select f from Favorito f WHERE (f.usuario.cedula = :cc) AND (f.producto.fechaLimite <:fechaActual ) "),
	
	@NamedQuery(name = Favorito.ES_FAVORITO,query ="select f from Favorito f WHERE (f.usuario.cedula = :cc ) AND (f.producto.id = :id) ")

})

public class Favorito implements Serializable {

	/**
	 * Constantes que identifican las consultas de Favoritos
	 */

	//constante que nos permite identificar la consulta de listar favoritos de un usuario
	public static final String LISTAR_FAVORITOS_USUARIO = "ListarFavoritosUsuario";
	//Constante que nos permite identificar la consulta de listar favoritos de un usuario, productos activos
	public static final String LISTAR_FAVORITOS_USUARIO_ACTIVOS = "ListarFavoritosUsuarioActivos";
	//Constante que nos permite identificar la consulta de listar favoritos de un usuario, productos vencidos
	public static final String LISTAR_FAVORITOS_USUARIO_VENCIDOS = "ListarFavoritosUsuarioVencidos";
	
	public static final String ES_FAVORITO = "EsFavorito";


	/**
	 * Relaciones de la entidad Favorito
	 */

	//Relacion de muchos a uno con la entidad usuario
	@ManyToOne
	private Usuario usuario;

	//Relacion de muchos a uno con la entidad de producto
	@ManyToOne
	private Producto producto;

	/**
	 * Atributos de la entidad Favorito
	 */

	/**
	 * Id autoincrementable el cual identifica cada favorito agregado en la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false, unique = false,updatable = false)
	private int id;


	private static final long serialVersionUID = 1L;

	public Favorito() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}   
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	@Override
	public String toString() {
		return "Favorito [usuario=" + usuario + ", producto=" + producto + ", id=" + id + "]";
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
		Favorito other = (Favorito) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
