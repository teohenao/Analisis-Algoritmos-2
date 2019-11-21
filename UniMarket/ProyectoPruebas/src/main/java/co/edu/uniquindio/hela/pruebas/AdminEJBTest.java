package co.edu.uniquindio.hela.pruebas;

import java.util.List;

import javax.ejb.EJB;
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
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import co.edu.uniquindio.hela.ejb.AdministradorEJB;
import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;


/**
 * @author Mateo Henao R,AnaMaria
 * Clase de pruebas para la capa de negocio de unimarket
 * @version 1.0
 */
@RunWith(Arquillian.class)
public class AdminEJBTest {

	@EJB
	private AdministradorEJB administradorEJB;

	@PersistenceContext
	private EntityManager entityManager;

	@Deployment
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AdministradorEJB.class)
				.addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Prueba unitaria que permite registrar un usuario en unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void registrarUsuarioTest(){

		Usuario usuario = new Usuario();
		usuario.setCedula("200");
		usuario.setClave("12345");
		usuario.setDireccion("asturias 21");
		usuario.setEmail("eail1@gamil.com");
		usuario.setNombreCompleto("samuel diaz");
		usuario.setNumeroTelefonico("3176996558");

		try {
			administradorEJB.registrarUsuario(usuario);
		}catch (InformacionRepetidaExcepcion e) {
			Assert.fail(e.getMessage());
		}catch (Exception e) {
			Assert.fail(String.format("Error inesperado %s", e.getMessage()));

		}
		Usuario registrado = entityManager.find(Usuario.class, usuario.getCedula()); 
		Assert.assertEquals(usuario, registrado);


	}


	/**
	 * prueba Test que permite determinar si un administrador inicio sesion
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void aprobarIngresoAdministrador() {
		Boolean ingreso = administradorEJB.aprobarIngresoAdmin("2", "12345");
		Assert.assertEquals(ingreso,true);
	}

	/**
	 * Prueba unitaria que permite verificar el metodo que lista los usuarios de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void listarUsuarios() {
		List<Usuario> usuarios = administradorEJB.listarUsuarios();
		Assert.assertEquals(usuarios.size(), 5);	
	}

	/**
	 * Prueba Unitaria que permite verificar la eliminacion de un usuario en la capa negocio
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void eliminarUsuario() {
		Boolean eliminado = administradorEJB.eliminarUsuario("6");
		Assert.assertEquals(eliminado,true);
	}

	/**
	 * Prueba unitaria que permite verificar la actualizacion de un usuario en la capa de negocio
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void actualizarUsuario() {
		Usuario u = entityManager.find(Usuario.class, "8");
		u.setDireccion("direccion actualizada");
		u = administradorEJB.actualizarUsuario(u);
		u = entityManager.find(Usuario.class, "8");
		Assert.assertEquals(u.getDireccion(),"direccion actualizada");
	}

	/**
	 * Prueba unitaria que permite buscar un administrador por la cedula
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void buscarAdministradorCedula() {
		Administrador admin = administradorEJB.buscarAdministradorPorCedula("2");
		Assert.assertEquals(admin.getEmail(),"daniel@gmail.com");
	}

	/**
	 * Prueba unitaria que permite listar todos los productos de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductos() {
		List<Producto> productos = administradorEJB.listarProductos();
		Assert.assertEquals(productos.size(), 15);	
	}

	/**
	 * Prueba unitaria que permite listar todos los productos vencidos de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosVencidos() {
		List<Producto> productos = administradorEJB.listarProductosVencidos();
		Assert.assertEquals(productos.size(), 13);	
	}

	/**
	 * Prueba unitaria que permite listar todos los productos activos de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosActivos() {
		List<Producto> productos = administradorEJB.listarProductosActivos();
		Assert.assertEquals(productos.size(), 10);	
	}

	/**
	 * Prueba unitaria que permite listar todos los productos por categoria en unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosCategoria() {
		List<Producto> productos = administradorEJB.listarProductosCategoria("tecnologia");
		Assert.assertEquals(productos.size(), 3);	
	}

	/**
	 * Prueba unitaria que permite listar todos los productos actuvos por categoria en unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosActivosCategoria() {
		List<Producto> productos = administradorEJB.listarProductosActivosCategoria("moda");
		Assert.assertEquals(productos.size(), 2);	
	}

	/**
	 * Prueba Unitatia que permite listar todos los productos vencidos en unimarket por categoria
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosVencidosCategoria() {
		List<Producto> productos = administradorEJB.listarProductosVencidosCategoria("moda");
		Assert.assertEquals(productos.size(), 1);	
	}

	/**
	 * Prueba unitaria que permite listar todos los productos por palabra o nombre en comun
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosNombre() {
		List<Producto> productos = administradorEJB.listarProductosNombre("cel");
		Assert.assertEquals(productos.size(), 3);	
	}
	/**
	 * Prueba unitaria que pemrite listar los productos que registro un usuario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarProductosUsuario() {
		List<Producto> productos = administradorEJB.listarProductosUsuario("6");
		Assert.assertEquals(productos.size(), 1);	
	}

	/**
	 * Prueba unitaria que permite listar los comentarios de un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json","comentario.json"})
	public void listarComentariosProducto() {
		List<Comentario> comentarios = administradorEJB.listarComentariosProducto(4);
		Assert.assertEquals(comentarios.size(), 2);	
	}

	/**
	 * Prueba unitaria que permite obtener la calificacion final de un producto
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json","calificacion.json"})
	public void obtenerCalificacionFinalProducto() {
		double calificacion = administradorEJB.calificacionFinalProducto(3);
		Assert.assertNotNull(calificacion);
	}

	/**
	 * Prueba Unitaria que permite obtener las imagenes de un producto en la capa de negocio
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"producto.json"})
	public void listarImagenesProducto() {
		List<Producto> imagenes = administradorEJB.listarImageneProducto(2);
		Assert.assertEquals(imagenes.size(), 2);	
	}


	/**
	 * Prueba Unitaria que permite obtener los 3 productos mas vendidos de unimarket
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"detalleCompra.json", "compra.json", "producto.json", "persona.json"})
	public void listarTopVendidosTest() {
		List<Object[]> top = administradorEJB.listaTopVendidos();
		Assert.assertEquals(5,top.get(0)[0]);
	}
	
	/**
	 * Prueba unitaria que permite loggear un usuario
	 */
	@Test
	@Transactional(value = TransactionMode.ROLLBACK)
	@UsingDataSet({"persona.json"})
	public void aprobarIngresoUser() {
		Boolean ingreso = administradorEJB.aprobarIngresoUser("6","12345");
		Assert.assertEquals(ingreso,true);
	}
	
	/**
	 * Prueba unitaria que permite listar los favoritos de un usuario
	 */
	public void listarFavoritosUsuario(){
		List<Favorito> lista = administradorEJB.listarFavoritosUsuario("7");
		Assert.assertEquals(lista.size(), 2);	
	}
}
