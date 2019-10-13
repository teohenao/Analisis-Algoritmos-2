package co.edu.uniquindio.hela.pruebas;


import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
import co.edu.uniquindio.hela.entidades.Compra;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.logica.ConsultaProductosDTO;
import co.edu.uniquindio.hela.logica.ConsultaUsuariosDTO;

/**
 * @author Mateo Henao R
 * Clase de pruebas para las consultas query o TypedQuery del proyecto unimarket
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
	 * Test de consulta que permite encontrar a una persona por medio del email.
	 */
	@Test
 	@Transactional(value = TransactionMode.ROLLBACK)
 	@UsingDataSet({ "persona.json"})
 	public void obtenerPersonaEmail() {
 
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL,Persona.class);
 		query.setParameter("email", "andrea@gmail.com");
 		Persona persona = query.getSingleResult();

 		assertEquals(persona.getCedula(), "6");
	}
	
	
	/**
	 * Test de consulta que permite obtener la persona con email y clave
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerPersonaEmailClaveTest() {

		Query query = entityManager.createNamedQuery(Persona.OBTENER_PERSONA_EMAIL_CLAVE);
		query.setParameter("email", "gabriel@gmail.com");
		query.setParameter("clave", "123456789");
		Object registrado = query.getSingleResult();

		Assert.assertNotNull(registrado);

	}

	/**
	 * Test de consulta que permite obtener la administrador con email y clave
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerAdministradorEmailClaveTest() {

		Query query = entityManager.createNamedQuery(Administrador.OBTENER_ADMIN_EMAIL_CLAVE);
		query.setParameter("email", "danielg@gmail.com");
		query.setParameter("clave", "12345");
		Object registrado = query.getSingleResult();

		Assert.assertNotNull(registrado);

	}
	/**
	 * Test de consulta que permite obtener usuario con email y clave
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void obtenerUsuarioEmailClaveTest() {

		Query query = entityManager.createNamedQuery(Usuario.OBTENER_USER_EMAIL_CLAVE);
		query.setParameter("email", "andrea@gmail.com");
		query.setParameter("clave", "12345");
		Object registrado = query.getSingleResult();

		Assert.assertNotNull(registrado);

	}
	
	

	/** 
	 * Test de consulta que permite listar todas las personas registradas en la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "persona.json" })
	public void listarPersonasTest() {

		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.LISTAR_PERSONAS,Persona.class);
		List<Persona> personas = query.getResultList();

		Assert.assertEquals(personas.size(), 10);
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

		Assert.assertEquals(administrador.size(), 5);
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
		
	}

	/** 
	 * Test consulta que permite listar todos los productos activos y vencidos de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarProductosTest() {
		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS,Producto.class);
		List<Producto> producto = query.getResultList();

		Assert.assertEquals(producto.size(), 15);
		
	} 

	/**
	 * Test de consulta que permite listar todos los productos de determinado Usuario, activos y vencidos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json","persona.json"})
	public void listarProductosUsuarioTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_USUARIO,Producto.class);
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 4);

	}

	/**
	 * Test de consulta que permite listar todos los productos activos y vencidos de determinada categoria de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json" })
	public void listarCategoriaProductoTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CATEGORIA,Producto.class);
		query.setParameter("c", "moda");
		List<Producto> producto = query.getResultList();

		Assert.assertEquals(producto.size(), 3);

	}

	/**
	 * Test de consulta que permite listar todos los comentarios de determinado producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "comentario.json","producto.json","persona.json" })
	public void listarComentariosProductoTest() {

		TypedQuery<Comentario> query = entityManager.createNamedQuery(Comentario.LISTAR_COMENTARIOS_PRODUCTO,Comentario.class);
		query.setParameter("id", 4);
		List<Comentario> comentarios = query.getResultList();

		Assert.assertEquals(comentarios.size(), 2);

	}

	/**
	 * Test de consulta que permite listar todos las calificaciones de determinado producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "calificacion.json","producto.json","persona.json" })
	public void listarCalificacionesProductoTest() {

		TypedQuery<Calificacion> query = entityManager.createNamedQuery(Calificacion.LISTAR_CALIFICACIONES_PRODUCTO,Calificacion.class);
		query.setParameter("id", 3);
		List<Calificacion> calificaciones = query.getResultList();

		Assert.assertEquals(calificaciones.size(), 3);

	}
	
	/**
	 * Test de consulta que permite listar la calificacion final de un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "calificacion.json" })
	public void listarCalificacionFinalProductoTest() {

		Query query = entityManager.createNamedQuery(Calificacion.CALIFICACION_FINAL_PRODUCTO);
		query.setParameter("id", 3);
		Object resultado = query.getSingleResult();
		double promedio = (double)resultado;
		DecimalFormat formato = new DecimalFormat("#.0");

		Assert.assertEquals("2,7",formato.format(promedio));
	}
	

	/**
	 * Test de consulta que permite listar todos las favoritos de determinado usuario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "favorito.json" })
	public void listarFavoritosUsuarioTest() {

		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.LISTAR_FAVORITOS_USUARIO,Favorito.class);
		query.setParameter("cc", "6");
		List<Favorito> favoritos = query.getResultList();

		 for (Favorito favorito: favoritos) {
			 System.out.println(favorito.getProducto().getNombre());
		 }
		Assert.assertEquals(favoritos.size(), 5);

	}
	/**
	 * Test de consulta que permite listar todos las favoritos de determinado usuario, productos Activos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "favorito.json","producto.json" })
	public void listarFavoritosUsuarioActivosTest() {

		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.LISTAR_FAVORITOS_USUARIO_ACTIVOS,Favorito.class);
		query.setParameter("cc", "6");
		query.setParameter("fechaActual", new Date());
		List<Favorito> favoritos = query.getResultList();

		Assert.assertEquals(favoritos.size(), 4);

	}
	/**
	 * Test de consulta que permite listar todos las favoritos de determinado usuario, productos Vencidos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "favorito.json","producto.json" })
	public void listarFavoritosUsuarioVencidosTest() {

		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.LISTAR_FAVORITOS_USUARIO_VENCIDOS,Favorito.class);
		query.setParameter("cc", "6");
		query.setParameter("fechaActual", new Date());
		List<Favorito> favoritos = query.getResultList();

		Assert.assertEquals(favoritos.size(), 1);

	}

	/**
	 * Test de consulta que permite listar todos los productos que se encuentran activos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosActivosTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS,Producto.class);
		query.setParameter("fechaActual", new Date());
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 11);

		
	}

	/**
	 * Test de consulta que permite listar todos los productos vencidos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosVencidosTest() {

		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS,Producto.class);
		query.setParameter("fechaActual",new Date());
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 4);

	}
	

	/**
	 * Test de consulta que permite listar todos los productos que se encuentran ACTIVOS por determinada Categoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosActivosCategoriaTest() {

		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA,Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c", "moda");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 2);

	}
	


	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosNombreTest() {

		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_NOMBRE,Producto.class);
		query.setParameter("nombre","%"+"celular"+"%");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 3);

	}

	/**
	 * Test de consulta que permite listar todos los productos que se encuentran VENCIDOS por determinada Categoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosVencidosCategoriaTest() {

		
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA,Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c","tecnologia");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 2);

	}

	/**
	 * Test de consulta que permite listar todos los productos que se encuentran ACTIVOS por el usuario creador
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosActivosUsuarioTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_USUARIO,Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 3);

	}
	/**
	 * Test de consulta que permite listar todos los productos que se encuentran VENCIDOS por el usuario creador
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "producto.json"})
	public void listarProductosVencidosUsuarioTest() {

	
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_USUARIO,Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("cc", "7");
		List<Producto> productos = query.getResultList();

		Assert.assertEquals(productos.size(), 1);
	
	}

	
	
	/** 
	 * Test consulta que permite listar todas las compras de la base de datos
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "compra.json" })
	public void listarComprasTest() {
		
		TypedQuery<Compra> query = entityManager.createNamedQuery(Compra.LISTAR_COMPRAS,Compra.class);
		List<Compra> compras = query.getResultList();

		Assert.assertEquals(compras.size(), 5);

	} 


	/**
	 * Test de consulta que permite listar las compras de determinado usuario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"compra.json","persona.json"})
	public void listarComprasUsuarioTest() {

		TypedQuery<Compra> query = entityManager.createNamedQuery(Compra.LISTAR_COMPRAS_USUARIO,Compra.class);
		query.setParameter("cc", "10");
		List<Compra> compras = query.getResultList();

		Assert.assertEquals(compras.size(), 2);

	}
	
	/**
	 * Test de consulta que permite listar todos los detalles qued eterminada compra
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json","producto.json"})
	public void listarDetallesCompraTest() {

		TypedQuery<DetalleCompra> query = entityManager.createNamedQuery(DetalleCompra.LISTAR_DETALLES_COMPRA,DetalleCompra.class);
		query.setParameter("ref", 1);
		List<DetalleCompra> detallesCompra = query.getResultList();

		Assert.assertEquals(detallesCompra.size(), 4);

		
	}
	
	/**
	 * Test de consulta que permite listar 5 productos mas vendidos de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json", "compra.json", "producto.json", "persona.json"})
	public void listarTopProductosTest() {
		
		TypedQuery<Object[]> query = entityManager.createNamedQuery(DetalleCompra.LISTAR_5PRODUCTOS_MAS_VENDIDOS, Object[].class);
		query.setMaxResults(3);
		
		Assert.assertEquals(5,query.getResultList().get(0)[0]);
		
//		for (Object[] objeto : query.getResultList()) {
//			System.out.println( objeto[0]+" "+objeto[1] );
//		}
		
	}
	
	
		
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json","compra.json","persona.json"})
	public void listarComprasFechaTest() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse("2019-10-10");
		TypedQuery<Compra> query = entityManager.createNamedQuery(Compra.LISTAR_COMPRAS_FECHA,Compra.class);
		query.setParameter("fecha",d);
		List<Compra> compra = query.getResultList();
		
		for(Compra c : compra) {
			System.out.println("id compra: "+c.getRef()+" metodo pago "+c.getMetodo_pago()+" cedula de la persona "+c.getUsuario().getCedula()
					+" email "+c.getUsuario().getEmail());
		}

		Assert.assertEquals(compra.size(), 2);	
	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "calificacion.json","producto.json","persona.json" })
	public void listarCalificacionesPromedioProductosTest() {
		
		TypedQuery<Object[]> query = entityManager.createNamedQuery(Calificacion.CALIFICACION_PROMEDIO_PRODUCTOS, Object[].class);
		
		for (Object[] objeto : query.getResultList()) {
			System.out.println( objeto[0]+" calificacion promedio del producto "+objeto[1] );
			}

	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({ "calificacion.json","producto.json","persona.json" })
	public void listarCalificacionesPromedioProductosConSinTest() {
		
		TypedQuery<Object[]> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CON_SIN_CALIFICACIONES, Object[].class);
		Object result = query.getResultList();
		System.out.println(result);
		for (Object[] objeto : query.getResultList()) {
			System.out.println( objeto );
			}

	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void productoDTOTest() {
		TypedQuery<ConsultaProductosDTO> resultados = entityManager.createNamedQuery(Producto.CONSULTA_DTO_PRODUCTO,ConsultaProductosDTO.class);
		List<ConsultaProductosDTO> resultDto = resultados.getResultList();
		
		for(ConsultaProductosDTO p : resultDto) {
			System.out.println("nombre producto: "+p.getNombre()+" descripcion "+p.getDescripcion());
		}

		
	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void usuarioDTOTest() {
		TypedQuery<ConsultaUsuariosDTO> resultados = entityManager.createNamedQuery(Usuario.CONSULTA_DTO_USUARIO,ConsultaUsuariosDTO.class);
		List<ConsultaUsuariosDTO> resultDto = resultados.getResultList();
		
		for(ConsultaUsuariosDTO u : resultDto) {
			System.out.println("nombre usuario: "+u.getNombreCompleto()+" email : "+u.getEmail());
		}
	}
	
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarImagenesProductoTest() {

		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_IMAGENES_PRODUCTO,Producto.class);
		query.setParameter("id", 1);
		
		List<Producto> productos = query.getResultList();
		for (int i = 0; i < productos.size(); i++) {
			System.out.println(productos.get(i));
		}
		
		Assert.assertEquals(productos.size(), 2);	
		
	}
			
	

}