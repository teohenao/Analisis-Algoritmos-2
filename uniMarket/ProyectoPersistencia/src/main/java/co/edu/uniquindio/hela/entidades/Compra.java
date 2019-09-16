package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entidad Compra la cual contiene todo lo relevante a las compras realizadas por los usuarios
 * @version 1.0
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
	
	/**
	 * Atributos de la entidad Compra
	 */
	   
	/**
	 * Ref autoincrementable el cual identifica cada compra registrada en la base de datos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ref",nullable = false,unique = true )
	private int ref;
	
	/**
	 * fechaCompra la cual guarda la fecha en la que fue realizada una compra en la aplicacion
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "fechaCompra",nullable = false)
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
