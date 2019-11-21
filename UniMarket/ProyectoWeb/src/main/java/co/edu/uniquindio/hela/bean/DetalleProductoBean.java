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
import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

/**
 * Bean que permite cargar el detalle de un producto e iteractuar con el
 * @author mateo,AnaMaria
 * @version 1.0
 */
@FacesConfig(version = Version.JSF_2_3)
@Named("detalleProductoBean")
@ApplicationScoped
public class DetalleProductoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@EJB
	private AdministradorEJB adminEJB;
	private Producto p;
	private List<Comentario> comentariosProducto;
	private Double calificacionFinalProducto;
	private String comentario;
	private Boolean esFavorito;
	private Calificacion calificacion;
	private Integer estrellas;
	private Usuario usuario;


	/**
	 * Metodo que inicializa la vista con todos los detalles de determinado producto
	 * @param producto
	 * @param u
	 * @return
	 */
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

	/**
	 * Metodo que permite realizar comentarios de un producto
	 */
	public void comentarProducto() {
		Comentario c = new Comentario();
		c.setComentario(comentario);
		c.setProducto(p);
		c.setUsuario(usuario);
		adminEJB.comentarProducto(c);
		comentariosProducto = adminEJB.listarComentariosProducto(p.getId());
		comentario = "";
	}

	/**
	 * Metodo que permite registrar un producto como favorito
	 */
	public void registrarFavorito(){
		Favorito f = new Favorito();
		f.setProducto(p);
		f.setUsuario(usuario);
		adminEJB.registrarFavorito(f);
		esFavorito = true;
	}
	
	/**
	 * Metodo que permite eliminar un producto de los favoritos
	 */
	public void eliminarFavorito(){
		adminEJB.eliminarFavorito(usuario.getCedula(), p.getId());
		esFavorito = false;
	}

	/**
	 * Metodo que permite actualizar la calificacion de un producto
	 */
	public void actualizarCalificacion(RateEvent rateEvent)
	{
		if(calificacion == null) {
			calificacion = new Calificacion();
			calificacion.setProducto(p);
			calificacion.setUsuario(usuario);
			calificacion.setValor(estrellas);
			adminEJB.registrarCalificacion(calificacion);
			Util.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Calificacion Registrada");
		}else if (adminEJB.actualizarCalificacion(calificacion,estrellas)) {
			Util.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Calificacion Actualizada");
		}else {
			Util.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "no se pudo actualizar la calificacion");
		}
		calificacionFinalProducto = adminEJB.calificacionFinalProducto(p.getId());
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


}
