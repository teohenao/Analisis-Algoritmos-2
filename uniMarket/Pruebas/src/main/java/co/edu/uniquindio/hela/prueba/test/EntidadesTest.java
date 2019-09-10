package co.edu.uniquindio.hela.prueba.test;

import java.util.List;

//lo que nos permite acceder a las tablas a nivel de base de datos
import javax.persistence.EntityManager;
//hace parte del persistence manager
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	//nos permite hacer las consultas con la base de datos
	private EntityManager entityManager;

	// metodo de despliegue al cual le indicamos la entidad Relacionada
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "prueba.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	

	@Test
	@Transactional(value=TransactionMode.COMMIT)
	@UsingDataSet({"persona.json"})
	public void jsonTestPersona(){
		
		Administrador administrador = entityManager.find(Administrador.class, "1");
		Assert.assertEquals("mateohenao@gmail.com", administrador.getEmail());
		
	}
	
	@Test
	@Transactional(value=TransactionMode.COMMIT)
	@UsingDataSet({"producto.json"})
	public void jsonTestProducto(){
		
		Query q = entityManager.createNativeQuery("select * from producto", Producto.class);
		List<Producto> l = q.getResultList();
		
		System.out.println(l);
		
		Producto p = entityManager.find(Producto.class, 1);
		System.out.println(p);
		
	}
	
	@Test
	@Transactional(value = TransactionMode.COMMIT)
	public void testRegistroAdministrador() {
		
		Administrador administrador = new Administrador();
		administrador.setCedula("1094952608");
		administrador.setNombre_completo("Mateo");
		administrador.setEmail("mateohr880@gmail.com");
		administrador.setDireccion("Calarca carrera 24");
		administrador.setNumero_telefonico("3176996558");
		administrador.setClave("12345");
		entityManager.persist(administrador);
		
		Administrador registrado = entityManager.find(Administrador.class, administrador.getCedula());
		Assert.assertEquals(administrador, registrado);
		
	}
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	public void testRegistroProducto() {
		Producto producto = new Producto();
		
		producto.setNombre("computador");
		producto.setDisponibilidad(23);
		producto.setDescripcion("esta melo caramelo");
		
		entityManager.persist(producto);
		
		Query q = entityManager.createNativeQuery("select * from producto", Producto.class);
		List<Producto> l = q.getResultList();
		
		System.out.println(l);
		
		System.out.println(producto);
		
		Producto registrado = entityManager.find(Producto.class, producto.getId());
		Assert.assertEquals(producto, registrado);
	}
	

}
