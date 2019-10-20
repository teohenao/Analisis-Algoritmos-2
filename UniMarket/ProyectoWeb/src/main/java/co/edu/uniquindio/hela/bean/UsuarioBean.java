package co.edu.uniquindio.hela.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;


@Named("usuarioBean")
@ApplicationScoped
public class UsuarioBean {
	
	@EJB
	private AdministradorEJB adminEJB;
	private Usuario usuario;
	
	private String cedula;
	private String nombreCompleto;
	private String direccion;
	private String numeroTelefonico;
	private String email;
	private String clave;

	/**
	 * Metodo que permite registrar un usuario en la base de datos
	 * @return
	 * @throws InformacionRepetidaExcepcion
	 */
	public String registrarUsuario() throws InformacionRepetidaExcepcion {
		Usuario usuario = new Usuario();
		usuario.setCedula(cedula);
		usuario.setNombreCompleto(nombreCompleto);
		usuario.setDireccion(direccion);
		usuario.setNumeroTelefonico(numeroTelefonico);
		usuario.setEmail(email);
		usuario.setClave(clave);
	
		if(adminEJB.registrarUsuario(usuario)!=null) {
			FacesMessage mensaje = new FacesMessage("EXITO"+"\n"+"El Empleado "+cedula+" Se registro con exito");
			FacesContext.getCurrentInstance().addMessage(null, mensaje);
			System.out.println("se registro");
			reiniciarCampos();
			return "/productos/Inicio";
		}else {
			System.out.println("no se registro");
			return "";
		}
	}
	
	/**
	 * Metodo que permite logear a un usuario en la base de datos
	 * @return redireccion 
	 */
	public String loginUsuario() {
		if(adminEJB.aprobarIngresoUser(cedula, clave)==true) {
			reiniciarCampos();
			usuario = adminEJB.buscarUsuarioPorCedula(cedula);
			return "/productos/Inicio";
		}else {
			return "";
		}
	}
	/**
	 * Metodo que permite reiniciar los campos despues de un registro o login
	 */
	public void reiniciarCampos() {
		nombreCompleto="";
		cedula="";
		email="";
		clave="";
		direccion="";
		numeroTelefonico="";
	}
	
	
	@PostConstruct
    public void init() {
        
    }
	
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
