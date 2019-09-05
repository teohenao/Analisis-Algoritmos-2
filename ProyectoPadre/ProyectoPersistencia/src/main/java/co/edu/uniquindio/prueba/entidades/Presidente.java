package co.edu.uniquindio.prueba.entidades;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Presidente
 *
 */
@Entity
@DiscriminatorValue("Presidente")
//Extends para que extienda atributos que tiene persona
public class Presidente extends Persona{
	
	//relacion de uno a uno con pais, es necesario que una de las dos este mapeada 
	//para recibir y enviar
	@OneToOne
	private Pais pais;

	private static final long serialVersionUID = 1L;

	public Presidente() {
		super();
	}   
	
   
}
