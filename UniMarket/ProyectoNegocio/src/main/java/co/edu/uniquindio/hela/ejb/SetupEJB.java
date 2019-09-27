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
     * Default constructor. 
     */
    public SetupEJB() {
        // TODO Auto-generated constructor stub
    }

    /**
     * metodo que se encarga de crear un administrador en caso de que no exista
     */
    @PostConstruct
    public void install(){
    
    	long conteoAdmin = entityManager.createNamedQuery(Administrador.CONTAR_ADMINISTRADORES, Long.class).getSingleResult();
        System.out.println("numero de administradores registrados:"+conteoAdmin);
        
        if(conteoAdmin == 0) {
    		
    		Administrador administrador=new Administrador();
    		administrador.setCedula("123");
    		administrador.setClave("12345");
    		administrador.setDireccion("calarca casa 100");
    		administrador.setEmail("mateohr880@gmail.com");
    		administrador.setNumeroTelefonico("1231111211");
    		administrador.setNombreCompleto("admin1");
    		entityManager.persist(administrador);
    	}
    }

}
