package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Compra;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.FormaPago;
import co.edu.uniquindio.hela.entidades.Usuario;

/**
 * Bean Compra el cual registra y carga las compras asociadas
 * @author mateo,AnaMaria
 * @version 1.0
 *
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("compraBean")
@ViewScoped
public class CompraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@ManagedProperty(value = "#{sessionBean.usuario}")
	private Usuario usuario;
	
	@Inject
	@ManagedProperty(value = "#{sessionBean}")
	SessionBean sessionBean;
	
	@EJB
	private AdministradorEJB adminEJB;
	
	private List<Compra> comprasUsuario;
	
	@PostConstruct 
	public void Inicializar() {
		comprasUsuario = adminEJB.listarComprasUsuario(usuario);
	}
	/**
	 * Metodo que se encarga de registrar la compra con los detalles
	 * @param metodoPago
	 * @return vista inicio
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public String realizarCompra(String metodoPago) throws AddressException, MessagingException
	{
		List<DetalleCompra> dc = sessionBean.getCarrito();
		if(dc.size() > 0) {
		Compra c = new Compra();
		c.setMetodo_pago(FormaPago.valueOf(FormaPago.class,metodoPago));
		Date d = new Date();
		c.setFechaCompra(d);
		c.setUsuario(usuario);
		adminEJB.registrarCompra(c);
		for (DetalleCompra detalleCompra : dc) {
			detalleCompra.setCompra(c);
			adminEJB.registrarDetalleCompra(detalleCompra);
		}
		sessionBean.reiniciarCarrito();
		enviarCorreoDetalleCompra(c,dc);
		return "/productos/Inicio?faces-redirect=true";
		}else {
			Util.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Ingrese productos a su carrito");
			return null;
		}
	}
	
	/**
	 * Metodo que permite enviar un email con el detalle de la compra al usuario
	 * @param Compra
	 * @param detalleCompra
	 * @return true, si el correo se envio
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public boolean enviarCorreoDetalleCompra(Compra c,List<DetalleCompra> dc) throws AddressException, MessagingException
	{
		String msj = "hola "+usuario.getNombreCompleto()+" el detalle de su compra fue: " + "<br><br> Ref: "+c.getRef();
		double totalCompra = 0;
		for (DetalleCompra detalleCompra : dc) {
			msj = msj+"<br><br> producto: "+detalleCompra.getProducto().getNombre()+" Cantidad: "+detalleCompra.getCantidad() +" Precio: "+detalleCompra.getPrecio();
			totalCompra += totalCompra + detalleCompra.getPrecio();
		}
		msj = msj+"<br><br> TOTAL COMPRA :"+totalCompra;
		if(adminEJB.envioEmail(usuario, msj)){
			return true;
		}else {
			return false;
		}
	}

	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Compra> getComprasUsuario() {
		return comprasUsuario;
	}

	public void setComprasUsuario(List<Compra> comprasUsuario) {
		this.comprasUsuario = comprasUsuario;
	}
	
	
}
