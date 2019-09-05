package co.edu.uniquindio.prueba.test;

import java.util.Date;

//lo que nos permite acceder a las tablas a nivel de base de datos
import javax.persistence.EntityManager;
//hace parte del persistence manager
import javax.persistence.PersistenceContext;
//imports de arquillan
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
//imports de JUniTest
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.prueba.entidades.Genero;
//imports de entidades
import co.edu.uniquindio.prueba.entidades.Persona;

/**
 * 
 * @author Mateo Henao R
 *
 */
//indicamos a la clase que trabaja con arquillan
@RunWith(Arquillian.class)
public class ModeloTest {

	@PersistenceContext
	private EntityManager entityManager;

	// metodo de despliegue al cual le indicamos la entidad Relacionada
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "prueba.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * punto 5 de la guia 5, primer metodo para probar persistencia de datos
	 */
	@Test
	public void probarPersistencia() {
		Persona p = new Persona();
		p.setCedula("1094952608");
		p.setNombre("Mateo");
		p.setApellido("Henao Rodriguez");
		//fecha de nacimiento de la persona, new date para tomar la fecha por medio de la libreria de java		
		p.setFechaNacimiento(new Date());
		//se hace uso del enum correspondiente para relacionarlo con la variable de genero
		p.setGenero(Genero.Masculino);
		
		
		
	}
}
