package co.edu.uniquindio.hela.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ref",nullable = false,unique = true )
	private int ref;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaCompra",nullable = false)
	private Date fechaCompra;
	
	//@Id????
	@ElementCollection
	@Column(name = "carritoCompras")
	private List<Producto> carritoCompras = new ArrayList<Producto>();
	
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
   
}
