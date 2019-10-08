package co.edu.uniquindio.hela.logica;

public class ConsultaCantidadProductosUsuarioDTO {

	private String cedula;
	private String email;
	
	public ConsultaCantidadProductosUsuarioDTO(String cedula,String email) {
		this.cedula = cedula;
		this.email = email;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}


