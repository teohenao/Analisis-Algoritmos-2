package co.edu.uniquindio.hela.bean;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;
import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.servlet.http.Part;

/**
 * Bean principal de la aplicacion unimarket, pagina inicio, crearProducto
 * @author mateo,AnaMaria
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("productoBean")
@ApplicationScoped
public class ProductoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AdministradorEJB adminEJB;

	private String nombre,categoria,descripcion,imagenInicio,inputBuscar,metodoPago,disponibilidad,precio;
	private Date fechaLimite;
	private UploadedFile file;

	@PostConstruct 
	public void Inicializar() {

	}

	/**
	 * Metodo que permite registrar un producto en unimarket
	 * @param usuario
	 * @return vista inicio
	 */
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
	
	/**
	 * Metodo que se encarga de obtener una imagen, cambiar su nombre y copiarla al servidor
	 * @return nombre imagen copiada
	 */
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
	
	/**
	 * Metodo que elimina un producto seleccionado
	 * @param producto
	 */
	public void eliminarProducto(Producto p)
	{
		if(adminEJB.eliminarProducto(p)!=false)
		{
			System.out.println("se elimino");
		}else {
			System.out.println("nada :( ");
		}
	}
	
	/**
	 * Metodo que se encarga de limpiar los campos despues de registrarlo
	 */
	public void limpiarCampos()
	{
		nombre = "";
		disponibilidad = "";
		categoria = "";
		precio = "";
		descripcion = "";
		fechaLimite = null;
	}


	
	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
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
	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}


}
