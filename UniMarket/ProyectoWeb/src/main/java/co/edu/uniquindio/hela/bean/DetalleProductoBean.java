package co.edu.uniquindio.hela.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;


import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Producto;


@Named
@ApplicationScoped
public class DetalleProductoBean{

	@EJB
	private AdministradorEJB adminEJB;
	private Producto p;
	private List<String> imagenesProducto;
	private List<Comentario> comentariosProducto;
	private Double calificacionFinalProducto;
	private String comentario;
	private Boolean esFavorito;
	
	/**
	 * Metodo que se llama desde la vista inicio y carga lo referente a el producto seleccionado para mostrar sus detalles
	 * @param producto
	 * @return
	 */
	public String detalleProducto(Producto producto) {
		p = producto;
		comentariosProducto = adminEJB.listarComentariosProducto(2);
		calificacionFinalProducto = adminEJB.calificacionFinalProducto(p.getId());
		esFavorito = adminEJB.esFavorito("1", p.getId());
		return "/productos/DetalleProducto.xhtml";
	}

	/**
	 * Metodo que permite comentar un producto de unimarket
	 */
	public void comentarProducto() {
		Comentario c = new Comentario();
		c.setComentario(comentario);
		c.setProducto(p);
		adminEJB.comentarProducto(c);
		comentariosProducto = adminEJB.listarComentariosProducto(p.getId());
		comentario = "";
	}
	
	/**
	 * Metodo que permite registrar el producto en la lista de favoritos del usuario
	 */
	public void registrarFavorito(){
		Favorito f = new Favorito();
		f.setProducto(p);
		adminEJB.registrarFavorito(f);
		esFavorito = true;
	}
	/**
	 * Metodo que permite eliminar un producto de la lista de favoritos del usuario
	 */
	public void eliminarFavorito(){
		adminEJB.eliminarFavorito("1", p.getId());
		esFavorito = false;
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


}