package co.edu.uniquindio.hela.bean;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.primefaces.event.RateEvent;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.FacesConfig.Version;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

@FacesConfig(version = Version.JSF_2_3)
@Named("detalleProductoBean")
@ApplicationScoped
public class DetalleProductoBean implements Serializable{
	
	private Usuario usuario;


	private static final long serialVersionUID = 1L;
	@EJB
	private AdministradorEJB adminEJB;
	private Producto p;
	private List<String> imagenesProducto;
	private List<Comentario> comentariosProducto;
	private Double calificacionFinalProducto;
	private String comentario,imagen;
	private Boolean esFavorito;
	private Calificacion calificacion;
	private Integer estrellas;
	
	

	
	public String detalleProducto(Producto producto,Usuario u) {
		p = producto;
		usuario = u;
		calificacion = adminEJB.obtenerCalificacionProductoUsuario(p,u);
		if(calificacion == null){
			calificacion = new Calificacion();
			calificacion.setProducto(p);
			calificacion.setUsuario(u);
			calificacion.setValor(0);
			adminEJB.registrarCalificacion(calificacion);
		}
		estrellas = 2;
		comentariosProducto = adminEJB.listarComentariosProducto(p.getId());
		calificacionFinalProducto = adminEJB.calificacionFinalProducto(p.getId());
		esFavorito = adminEJB.esFavorito(u.getCedula(), p.getId());
		imagen = p.getImagenes().get(0);
		return "/productos/DetalleProducto.xhtml";
	}

	public void comentarProducto() {
		Comentario c = new Comentario();
		c.setComentario(comentario);
		c.setProducto(p);
		adminEJB.comentarProducto(c,usuario);
		comentariosProducto = adminEJB.listarComentariosProducto(p.getId());
		comentario = "";
	}
	
	public void registrarFavorito(){
		Favorito f = new Favorito();
		f.setProducto(p);
		adminEJB.registrarFavorito(f,usuario);
		esFavorito = true;
	}
	public void eliminarFavorito(){
		adminEJB.eliminarFavorito(usuario.getCedula(), p.getId());
		esFavorito = false;
	}
  
	public void actualizarCalificacion()
	{

		System.out.println(estrellas);
		if(adminEJB.actualizarCalificacion(calificacion)) {
			FacesMessage mensaje = new FacesMessage("EXITO calificacion actualizada");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}else {
			FacesMessage mensaje = new FacesMessage("La calificacion no se pudo actualizar");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
	}

	@PostConstruct
	public void init(){
		

	}

	public Producto getP() {
		return p;
	}
	public void setP(Producto p) {
		this.p = p;
	}


	public List<String> getImagenesProducto() {
		return imagenesProducto;
	}


	public void setImagenesProducto(List<String> imagenesProducto) {
		this.imagenesProducto = imagenesProducto;
	}


	public List<Comentario> getComentariosProducto() {
		return comentariosProducto;
	}


	public void setComentariosProducto(List<Comentario> comentariosProducto) {
		this.comentariosProducto = comentariosProducto;
	}


	public Double getCalificacionFinalProducto() {
		return calificacionFinalProducto;
	}


	public void setCalificacionFinalProducto(Double calificacionFinalProducto) {
		this.calificacionFinalProducto = calificacionFinalProducto;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Boolean getEsFavorito() {
		return esFavorito;
	}
	public void setEsFavorito(Boolean esFavorito) {
		this.esFavorito = esFavorito;
	}

	public Integer getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Integer estrellas) {
		this.estrellas = estrellas;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	

}
