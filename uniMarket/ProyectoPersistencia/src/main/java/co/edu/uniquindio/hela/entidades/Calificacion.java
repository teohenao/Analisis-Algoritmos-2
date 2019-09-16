package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad calificacion que contiene las calificaciones que le dan los usuarios a cierto producto
 * @version 1.0
 */
@Entity
@NamedQueries({
	/**
	 * Consulta que nos permite listar todas las calificaciones que los usuarios le han dado a determinado producto
	 */
	@NamedQuery(name = Calificacion.LISTAR_CALIFICACIONES_PRODUCTO, query = "select c from Calificacion c WHERE c.producto.id = :id")

})
public class Calificacion implements Serializable {
	
	//Constante que identifica la consulta de listar las calificaciones de cierto producto
	public static final String LISTAR_CALIFICACIONES_PRODUCTO = "ListarCalificacionesProducto";

	
	/**
	 * Relaciones de la entidad Calificacion
	 */
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	/**
	 * Atributos de la entidad Calificacion
	 */
	   
	/**
	 * Id autoincrementable de cada calificacion registrada en la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false, unique = true)
	private int id;
	
	/**
	 * valor de la calificacion que el usuario le dio a cierto producto seleccionado
	 */
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
