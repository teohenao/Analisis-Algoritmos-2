package co.edu.uniquindio.hela.pruebas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Compra;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.FormaPago;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;

/**
 * @author Mateo Henao R, Ana Maria Latorre
 * Clase de pruebas para el CRUD de entidades identificacdas en el proyecto unimarket
 * @version 1.0
 */
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
	@Transactional(value = TransactionMode.ROLLBACK)
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
	public void actualizarAdministradorTest() {

		Administrador administrador = entityManager.find(Administrador.class, "2");
		administrador.setDireccion("direccion actualizada");
		entityManager.merge(administrador);
		administrador = entityManager.find(Administrador.class, "2");

		Assert.assertEquals("direccion actualizada", administrador.getDireccion());

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



	//*************************************************************************************************************************************
	//********************************************************USUARIO**********************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "USUARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	public void insertarUsuarioTest() {

		Usuario u = new Usuario();
		u.setCedula("111");
		u.setNombreCompleto("oskar alejandro ortiz");
		u.setDireccion("cucuta");
		u.setNumeroTelefonico("31769543557");
		u.setEmail("oskar@gmail.com");
		u.setClave("123");
		entityManager.persist(u);
		Usuario registrado = entityManager.find(Usuario.class, u.getCedula());

		Assert.assertEquals(u, registrado);
	}

	/**
	 * Buscar en la la tabla "USUARIO" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void buscarUsuarioTest() {

		Usuario registrado = entityManager.find(Usuario.class, "6");

		Assert.assertEquals("andrea@gmail.com", registrado.getEmail());
	}

	/**
	 * Actualizar en la tabla "USUARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void actualizarUsuarioTest() {

		Usuario u = entityManager.find(Usuario.class, "6");
		u.setDireccion("direccion actualizada");
		entityManager.merge(u);
		u = entityManager.find(Usuario.class, "6");

		Assert.assertEquals("direccion actualizada", u.getDireccion());

	}

	/**
	 *Eliminar de la tabla "USUARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void eliminarUsuarioTest() {

		Usuario user = entityManager.find(Usuario.class, "7");
		entityManager.remove(user);
		user = entityManager.find(Usuario.class, "7");

		Assert.assertNull(user);
	}


	//*************************************************************************************************************************************
	//*****************************************************PRODUCTO************************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "PRODUCTO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void insertarProductoTest() throws ParseException {

		Producto p = new Producto();
		p.setId(1);
		p.setCategoria(Categoria.tecnologia);
		p.setDescripcion("descripcion1");
		p.setDisponibilidad(31);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse("2019-10-02");
		p.setFechaLimite(d);
		p.setNombre("articulo1");
		p.setPrecio(12.400);
		p.getImagenes().add("imagen1.png");
		p.getImagenes().add("imagen2.png");
		Usuario user = entityManager.find(Usuario.class, "7");
		p.setUsuario(user);
		entityManager.persist(p);
		Producto registrado = entityManager.find(Producto.class, p.getId());

		Assert.assertEquals(p, registrado);
	}

	/**
	 * Buscar en la la tabla "PRODUCTO" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void buscarProductoTest() {

		Producto registrado = entityManager.find(Producto.class , 3);

		Assert.assertEquals("moda", registrado.getCategoria().toString());
	}

	/**
	 * Actualizar en la tabla "PRODUCTO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void actualizarProductoTest() {

		Producto p = entityManager.find(Producto.class, 2);
		p.setDisponibilidad(100);
		entityManager.merge(p);
		p = entityManager.find(Producto.class, 2);

		Assert.assertEquals(100, p.getDisponibilidad());

	}

	/**
	 *Eliminar de la tabla "Producto"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void eliminarProductoTest() {

		Producto p = entityManager.find(Producto.class, 3);
		entityManager.remove(p);
		p = entityManager.find(Producto.class, 3);

		Assert.assertNull(p);
	}




	//*************************************************************************************************************************************
	//*****************************************************CALIFICACION********************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "CALIFICACION"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","producto.json"})
	public void insertarCalificacionTest() {

		Usuario u = entityManager.find(Usuario.class, "9");
		Producto p = entityManager.find(Producto.class, 3 );
		Calificacion c = new Calificacion();
		c.setId(1);
		c.setProducto(p);
		c.setUsuario(u);
		c.setValor(5);
		entityManager.persist(c);
		Calificacion registrado = entityManager.find(Calificacion.class, c.getId());

		Assert.assertEquals(c, registrado);
	}

	/**
	 * Buscar en la la tabla "CALIFICACION" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"calificacion.json"})
	public void buscarCalificacionTest() {

		Calificacion registrado = entityManager.find(Calificacion.class, 1);
		
		Assert.assertEquals(5, registrado.getValor());
	}

	/**
	 * Actualizar en la tabla "CALIFICACION"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"calificacion.json"})
	public void actualizarCalificacionTest() {

		Calificacion c = entityManager.find(Calificacion.class, 2);
		c.setValor(3);
		entityManager.merge(c);
		c = entityManager.find(Calificacion.class, 2);

		Assert.assertEquals(3, c.getValor());
	}

	/**
	 *Eliminar de la tabla "CALIFICACION"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"calificacion.json"})
	public void eliminarCalificacionTest() {

		Calificacion c = entityManager.find(Calificacion.class, 2);
		entityManager.remove(c);
		c = entityManager.find(Calificacion.class, 2);

		Assert.assertNull(c);
	}



	//*************************************************************************************************************************************
	//*****************************************************COMENTARIO**********************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "COMENTARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","producto.json"})
	public void insertarComentarioTest() {

		Usuario u = entityManager.find(Usuario.class, "9");
		Producto p = entityManager.find(Producto.class, 3 );
		Comentario c = new Comentario();
		c.setId(1);
		c.setProducto(p);
		c.setUsuario(u);
		c.setComentario("este es el comentario de prueba");
		entityManager.persist(c);
		Comentario registrado = entityManager.find(Comentario.class, c.getId());

		Assert.assertEquals(c, registrado);
	}

	/**
	 * Buscar en la la tabla "COMENTARIO" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"comentario.json"})
	public void buscarComentarioTest() {

		Comentario registrado = entityManager.find(Comentario.class, 3);

		Assert.assertEquals("comentario 3", registrado.getComentario());
	}

	/**
	 * Actualizar en la tabla "COMENTARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"comentario.json"})
	public void actualizarComentarioTest() {

		Comentario c = entityManager.find(Comentario.class, 2);
		c.setComentario("comentario actualizado");
		entityManager.merge(c);
		c = entityManager.find(Comentario.class, 2);

		Assert.assertEquals("comentario actualizado", c.getComentario());

	}

	/**
	 *Eliminar de la tabla "COMENTARIO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"comentario.json"})
	public void eliminarComentarioTest() {

		Comentario c = entityManager.find(Comentario.class, 2);
		entityManager.remove(c);
		c = entityManager.find(Comentario.class, 2);

		Assert.assertNull(c);
	}




	//*************************************************************************************************************************************
	//*****************************************************FAVORITO************************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "FAVORITO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json","producto.json"})
	public void insertarFavoritoTest() {

		Usuario u = entityManager.find(Usuario.class, "6");
		Producto p = entityManager.find(Producto.class, 2 );
		Favorito f = new Favorito();
		f.setId(1);
		f.setProducto(p);
		f.setUsuario(u);
		entityManager.persist(f);
		Favorito registrado = entityManager.find(Favorito.class, f.getId());

		Assert.assertEquals(f, registrado);
	}

	/**
	 * Buscar en la la tabla "FAVORITO" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"favorito.json"})
	public void buscarFavoritoTest() {

		Favorito registrado = entityManager.find(Favorito.class, 3);

		Assert.assertEquals("7" , registrado.getUsuario().getCedula().toString());
	}

	/**
	 * Actualizar en la tabla "FAVORITO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"favorito.json","producto.json"})
	public void actualizarFavoritoTest() {

		Producto p = entityManager.find(Producto.class, 5);
		Favorito f = entityManager.find(Favorito.class, 2);
		f.setProducto(p);;
		entityManager.merge(f);
		p = entityManager.find(Producto.class, 5);

		Assert.assertEquals(5 , f.getProducto().getId() );

	}

	/**
	 *Eliminar de la tabla "FAVORITO"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"favorito.json"})
	public void eliminarFavoritoTest() {

		Favorito f = entityManager.find(Favorito.class, 3);
		entityManager.remove(f);
		f = entityManager.find(Favorito.class, 3);

		Assert.assertNull(f);
	}



	//*************************************************************************************************************************************
	//*******************************************************COMPRAS***********************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void insertarCompraTest() {

		Usuario u = entityManager.find(Usuario.class, "7");
		Compra c = new Compra();
		c.setRef(1);
		c.setMetodo_pago(FormaPago.paypal);
		c.setFechaCompra(new Date());
		c.setUsuario(u);
		entityManager.persist(c);
		Compra registrado = entityManager.find(Compra.class, c.getRef());

		Assert.assertEquals(c, registrado);
	}

	/**
	 * Buscar en la la tabla "COMPRA" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"compra.json",})
	public void buscarCompraTest() {

		Compra registrado = entityManager.find(Compra.class, 2);

		Assert.assertEquals("paypal" , registrado.getMetodo_pago().toString());
	}

	/**
	 * Actualizar en la tabla "COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"compra.json"})
	public void actualizarCompraTest(){

		Compra c = entityManager.find(Compra.class, 2);
		c.setMetodo_pago(FormaPago.bitcoin);
		entityManager.merge(c);
		c = entityManager.find(Compra.class, 2);

		Assert.assertEquals("bitcoin" ,c.getMetodo_pago().toString() );

	}

	/**
	 *Eliminar de la tabla "COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"compra.json"})
	public void eliminarCompraTest() {

		Compra c = entityManager.find(Compra.class, 2);
		entityManager.remove(c);
		c = entityManager.find(Compra.class, 2);

		Assert.assertNull(c);

	}

	//*************************************************************************************************************************************
	//**************************************************DETALLE-COMPRA*******************************************************************
	//*************************************************************************************************************************************

	/**
	 * Insertar en la tabla "DETALLE COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json","compra.json"})
	public void insertarDetalleCompraTest() {

		Producto p = entityManager.find(Producto.class, 3 );
		Compra c = entityManager.find(Compra.class, 1);
		DetalleCompra dc = new DetalleCompra();
		dc.setId(1);
		dc.setCantidad(3);
		dc.setPrecio(12.000);
		dc.setCompra(c);
		dc.setProducto(p);
		entityManager.persist(dc);
		DetalleCompra registrado = entityManager.find(DetalleCompra.class,dc.getId());

		Assert.assertEquals(dc, registrado);
	}

	/**
	 * Buscar en la la tabla "DETALLE COMPRA" utilizando JSON
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json",})
	public void buscarDetalleCompraTest() {

		DetalleCompra registrado = entityManager.find(DetalleCompra.class, 2);

		Assert.assertEquals(4 , registrado.getCantidad());
	}

	/**
	 * Actualizar en la tabla "DETALLE COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json"})
	public void actualizarDetalleCompraTest(){

		DetalleCompra dc = entityManager.find(DetalleCompra.class, 5);
		dc.setCantidad(10);
		entityManager.merge(dc);
		dc = entityManager.find(DetalleCompra.class, 5);

		Assert.assertEquals( 10 ,dc.getCantidad());

	}

	/**
	 *Eliminar de la tabla "DETALLE COMPRA"
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json"})
	public void eliminarDetalleCompraTest() {

		DetalleCompra dc = entityManager.find(DetalleCompra.class, 2);
		entityManager.remove(dc);
		dc = entityManager.find(DetalleCompra.class, 2);

		Assert.assertNull(dc);

	}

}
