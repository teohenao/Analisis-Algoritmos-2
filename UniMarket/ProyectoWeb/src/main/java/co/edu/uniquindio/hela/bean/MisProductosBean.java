package co.edu.uniquindio.hela.bean;

import java.io.Serializable;
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

/**
 * Bean que se encarga de mostrar los productos de un usuario
 * @author mateo,AnaMaria
 * @version 1.0
 */
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
	
	private List<Producto>listaProductosUsuario;

	
	/**
	 * Metodo que permite listar todos los productos que un usuario a registrado en unimarket 
	 * @return lista productos  usuario
	 */
	public List<Producto> productosUsuario(){
		return listaProductosUsuario = adminEJB.listarProductosUsuario(usuario.getCedula());
	}
	

	@PostConstruct
    public void init(){
		listaProductosUsuario = adminEJB.listarProductosUsuario(usuario.getCedula());
    }


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public List<Producto> getListaProductosUsuario() {
		return listaProductosUsuario;
	}


	public void setListaProductosUsuario(List<Producto> listaProductosUsuario) {
		this.listaProductosUsuario = listaProductosUsuario;
	}


}