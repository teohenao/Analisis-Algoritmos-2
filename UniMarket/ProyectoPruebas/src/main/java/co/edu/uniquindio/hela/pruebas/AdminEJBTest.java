package co.edu.uniquindio.hela.pruebas;

import javax.ejb.EJB;

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
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;



@RunWith(Arquillian.class)
public class AdminEJBTest {

	@EJB
	private AdministradorEJB administradorEJB;

	@Deployment
	public static Archive<?> createDeploymentPackage() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AdministradorEJB.class)
				.addPackage(Persona.class.getPackage())
				.addAsResource("persistenceForTest.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	/**
	 * Test que permite registrar un empleado en la capa negocio
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

	}

}
