package co.edu.uniquindio.prueba.entidades;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Automovil
 *
 */
@Entity

public class Automovil implements Serializable {
	
	//Relacion de Muchos a uno
	//entidad propietaria, ya que varios automoviles pertenecen a una marca
	@ManyToOne
	private Marca marca;

	   
	@Id
	private String codigo;
	private String placa;
	private String color;
	private String categoria;
	private static final long serialVersionUID = 1L;

	public Automovil() {
		super();
	}   
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}   
	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}   
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}   
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
   
}
