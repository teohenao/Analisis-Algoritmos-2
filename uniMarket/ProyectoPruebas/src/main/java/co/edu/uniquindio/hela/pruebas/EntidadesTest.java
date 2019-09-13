package co.edu.uniquindio.hela.pruebas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.hssf.record.formula.functions.Usdollar;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

@RunWith(Arquillian.class)
public class EntidadesTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class,"prueba.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml","META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	//*************************************************************************************************************************************
	//*****************************************************ADMINISTRADOR*******************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "ADMINISTRADOR"
	 */
	@Test
	@Transactional(value = TransactionMode.COMMIT)
	public void insertarAdministradorTest() {
		
		Administrador administrador = new Administrador();
		administrador.setCedula("123");
		administrador.setNombreCompleto("pepito pinares");
		administrador.setDireccion("valle del cauca");
		administrador.setNumeroTelefonico("31769965557");
		administrador.setEmail("epa@gmail.com");
		administrador.setClave("123");

		entityManager.persist(administrador);

		Administrador registrado = entityManager.find(Administrador.class, administrador.getCedula());
		Assert.assertEquals(administrador, registrado);
	}

	/**
	 * Buscar en la la tabla "ADMINISTRADOR" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void buscarAdministradorTest() {

		Administrador registrado = entityManager.find(Administrador.class, "1");
		Assert.assertEquals("mateohenao@gmail.com", registrado.getEmail());
	}

	/**
	 * Actualizar en la tabla "ADMINISTRADOR"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void actualizarDireccionTest() {

		Administrador administrador = entityManager.find(Administrador.class, "2");

		administrador.setDireccion("direccion actualizada");

		//merge sincroniza los datos a la tabla destino
		entityManager.merge(administrador);
	}

	/**
	 *Eliminar de la tabla "ADMINISTRADOR"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void eliminarAdministradorTest() {

		Administrador administrador = entityManager.find(Administrador.class, "1");

		entityManager.remove(administrador);

		administrador = entityManager.find(Administrador.class, "1");
		Assert.assertNull(administrador);
	}

	
	
	
	@Test
	@Transactional(value = TransactionMode.COMMIT)
	public void crearProductoTest() throws ParseException {
		
		Usuario u = new Usuario();
		u.setCedula("1");
		u.setClave("123");
		u.setDireccion("calarca caldas");
		u.setEmail("usuario1.gmail.com");
		u.setNombreCompleto("usuario1");
		u.setNumeroTelefonico("31765412122");
		
		entityManager.persist(u);
		
		Producto p = new Producto();
		p.setNombre("articulo1");
		p.setCategoria(Categoria.tecnologia);
		p.setDescripcion("descripcion1");
		p.setDisponibilidad(31);
		p.setPrecio(12.400);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date d = sdf.parse("2019-10-02");
		p.setFechaLimite(d);
		
		//List <String> img = new ArrayList<String>();
		//img.add("imagen1.pbg");
		//p.setImagenes(img);	
		
		p.getImagenes().add("imagen1.png");
		p.getImagenes().add("imagen2.png");

		Usuario user = entityManager.find(Usuario.class, "1");
		p.setUsuario(user);
		
		entityManager.persist(p);
		
		Producto registrado = entityManager.find(Producto.class, p.getId());
		Assert.assertEquals(p, registrado);
		
	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","producto.json"})
	public void productoJson(){
		
	}
	
	
	
	
	

}
