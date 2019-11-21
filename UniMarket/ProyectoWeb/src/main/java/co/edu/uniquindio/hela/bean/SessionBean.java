package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.constraints.NotNull;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

/**
 * Bean de session que maneja todo lo relacionado al usuario 
 * @author mateo,AnaMaria
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("sessionBean")
@ApplicationScoped
public class SessionBean  implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Los campos no pueden estar vacios")
	private Usuario usuario;

	@EJB
	private  AdministradorEJB adminEJB;

	private boolean autenticado;
	private String cedula,nombreCompleto,direccion,numeroTelefonico,email,clave;
	private double totalCompra;
	private int cantidadProductosCarrito;
	private ArrayList<DetalleCompra> carrito;

	@PostConstruct
	private void inicializar() {
		usuario = new Usuario();
		autenticado = false;
	}

	/**
	 * Metodo que permite logear a un usuario en la base de datos
	 * @return redireccion 
	 */
	public String loginUsuario() {
		if(adminEJB.aprobarIngresoUser(cedula, clave)==true) {
			usuario = adminEJB.buscarUsuarioPorCedula(cedula);
			autenticado = true;
			reiniciarCarrito();
			reiniciarCampos();
			return "/productos/Inicio?faces-redirect=true";
		}else {
			Util.mostrarMensaje(FacesMessage.SEVERITY_INFO, "No se realizo el inicio, verifique sus datos");
			return null;
		}
	}
	/**
	 * Metodo que permite eliminar un producto del carrito de compras
	 * @param DetalleCompra
	 */
	public void eliminarCarrito(DetalleCompra dc)
	{
		if(carrito.size()==1) {
			reiniciarCarrito();
		}else {
			for (int i = 0; i < carrito.size(); i++) {
				if(carrito.get(i).getProducto().getNombre()==dc.getProducto().getNombre()) {
					carrito.remove(i);
				}
			}
			actualizarPedido();
		}
	}
	
	/**
	 * Metodo que permite agregar un producto al carrito de compras
	 * @param Producto
	 * @return vista carrito
	 */
	public String agregarCarrito(Producto p)
	{
		if(productoRepetido(p)==false) {
			DetalleCompra dc = new DetalleCompra();
			dc.setPrecio(p.getPrecio());
			dc.setCantidad(1);
			dc.setProducto(p);
			carrito.add(dc);
			actualizarPedido();
			return "/compras/Carrito?faces-redirect=true";
		}else {
			Util.mostrarMensaje(FacesMessage.SEVERITY_INFO, "el producto "+p.getNombre()+" ya esta en el carrito");
			return null;
		}
	}
	/**
	 * Metodo que permite registrar un usuario en la base de datos
	 * @return vista inicio
	 * @throws InformacionRepetidaExcepcion
	 */
	public String registrarUsuario() throws InformacionRepetidaExcepcion {
		Usuario usuario = new Usuario();
		usuario.setCedula(cedula);
		usuario.setNombreCompleto(nombreCompleto);
		usuario.setDireccion(direccion);
		usuario.setNumeroTelefonico(numeroTelefonico);
		usuario.setEmail(email);
		usuario.setClave(clave);
		try {
			adminEJB.registrarUsuario(usuario);
			Util.mostrarMensaje(FacesMessage.SEVERITY_INFO, "EXITO"+"\n"+"El usuario "+cedula+" Se registro con exito");
			this.usuario = usuario;
			autenticado = true;
			reiniciarCampos();
			return "/productos/Inicio?faces-redirect=true";
		} catch (Exception e) {
			Util.mostrarMensaje(FacesMessage.SEVERITY_FATAL,"Lo sentimos un usuario ya existe con esa informacion");
			return null;
		}

	}

	/**
	 * Metodo que permite recuperar la contraseña por medio del email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void recuperarClave() throws AddressException, MessagingException 
	{
		Usuario user = adminEJB.buscarUsuarioPorCedula(cedula);
		if(cedula =="") {
			Util.mostrarMensaje(FacesMessage.SEVERITY_ERROR,"Ingrese una cedula");
		}else if(user==null) 
		{
			Util.mostrarMensaje(FacesMessage.SEVERITY_ERROR,"No se encontro usuario asociado a esa cedula");
		}else {
			String msj = "hola "+user.getNombreCompleto()+" se nos informo que perdio su contraseña no se asuste." + "<br><br> Su contraseña es: "+user.getClave() +"  <br>Feliz dia y no sea tan olvidadizo :D";

			if(adminEJB.envioEmail(user,msj)) 
			{
				Util.mostrarMensaje(FacesMessage.SEVERITY_INFO,"Ya puede revisar su email");
			}else {
				Util.mostrarMensaje(FacesMessage.SEVERITY_ERROR,"No se pudo enviar el email");
			}
		}
	}

	/**
	 * Metodo que permmite actualizar los datos del pedido cuando sean modificados
	 */
	public void actualizarPedido()
	{
		totalCompra = 0;
		for (DetalleCompra detalleCompra : carrito) {
			totalCompra += detalleCompra.getPrecio();
		}
		cantidadProductosCarrito = carrito.size();
	}

	/**
	 * Metodo que permite reiniciar los campos despues de un registro o login
	 */
	public void reiniciarCampos() {
		nombreCompleto="";
		cedula="";
		email="";
		clave="";
		direccion="";
		numeroTelefonico="";
	}
	/**
	 * Metodo que reinicia el carrito de compras cada vez que sea necesario
	 */
	public void reiniciarCarrito()
	{
		carrito = new ArrayList<DetalleCompra>();
		cantidadProductosCarrito = 0;
		totalCompra = 0;
	}
	/**
	 * Metodo que se encarga de verificar si un producto ya fue agregado al carrito de compras
	 * @param Producto
	 * @return true, si el producto ya fue agregado
	 */
	public boolean productoRepetido(Producto p)
	{
		for (DetalleCompra detalleCompra : carrito) {
			if(detalleCompra.getProducto().getNombre()==p.getNombre()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo que permite cerrar session
	 * @return vista inicio de unimarket
	 */
	public String cerrarSession()
	{
		autenticado = false;
		return "/inicio/UniMarket?faces-redirect=true";
	}


	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isAutenticado() {
		return autenticado;
	}
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public AdministradorEJB getAdminEJB() {
		return adminEJB;
	}

	public void setAdminEJB(AdministradorEJB adminEJB) {
		this.adminEJB = adminEJB;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public ArrayList<DetalleCompra> getCarrito() {
		return carrito;
	}

	public void setCarrito(ArrayList<DetalleCompra> carrito) {
		this.carrito = carrito;
	}


	public int getCantidadProductosCarrito() {
		return cantidadProductosCarrito;
	}

	public void setCantidadProductosCarrito(int cantidadProductosCarrito) {
		this.cantidadProductosCarrito = cantidadProductosCarrito;
	}

	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double totalCompra) {
		this.totalCompra = totalCompra;
	}



}
