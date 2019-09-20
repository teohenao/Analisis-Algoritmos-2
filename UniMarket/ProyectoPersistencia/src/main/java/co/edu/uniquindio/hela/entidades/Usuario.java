package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad Usuario, Clase hija de Persona
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Usuario")
@NamedQueries({
	/**
	 * Consulta la cual permite listar todos los usuarios registrados en la base de datos
	 */
	@NamedQuery(name = Usuario.LISTAR_USUARIOS, query = "select USUARIO from Usuario usuario")

})
public class Usuario extends Persona implements Serializable {
	
	@OneToMany(mappedBy = "usuario")
	private List<Calificacion> calificaciones;
	
	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;
	
	@OneToMany(mappedBy = "usuario")
	private List<Favorito> favoritos;
	
	@OneToMany(mappedBy = "usuario")
	private List<Producto> productos;
	
	//constante que identifica la consulta que lista todos los usuarios
	public static final String LISTAR_USUARIOS = "ListarUsuarios";


	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}

	public List<Calificacion> getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(List<Calificacion> calificaciones) {
		this.calificaciones = calificaciones;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "Usuario [calificaciones=" + calificaciones + ", compras=" + compras + ", favoritos=" + favoritos
				+ ", productos=" + productos + "]";
	}
	
	
	
   
}
