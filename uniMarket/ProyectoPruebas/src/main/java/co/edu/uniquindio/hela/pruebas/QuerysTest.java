package co.edu.uniquindio.hela.pruebas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

/**
 * @author Mateo Henao R
 * Clase de pruebas para las consultas query del proyecto unimarket
 * @version 1.0
 */
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
	 * Test de consulta que permite listar todos los administradores de la base de datos---------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void listarAdministradoresTest() {

		TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.LISTAR_ADMINISTRADORES,Administrador.class);
		List<Administrador> administrador = query.getResultList();
		
		Assert.assertEquals(administrador.size(), 5);

		System.out.println("----Listado de administradores-----\n");
		for (Administrador a : administrador ) {
			System.out.println(a.getNombreCompleto());
		}		System.out.println("\n");

	}
	
	/** 
	 * Test de consulta que permite listar todos los usuarios de la base de datos----------------------------------------------------------
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
		}		System.out.println("\n");

	}
	
	/** 
	 * Test consulta que permite listar todos los productos de la base de datos------------------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarProductosTest() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS,Producto.class);
		List<Producto> producto = query.getResultList();
		
		Assert.assertEquals(producto.size(), 15);

		System.out.println("----Listado de productos-----\n");
		for (Producto p : producto ) {
			System.out.println(p.getNombre());
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos de determinado usuario---------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json","persona.json"})
	public void listarProductosUsuarioTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_USUARIO,Producto.class);
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 4);

		System.out.println("----productos de la categoria moda-----\n");
		for (Producto p : productos ) {
			System.out.println(p.getNombre()+" le pertenece al usuario "+p.getUsuario().getNombreCompleto());
		}
		System.out.println("\n");
	}
	
	/**
	 * Test de consulta que permite listar todos los productos de determinada categoria de la base de datos-------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarCategoriaProductoTest() {
		//select categoria from Producto where categoria like 'tecnologia'
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CATEGORIA,Producto.class);
		query.setParameter("c", "moda");
		List<Producto> producto = query.getResultList();

		Assert.assertEquals(producto.size(), 3);

		System.out.println("----productos de la categoria moda-----\n");
		for (Producto p : producto ) {
			System.out.println(p.getNombre());
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los comentarios de determinado producto---------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "comentario.json","producto.json","persona.json" })
	public void listarComentariosProductoTest() {

		TypedQuery<Comentario> query = entityManager.createNamedQuery(Comentario.LISTAR_COMENTARIOS_PRODUCTO,Comentario.class);
		query.setParameter("id", 4);
		List<Comentario> comentarios = query.getResultList();

		Assert.assertEquals(comentarios.size(), 2);

		System.out.println("----comentarios del producto-----\n");
		for (Comentario c : comentarios ) {
			System.out.println(c.getId()+" comentario realizado por: " + c.getUsuario().getNombreCompleto());
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos las calificaciones de determinado producto-----------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "calificacion.json","producto.json","persona.json" })
	public void listarCalificacionesProductoTest() {

		TypedQuery<Calificacion> query = entityManager.createNamedQuery(Calificacion.LISTAR_CALIFICACIONES_PRODUCTO,Calificacion.class);
		query.setParameter("id", 3);
		List<Calificacion> calificaciones = query.getResultList();

		Assert.assertEquals(calificaciones.size(), 3);

		System.out.println("----comentarios del producto-----\n");
		for (Calificacion c : calificaciones ) {
			System.out.println(c.getId()+" calificaion: "+ c.getValor() +"  realizado por: " + c.getUsuario().getNombreCompleto());
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos las favoritos de determinada persona-----------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "favorito.json","producto.json","persona.json" })
	public void listarFavoritosUsuarioTest() {

		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.LISTAR_FAVORITOS_USUARIO,Favorito.class);
		query.setParameter("cc", "6");
		List<Favorito> favoritos = query.getResultList();

		Assert.assertEquals(favoritos.size(), 5);

		System.out.println("----productos favoritos de usuario -----\n");
		for (Favorito f : favoritos ) {
			System.out.println(" usuario: "+f.getUsuario().getNombreCompleto()+" favorito : "+f.getProducto().getNombre());
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran activos---------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosActivosTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS,Producto.class);
		query.setParameter("fechaActual",fecha);
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 11);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos vigentes -----\n");
		for (Producto p: productos ) {
			System.out.println(p.getNombre()+" con fecha limite de  "+dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos vencidos-------------------------------------------------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosVencidosTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS,Producto.class);
		query.setParameter("fechaActual",fecha);
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 4);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos vencidos -----\n");
		for (Producto p: productos ) {
			System.out.println(p.getNombre()+" vencido el  "+ dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran ACTIVOS por determinada Categoria-------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosActivosCategoriaTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA,Producto.class);
		query.setParameter("fechaActual",fecha);
		query.setParameter("c", "moda");
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 2);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos activos por categoria -----\n");
		for (Producto p: productos ) {
			System.out.println("producto "+p.getNombre()+" de la categoria "+p.getCategoria() +" con fecha limite de  "+dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran VENCIDOS por determinada Categoria------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosVencidosCategoriaTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA,Producto.class);
		query.setParameter("fechaActual",fecha);
		query.setParameter("c", "tecnologia");
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 2);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos vencidos por categoria -----\n");
		for (Producto p: productos ) {
			System.out.println("producto "+p.getNombre()+" de la categoria "+p.getCategoria() +" vencido el  "+dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran ACTIVOS por el usuario creador---------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json","persona.json"})
	public void listarProductosActivosUsuarioTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_USUARIO,Producto.class);
		query.setParameter("fechaActual",fecha);
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 3);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos activos por usuario -----\n");
		for (Producto p: productos ) {
			System.out.println("producto "+p.getNombre()+" con fecha limite de  "+dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran VENCIDOS por el usuario creador--------------------------
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json","persona.json"})
	public void listarProductosVencidosUsuarioTest() {
		
		Date datosFecha = new Date();
		String fecha = new SimpleDateFormat("yyyy-MM-dd").format(datosFecha);
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_USUARIO,Producto.class);
		query.setParameter("fechaActual",fecha);
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();
		
		Assert.assertEquals(productos.size(), 1);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("----productos activos por usuario -----\n");
		for (Producto p: productos ) {
			System.out.println("producto "+p.getNombre()+" con fecha que se vencio en "+dateFormat.format(p.getFechaLimite()));
		}		System.out.println("\n");

	}
	
	
	
	
}