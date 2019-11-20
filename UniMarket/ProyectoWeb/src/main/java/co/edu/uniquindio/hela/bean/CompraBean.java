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
import javax.faces.context.FacesContext;
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
	public String realizarCompra(List<DetalleCompra> dc,Usuario u,String metodoPago) throws AddressException, MessagingException
	{
		if(dc.size() > 0) {
		Compra c = new Compra();
		c.setMetodo_pago(FormaPago.valueOf(FormaPago.class,metodoPago));
		Date d = new Date();
		c.setFechaCompra(d);
		c.setUsuario(u);
		adminEJB.registrarCompra(c);
		for (DetalleCompra detalleCompra : dc) {
			detalleCompra.setCompra(c);
			adminEJB.registrarDetalleCompra(detalleCompra);
		}
		sessionBean.reiniciarCarrito();
		enviarCorreoDetalleCompra(c,u,dc);
		return "/productos/Inicio?faces-redirect=true";
		}else {
			FacesMessage mensaje = new FacesMessage("por favor ingrese productos a su compra :D");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			return null;
		}
	}
	public boolean enviarCorreoDetalleCompra(Compra c,Usuario user,List<DetalleCompra> dc) throws AddressException, MessagingException
	{
		String msj = "hola "+user.getNombreCompleto()+" el detalle de su compra fue: " + "<br><br> Ref: "+c.getRef();
		double totalCompra = 0;
		for (DetalleCompra detalleCompra : dc) {
			msj = msj+"<br><br> producto: "+detalleCompra.getProducto().getNombre()+" Cantidad: "+detalleCompra.getCantidad() +" Precio: "+detalleCompra.getPrecio();
			totalCompra += totalCompra + detalleCompra.getPrecio();
		}
		msj = msj+"<br><br> TOTAL COMPRA :"+totalCompra;
		if(adminEJB.envioEmail(user, msj)){
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
