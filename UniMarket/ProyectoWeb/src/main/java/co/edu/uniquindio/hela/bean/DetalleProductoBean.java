package co.edu.uniquindio.hela.bean;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RateEvent;

import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
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
	private List<Comentario> comentariosProducto;
	private Double calificacionFinalProducto;
	private String comentario,imagen;
	private Boolean esFavorito;
	private Calificacion calificacion;
	private Integer estrellas;


	public String detalleProducto(Producto producto,Usuario u) {
		p = producto;
		usuario = u;
		calificacion = adminEJB.obtenerCalificacionProductoUsuario(p,usuario);
		estrellas = 0;
		if(calificacion != null){
			estrellas = calificacion.getValor();
		}
		comentariosProducto = adminEJB.listarComentariosProducto(p.getId());
		calificacionFinalProducto = adminEJB.calificacionFinalProducto(p.getId());
		esFavorito = adminEJB.esFavorito(usuario.getCedula(), p.getId());
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

	public void actualizarCalificacion(RateEvent rateEvent)
	{
		System.out.println( estrellas );
		if(calificacion == null) {
			calificacion = new Calificacion();
			calificacion.setProducto(p);
			calificacion.setUsuario(usuario);
			calificacion.setValor(estrellas);
			adminEJB.registrarCalificacion(calificacion);
			FacesMessage mensaje = new FacesMessage("EXITO calificacion registrada");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}else if (adminEJB.actualizarCalificacion(calificacion,estrellas)) {
			FacesMessage mensaje = new FacesMessage("EXITO calificacion actualizada");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}else {
			FacesMessage mensaje = new FacesMessage("La calificacion no se pudo actualizar");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
		}
		calificacionFinalProducto = adminEJB.calificacionFinalProducto(p.getId());
	}

	@PostConstruct
	public void init(){


	}
	
	public void mostrarEstrella( int value) {
		System.out.println("Estrellas: "+value);
	}

	public Producto getP() {
		return p;
	}
	public void setP(Producto p) {
		this.p = p;
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
