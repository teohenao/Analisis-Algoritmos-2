package co.edu.uniquindio.hela.prueba.test;

//lo que nos permite acceder a las tablas a nivel de base de datos
import javax.persistence.EntityManager;
//hace parte del persistence manager
import javax.persistence.PersistenceContext;
//imports de arquillan
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
//imports de JUniTest
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;


import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;


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
	
	/*
	 * pruebas de Jsons
	 */
	@Test
	//ROCKBALL es usado por si es necesario revertir cambios mas adelante
	@Transactional(value=TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","producto.json"})
	public void jsonTest(){
	}
	
	/**
	 * punto 5 de la guia 5, primer metodo para probar persistencia de datos
	 */
	@Test
	//TransactionMode commit es para confirmar como permanentes las modificaciones
	@Transactional(value = TransactionMode.COMMIT)
	public void probarPersistencia() {
		
		//crear objeto tipo persona
		Administrador p = new Administrador();
		p.setCedula("1094952608");
		p.setNombre_completo("Mateo");
		p.setEmail("mateohr880@gmail.com");
		p.setDireccion("Calarca carrera 24");
		p.setNumero_telefonico("3176996558");
		p.setClave("12345");
		//para persistirlo (guardar en la base de datos)
		entityManager.persist(p);
		
		Administrador registrado = entityManager.find(Administrador.class, p.getCedula());
		Assert.assertEquals(p, registrado);
		
	}
	@Test
	@Transactional(value = TransactionMode.COMMIT)
	public void probarProducto() {
		Producto p = new Producto();
		p.setNombre("computador");
		p.setDisponibilidad(23);
		p.setDescripcion("esta melo caramelo");	
		entityManager.persist(p);
	}
	

}
