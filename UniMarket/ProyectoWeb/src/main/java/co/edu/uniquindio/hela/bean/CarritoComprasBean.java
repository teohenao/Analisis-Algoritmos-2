package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Producto;

@Named
@ApplicationScoped
public class CarritoComprasBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AdministradorEJB adminEJB;
	
	private List<DetalleCompra> carrito = new ArrayList<DetalleCompra>();

	
	public String añadirProductoCarrito(Producto p) {
		DetalleCompra dc = new DetalleCompra();
		dc.setCantidad(1);
		dc.setPrecio(p.getPrecio());
		dc.setProducto(p);
		carrito.add(dc);
		System.out.println(carrito);
		return "/compras/Carrito.xhtml";
	}
	
	@PostConstruct 
	public void Inicializar() {
		
	}
	
	
	

	public List<DetalleCompra> getCarrito() {
		return carrito;
	}
	public void setCarrito(List<DetalleCompra> carrito) {
		this.carrito = carrito;
	}
	
}


