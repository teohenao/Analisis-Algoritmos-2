package co.edu.uniquindio.hela.bean;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

/**
 * Bean principal de la aplicacion unimarket
 * @author mateo
 */
@Named
@ApplicationScoped
public class ProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Instancia de la capa negocio en el bean para utilizar sus metodos
	 */
	@EJB
	private AdministradorEJB adminEJB;

	private String ccUsuario = "1";
	
	private List<Producto>listaProductos;
	private List<Producto>listaImagenesProducto;
	private List<Favorito>listaMisFavoritos;
	private Producto producto;
	private double precio;
	private int id,disponibilidad;
	private String nombre,categoria,descripcion,imagenInicio,inputBuscar;
	private Date fechaLimite;

	
	private Producto productoSeleccionadoUsuario;
	
	

	@PostConstruct 
	public void Inicializar() {
		inputBuscar = "";
		listaProductos=adminEJB.listarProductosActivos();
		listaMisFavoritos=adminEJB.listarFavoritosUsuario(ccUsuario);
		productoSeleccionadoUsuario = new Producto();
		
	}
	
	public String registrarProducto() 
	{
		
		Producto p = new Producto();
		p.setNombre(nombre);
		p.setDisponibilidad(disponibilidad);
		p.setCategoria(Categoria.valueOf(Categoria.class,categoria));
		p.setPrecio(precio);
		p.setDescripcion(descripcion);
		p.setFechaLimite(fechaLimite);
		Usuario u = adminEJB.buscarUsuarioPorCedula("1");
		p.setUsuario(u);
		
		if(adminEJB.registrarProducto(p)) {
			 return "/productos/Inicio";
		}else
		{
			System.out.println("llorela");
			return null;
		}
	}
	
	/**
	 * Metodo que permite actualizar el producto creado por un usuario
	 */
	public void actualizarProducto(){
		Producto p = new Producto();
		p.setId(productoSeleccionadoUsuario.getId());
		p.setNombre(productoSeleccionadoUsuario.getNombre());
		p.setDescripcion(productoSeleccionadoUsuario.getDescripcion());
		p.setPrecio(productoSeleccionadoUsuario.getPrecio());
		p.setDisponibilidad(productoSeleccionadoUsuario.getDisponibilidad());
		p.setFechaLimite(productoSeleccionadoUsuario.getFechaLimite());
		p.setCategoria(productoSeleccionadoUsuario.getCategoria());
		p.setUsuario(productoSeleccionadoUsuario.getUsuario());
		
		adminEJB.actualizarProducto(p);
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
		return listaMisFavoritos = adminEJB.listarFavoritosUsuario(ccUsuario);
	}

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

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
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
	public String getInputBuscar() {
		return inputBuscar;
	}
	public void setInputBuscar(String inputBuscar) {
		this.inputBuscar = inputBuscar;
	}
	public String getCcUsuario() {
		return ccUsuario;
	}
	public void setCcUsuario(String ccUsuario) {
		this.ccUsuario = ccUsuario;
	}
	public List<Favorito> getListaMisFavoritos() {
		return listaMisFavoritos;
	}
	public void setListaMisFavoritos(List<Favorito> listaMisFavoritos) {
		this.listaMisFavoritos = listaMisFavoritos;
	}
	public Producto getProductoSeleccionadoUsuario() {
		return productoSeleccionadoUsuario;
	}
	public void setProductoSeleccionadoUsuario(Producto productoSeleccionadoUsuario) {
		this.productoSeleccionadoUsuario = productoSeleccionadoUsuario;
	}



	
}
