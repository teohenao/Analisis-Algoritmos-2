package co.edu.uniquindio.hela.logica;

public class ConsultaUsuariosDTO {
	
	private String nombreCompleto;
	private String email;
	
	public ConsultaUsuariosDTO(String nombreCompleto,String email) {
		this.nombreCompleto = nombreCompleto;
		this.email = email;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
