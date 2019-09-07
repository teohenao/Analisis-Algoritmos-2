package co.edu.uniquindio.hela.prueba.test;

//lo que nos permite acceder a las tablas a nivel de base de datos
import javax.persistence.EntityManager;
//hace parte del persistence manager
import javax.persistence.PersistenceContext;
//imports de arquillan
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
//imports de JUniTest
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.hela.entidades.Persona;


/**
 * 
 * @author Mateo Henao R
 *
 */
//indicamos a la clase que trabaja con arquillan
@RunWith(Arquillian.class)
public class EntidadesTest {
	
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
	@Transactional(value = TransactionMode.COMMIT)
	public void probarPersistencia() {
		
		//crear objeto tipo persona
		Persona p = new Persona();
		p.setCedula("1094952608");
		p.setNombres("Mateo");
		
		//para persistirlo (guardar en la base de datos)
		entityManager.persist(p);
		
		
		
	}
	

}
