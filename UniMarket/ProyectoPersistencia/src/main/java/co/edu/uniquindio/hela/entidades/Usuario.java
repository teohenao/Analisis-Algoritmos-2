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
	@NamedQuery(name = Usuario.LISTAR_USUARIOS, query = "select USUARIO from Usuario usuario"),
	@NamedQuery(name = Usuario.BUSCAR_POR_CEDULA, query = "select usuario from Usuario usuario where usuario.cedula = :cedula"),
	/**
	 * Consulta que nos permite obtener usuario al cual le pertenecen el email y clave
	 */
	@NamedQuery(name = Usuario.OBTENER_USER_EMAIL_CLAVE, query = "select user from Usuario user where user.email=:email and user.clave =:clave"),
	@NamedQuery(name = Usuario.CONSULTA_DTO_USUARIO , query = "select new co.edu.uniquindio.hela.logica.ConsultaUsuariosDTO(usuario.nombreCompleto,usuario.email) from Usuario usuario"),
	@NamedQuery(name = Usuario.USUARIOS_GMAIL, query = "select u from Usuario u")

})
public class Usuario extends Persona implements Serializable {
	
	//constante que identifica la consulta que lista todos los usuarios
		public static final String LISTAR_USUARIOS = "ListarUsuarios";

		//constante que identifica la consulta de usuario por email y clave
		public static final String OBTENER_USER_EMAIL_CLAVE = "ObtenerUserEmailClave";
		//constante que identifica la consulta de buscar un usuario por cedula
		public static final String BUSCAR_POR_CEDULA = "BuscarUserCedula";
		public static final String CONSULTA_DTO_USUARIO = "ConsultaDtoUsuario";
		
		public static final String USUARIOS_GMAIL = "UsuariosGmail";


	@OneToMany(mappedBy = "usuario")
	private List<Calificacion> calificaciones;

	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;

	@OneToMany(mappedBy = "usuario")
	private List<Favorito> favoritos;

	//,cascade = CascadeType.ALL
	@OneToMany(mappedBy = "usuario")
	private List<Producto> productos;

	

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
