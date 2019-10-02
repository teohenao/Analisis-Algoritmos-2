package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad Compra la cual contiene todo lo relevante a las compras realizadas por los usuarios
 * @version 1.0
 */

@Entity
@NamedQueries({
	
	/**
	 * Consulta la cual permite listar todas las compras registradas en la base de datos
	 */
	@NamedQuery(name = Compra.LISTAR_COMPRAS, query = "select compra from Compra compra"),
	/**
	 * Consulta la cual nos permite listar las compras que ha realizado cierto usuario
	 */
	@NamedQuery(name = Compra.LISTAR_COMPRAS_USUARIO, query = "select c from Compra c where c.usuario.cedula = :cc"),
	
	@NamedQuery(name = Compra.LISTAR_COMPRAS_FECHA, query = "select c from Compra c where c.fechaCompra = :fecha"),
	
	@NamedQuery(name = Compra.CONTAR_COMPRAS, query = "select COUNT(c.ref) from Compra c ")
	
})
public class Compra implements Serializable {

	/**
	 * Constantes que identifican las consultas de Compra
	 */
	
	//Constante que identifica la consulta que lista todos las compras registrados
	public static final String LISTAR_COMPRAS = "ListarCompras";
	//Constante que identifica la consulta que lista todos las compras de determinado usuario
	public static final String LISTAR_COMPRAS_USUARIO = "ListarComprasUsuario";

	public static final String LISTAR_COMPRAS_FECHA = "ListarComprasFecha";
	
	public static final String CONTAR_COMPRAS = "ContarCompras";

	/**
	 * Relaciones de la entidad Compra
	 */

	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;

	//Relacion de uno a muchos con la entidad de detalle compras
	@OneToMany(mappedBy = "compra")
	private List<DetalleCompra> DetalleCompras;

	/**
	 * Atributos de la entidad Compra
	 */

	/**
	 * Ref autoincrementable el cual identifica cada compra registrada en la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ref",nullable = false,unique = true,updatable = false)
	private int ref;

	/**
	 * fechaCompra la cual guarda la fecha en la que fue realizada una compra en la aplicacion
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaCompra",nullable = false,updatable = false)
	private Date fechaCompra;

	/**
	 * metodo_pago enum, el cual contiene los distintos metodos de pago que acepta la plataforma
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago",nullable = false)
	private FormaPago metodo_pago;


	private static final long serialVersionUID = 1L;


	public Compra() {
		super();
	}   
	public int getRef() {
		return this.ref;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}
	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}
	public FormaPago getMetodo_pago() {
		return metodo_pago;
	}
	public void setMetodo_pago(FormaPago metodo_pago) {
		this.metodo_pago = metodo_pago;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<DetalleCompra> getDetalleCompras() {
		return DetalleCompras;
	}
	public void setDetalleCompras(List<DetalleCompra> detalleCompras) {
		DetalleCompras = detalleCompras;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ref;
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
		Compra other = (Compra) obj;
		if (ref != other.ref)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Compra [usuario=" + usuario + ", DetalleCompras=" + DetalleCompras + ", ref=" + ref + ", fechaCompra="
				+ fechaCompra + ", metodo_pago=" + metodo_pago + "]";
	}

}
