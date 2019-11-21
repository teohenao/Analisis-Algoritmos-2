package co.edu.uniquindio.hela.bean;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.servlet.http.Part;

/**
 * Bean principal de la aplicacion unimarket
 * @author mateo
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("productoBean")
@ApplicationScoped
public class ProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AdministradorEJB adminEJB;


	private List<Producto>listaProductos;
	private List<Producto>listaImagenesProducto;
	private List<Favorito>listaMisFavoritos;
	private Producto producto;
	private int id;
	private String nombre,categoria,descripcion,imagenInicio,inputBuscar,metodoPago,disponibilidad,precio;
	private Date fechaLimite;
	private Producto productoSeleccionadoUsuario;
	private UploadedFile file;

	@PostConstruct 
	public void Inicializar() {

	}

	public String registrarProducto(Usuario u) 
	{
		Producto p = new Producto();
		p.setNombre(nombre);
		p.setDisponibilidad(Integer.parseInt(disponibilidad));
		p.setCategoria(Categoria.valueOf(Categoria.class,categoria));
		p.setPrecio(Double.parseDouble(precio));
		p.setDescripcion(descripcion);
		p.setFechaLimite(fechaLimite);
		p.setUsuario(u);
		String imagen = saveFile();
		if(imagen != null) {
			p.getImagenes().add(imagen);
		}
		if(adminEJB.registrarProducto(p)) {
			limpiarCampos();
			return "/productos/Inicio?faces-redirect=true";
		}else
		{
			return null;
		}
	}

	private Part uploadedFile;
	private String folder = "/home/mateo/eclipse/jee-2019-09/eclipse/server/glassfish5.1/glassfish/domains/domain1/docroot/";

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


	public String saveFile(){
	    UUID idImg = UUID.randomUUID();

		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = ""+idImg;
			Files.copy(input, new File(folder, fileName).toPath());
			return fileName;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void eliminarProducto(Producto p)
	{
		if(adminEJB.eliminarProducto(p)!=false)
		{
			System.out.println("se elimino");
		}else {
			System.out.println("nada :( ");
		}
	}
	

	public void limpiarCampos()
	{
		nombre = "";
		disponibilidad = "";
		categoria = "";
		precio = "";
		descripcion = "";
		fechaLimite = null;
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



	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}


}
