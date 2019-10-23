package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @author Mateo Henao R,AnaMaria
 * Entidad DetalleCompra la cual contiene todo lo relevante a los datalles con productos que puede tener una compra realizada por un usuario
 * @version 1.0
 */
@Entity
@NamedQueries({
	/**
	 * Consulta la cual nos permite listar los detalles que tiene determinada compra
	 */
	@NamedQuery(name = DetalleCompra.LISTAR_DETALLES_COMPRA, query = "select dc from DetalleCompra dc where dc.compra.ref =:ref"),
	/**
	 * Consulta la cual nos permite listar los 5 productos mas vendidos en unimarket
	 */
	@NamedQuery(name = DetalleCompra.LISTAR_5PRODUCTOS_MAS_VENDIDOS, query = "select dc.producto.id ,SUM(dc.cantidad) AS total from DetalleCompra dc GROUP BY dc.producto.id ORDER BY total DESC")

})
public class DetalleCompra implements Serializable {

	/**
	 * Constantes que identifican las consultas de DetalleCompra
	 */

	//Constante que identifica la consulta que lista todos los detalles de una compra
	public static final String LISTAR_DETALLES_COMPRA = "ListarDetallesCompra";
	//Constante que identifica la consulta que listar los 5 productos mas vendidos
	public static final String LISTAR_5PRODUCTOS_MAS_VENDIDOS = "Listar5ProductosMasVendidos";

	/**
	 * Relaciones de la entidad Detalle Compra
	 */

	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	//Relacion de muchos a uno con compra
	@ManyToOne
	private Compra compra;


	/**
	 * Atributos de la entidad DetalleCompra
	 */

	/**
	 * Id auto incrementable el cual identifica cada detalle de compra de la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false,unique = true,updatable = false)
	private int id;

	/**
	 * Cantidad que desea el usuario de cada producto
	 */
	@Column(name = "cantidad",nullable = false,length = 4)
	private int cantidad;

	/**
	 * Precio con el cual el producto fue vendido
	 */
	@Column(name = "precio",nullable = false,length = 10)
	private Double precio;

	private static final long serialVersionUID = 1L;

	public DetalleCompra() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}   
	public Double getPrecio() {
		return this.precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
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
		DetalleCompra other = (DetalleCompra) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DetalleCompra [producto=" + producto + ", compra=" + compra + ", id=" + id + ", cantidad=" + cantidad
				+ ", precio=" + precio + "]";
	}


}
