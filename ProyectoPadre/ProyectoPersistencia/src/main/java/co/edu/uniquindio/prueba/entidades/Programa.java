package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * @author Mateo Henao R
 * Entity implementation class for Entity: Programa
 */
@Entity

public class Programa implements Serializable {

	   
	@Id
	//autogenerar el id al realizar el mapeo, al realizar el registro, estrategia auto o puede ser desde mysql o java
	//Cre la tabla sequence donde aloja los ID para irlos comparando	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigo;
	private String nombre;
	private String descripcion;
	private String version;
	private static final long serialVersionUID = 1L;

	public Programa() {
		super();
	}   
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}   
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
   
}
