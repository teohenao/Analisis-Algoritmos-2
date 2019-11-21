package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

@FacesConfig(version = Version.JSF_2_3)
@Named("inicioBean")
@ViewScoped
public class InicioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	@ManagedProperty(value = "#{sessionBean.usuario}")
	private Usuario usuario;
	
	@EJB
	private AdministradorEJB adminEJB;
	
	private List<Producto>listaProductos;
	private List<Producto>listaTop;
	private List<Favorito>listaMisFavoritos;
	private Producto producto;
	private double precio;
	private int id,disponibilidad;
	private String nombre,categoria,descripcion,imagenInicio,inputBuscar,cantidad;
	private Date fechaLimite;
	private Producto productoSeleccionado;
	
	
	@PostConstruct 
	public void Inicializar() {
		inputBuscar = "";
		cantidad = "";
		listaProductos=adminEJB.listarProductosActivos();
		listaMisFavoritos=adminEJB.listarFavoritosUsuario(usuario.getCedula());
		listaTop = adminEJB.top3MasVendidos();
		productoSeleccionado = new Producto();
	}
	/**
	 * Metodo que permite buscar los productos activos de unimarket, para la pagina principal
	 * @return Lista productos Activos por nombre
	 */
	public List<Producto> buscarProductosNombreActivos(){
		return listaProductos = adminEJB.listarProductosNombreActivos(inputBuscar);
	}
	/**
	 * Metodo que permite listar productos por categoria que se encuentren activos para la pagina principal
	 * @param categoriaProducto
	 * @return List productos activos por categoria
	 */
	public List<Producto> productosActivosCategoria(String categoriaProducto){
		return listaProductos = adminEJB.listarProductosActivosCategoria(categoriaProducto);
	}
	/**
	 * Metodo que permite listar productos activos de unimarket, para la pagina principal
	 * @return list productos activos de unimarket
	 */
	public List<Producto> productosActivos(){
		return listaProductos = adminEJB.listarProductosActivos();
	}
	/**
	 * Metodo que permite listar los favoritos de un usuario que se encuentren activos
	 * @return lista favoritos usuario
	 */
	public List<Favorito> misFavoritos(){
		return listaMisFavoritos = adminEJB.listarFavoritosUsuario(usuario.getCedula());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AdministradorEJB getAdminEJB() {
		return adminEJB;
	}

	public void setAdminEJB(AdministradorEJB adminEJB) {
		this.adminEJB = adminEJB;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<Favorito> getListaMisFavoritos() {
		return listaMisFavoritos;
	}

	public void setListaMisFavoritos(List<Favorito> listaMisFavoritos) {
		this.listaMisFavoritos = listaMisFavoritos;
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

	public String getImagenInicio() {
		return imagenInicio;
	}

	public void setImagenInicio(String imagenInicio) {
		this.imagenInicio = imagenInicio;
	}

	public String getInputBuscar() {
		return inputBuscar;
	}

	public void setInputBuscar(String inputBuscar) {
		this.inputBuscar = inputBuscar;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Producto getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(Producto productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public List<Producto> getListaTop() {
		return listaTop;
	}
	public void setListaTop(List<Producto> listaTop) {
		this.listaTop = listaTop;
	}



}