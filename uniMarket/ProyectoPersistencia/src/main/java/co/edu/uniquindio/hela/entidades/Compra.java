package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Compras
 *
 */
@Entity

public class Compra implements Serializable {
	
	/**
	 * Relaciones de la entidad Compra
	 */
	
	//Relacion de muchos a uno con usuario
	@ManyToOne
	private Usuario usuario;
	
	//Relacion de muchos a uno con producto
	@ManyToOne
	private Producto producto;

	   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ref",nullable = false,unique = true )
	private int ref;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCompra",nullable = false)
	private Date fechaCompra;
	
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
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
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
   
}
