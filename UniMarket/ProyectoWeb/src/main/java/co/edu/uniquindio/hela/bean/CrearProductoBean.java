package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

@Named("crearProductoBean")
@ApplicationScoped
public class CrearProductoBean{
	
	/**
	 * 
	 */
	
	private Producto producto;
	private double precio;
	private int id,disponibilidad;
	private String nombre,categoria,descripcion,imagenInicio,inputBuscar;
	private Date fechaLimite;
	
	@EJB
	private AdministradorEJB adminEJB;

	public String registrarProducto() 
	{
		System.out.println("esta en el metodo");
		Producto p = new Producto();
		p.setNombre("prueba");
		p.setDisponibilidad(1);
		p.setCategoria(Categoria.deporte);
		p.setPrecio(1.2);
		p.setDescripcion("prueba");
		Date d = new Date();
		p.setFechaLimite(d);
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
	@PostConstruct
    public void init() {
        
    }
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public AdministradorEJB getAdminEJB() {
		return adminEJB;
	}

	public void setAdminEJB(AdministradorEJB adminEJB) {
		this.adminEJB = adminEJB;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
