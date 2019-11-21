package co.edu.uniquindio.hela.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.uniquindio.hela.entidades.Administrador;


/**
 * Session Bean implementation class SetupEJB
 */
@Singleton
@LocalBean
@Startup
public class SetupEJB {

	@PersistenceContext
	private EntityManager entityManager;
    /**
     * Constructor de la clase
     */
    public SetupEJB() {
    }

    /**
     * metodo que se encarga de crear un administrador en caso de que no exista y persistiendo en la base de datos
     */
    @PostConstruct
    public void install(){
    
    	long conteoAdmin = entityManager.createNamedQuery(Administrador.CONTAR_ADMINISTRADORES, Long.class).getSingleResult();
        System.out.println("numero de administradores registrados:"+conteoAdmin);
        
        if(conteoAdmin == 0) {
    		
    		Administrador administrador=new Administrador();
    		administrador.setCedula("122333");
    		administrador.setClave("12345");
    		administrador.setDireccion("calarca casa 100");
    		administrador.setEmail("m-teo770@hotmail.com");
    		administrador.setNumeroTelefonico("1231111211");
    		administrador.setNombreCompleto("Mateo Henao");
    		entityManager.persist(administrador);
    	}
    }

}
