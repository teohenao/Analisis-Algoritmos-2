package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.view.ViewScoped;

@FacesConfig(version = Version.JSF_2_3)
@Named("misProductosBean")
@ViewScoped
public class MisProductosBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private AdministradorEJB adminEJB;

	@Inject
	@ManagedProperty(value = "#{sessionBean.usuario}")
	private Usuario usuario;
	
	
	private List<Producto>listaImagenesProducto;
	private List<Producto>listaProductosUsuario;
	private Producto producto;
	private double precio;
	private int id,disponibilidad;
	private String nombre,categoria,descripcion,imagenInicio,idSeleccionado;
	private Date fechaLimite;


	/**
	 * Metodo para obtener una imagen y mostrarla por cada producto
	 * @param idProducto
	 * @return Imagen producto asociado
	 */
	public String imagenInicioProducto(String idProducto) {
		int idProduct = Integer.parseInt(idProducto);
		listaImagenesProducto = adminEJB.listarImageneProducto(idProduct);
		if(listaImagenesProducto.get(0)==null) {
			return "C:\\Users\\Mateo Henao R\\eclipse-workspace\\UniMarket\\ProyectoEscritorio\\src\\main\\java\\co\\edu\\uniquindio\\hela\\utilidades\\hela.jpg";
		}else {
			return ""+listaImagenesProducto.get(0)+"";
		}	
	}
	
	/**
	 * Metodo que permite listar todos los productos que un usuario a registrado en unimarket 
	 * @return lista productos  usuario
	 */
	public List<Producto> productosUsuario(){
		return listaProductosUsuario = adminEJB.listarProductosUsuario(usuario.getCedula());
	}
	
	public String CrearProducto()
	{
		return "";
	}
	
	private Producto productoSeleccionado;


	@PostConstruct
    public void init(){
		listaProductosUsuario = adminEJB.listarProductosUsuario(usuario.getCedula());
    }



	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public List<Producto> getListaImagenesProducto() {
		return listaImagenesProducto;
	}
	public void setListaImagenesProducto(List<Producto> listaImagenesProducto) {
		this.listaImagenesProducto = listaImagenesProducto;
	}
	public String getImagenInicio() {
		return imagenInicio;
	}
	public void setImagenInicio(String imagenInicio) {
		this.imagenInicio = imagenInicio;
	}
	
	public List<Producto> getListaProductosUsuario() {
		return listaProductosUsuario;
	}
	public void setListaProductosUsuario(List<Producto> listaProductosUsuario) {
		this.listaProductosUsuario = listaProductosUsuario;
	}

	public String getIdSeleccionado() {
		return idSeleccionado;
	}

	public void setIdSeleccionado(String idSeleccionado) {
		this.idSeleccionado = idSeleccionado;
	}

	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}	
}
