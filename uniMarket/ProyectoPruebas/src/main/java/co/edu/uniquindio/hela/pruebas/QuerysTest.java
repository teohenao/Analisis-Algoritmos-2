package co.edu.uniquindio.hela.pruebas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.persistence.TypedQuery;


import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;


@RunWith(Arquillian.class)
public class QuerysTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class,"prueba.war").addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml","META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	/** 
	 * Test de consulta que permite listar todos los administradores de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void listarAdministradoresTest() {

		TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.LISTAR_ADMINISTRADORES,Administrador.class);
		List<Administrador> administrador = query.getResultList();
		
		Assert.assertEquals(administrador.size(), 2);

		System.out.println("----Listado de administradores-----\n");
		for (Administrador a : administrador ) {
			System.out.println(a.getNombreCompleto());
		}

	}
	
	/** 
	 * Test de consulta que permite listar todos los usuarios de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void listarUsuariosTest() {

		TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.LISTAR_USUARIOS,Usuario.class);
		List<Usuario> usuarios = query.getResultList();
		
		Assert.assertEquals(usuarios.size(), 5);

		System.out.println("----Listado de usuarios-----\n");
		for (Usuario u : usuarios ) {
			System.out.println(u.getNombreCompleto());
		}

	}
	
	/** 
	 * Test consulta que permite listar todos los productos de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarProductosTest() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS,Producto.class);
		List<Producto> producto = query.getResultList();
		
		//Assert.assertEquals(producto.size(), 3);

		System.out.println("----Listado de productos-----\n");
		for (Producto p : producto ) {
			System.out.println(p.getNombre());
		}
	}
	
	/**
	 * Metodo de consulta que permite listar todos los productos con categoria tecnologia de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarCategoriaProductoTest() {
		//select categoria from Producto where categoria like 'tecnologia'
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CATEGORIA,Producto.class);
		query.setParameter("c", "moda");
		List<Producto> producto = query.getResultList();

		//Assert.assertEquals(producto.size(), 3);

		System.out.println("----productos de tecnologia-----\n");
		for (Producto p : producto ) {
			System.out.println(p.getNombre());
		}
	}
	
	
	
	
}