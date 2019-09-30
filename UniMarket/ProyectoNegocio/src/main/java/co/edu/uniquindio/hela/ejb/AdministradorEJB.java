package co.edu.uniquindio.hela.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionInexistenteExcepcion;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

/**
 * Session Bean implementation class AdministradorEJB
 */
@Stateless
@LocalBean
public class AdministradorEJB implements AdministradorEJBRemote {

	/**
	 * Permite manejar las transacciones realizadas por parte del administrador
	 */
	@PersistenceContext  
	private EntityManager entityManager; 

	/**
	 * Constructor del EJB administrador
	 */
	public AdministradorEJB() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo para registrar y verificar si un usuario ya fue registrado en la base de datos
	 * @param usuario
	 * @return usuario
	 * @throws InformacionRepetidaExcepcion
	 */
	public Usuario registrarUsuario(Usuario usuario) throws InformacionRepetidaExcepcion{

		if (entityManager.find(Persona.class, usuario.getCedula()) != null) {
			throw new InformacionRepetidaExcepcion("La cedula " + usuario.getCedula() + " ya ha sido registrado");
		} else if (buscarPorEmail(usuario.getEmail())) {
			throw new InformacionRepetidaExcepcion("El email " + usuario.getEmail() + " ya ha sido registrado");
		}
		try {
			entityManager.persist(usuario);
			return usuario;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Buscar un usuario por el correo electronico en la base de datos
	 * @param email
	 * @return true si se obtuvo una persona con ese email
	 */
	public boolean buscarPorEmail(String email) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONA_POR_EMAIL, Persona.class);
		query.setParameter("email", email);

		return query.getResultList().size() > 0;
	}

	/**
	 * Realiza una busqueda por cedula y clave para ver si coincide con algun
	 * administrador registrado para realizar el login
	 * @param cedula
	 * @param clave
	 * @return
	 */
	public boolean aprobarIngresoAdmin(String cedula, String clave) {
		Administrador admin = buscarAdministradorPorCedula(cedula);
		if (admin != null && admin.getClave().equals(clave)) {
			return true;
		}
		return false;
	}

	
	/**
	 * Busca un administrador por cedula
	 * @param cedula
	 * @return el adminsitrador si existe, de lo contrario null
	 */
	private Administrador buscarAdministradorPorCedula(String cedula) {
		try {
			TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.BUSCAR_POR_CEDULA,Administrador.class);
			query.setParameter("cedula", cedula);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}


	/**
	 * Listar todos los usuarios de la vase de datos
	 * @return List Usuarios
	 */
	public List<Usuario> listarUsuarios() {
		TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.LISTAR_USUARIOS, Usuario.class);
		return query.getResultList();
	}

	/**
	 * Metodo para eliminar un usuario de unimarKet
	 * @param cedula
	 * @return
	 */
	public boolean eliminarUsuario(String cedula){

		if (entityManager.find(Usuario.class, cedula) != null) {
			entityManager.remove(buscarUsuarioPorCedula(cedula));
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "no se pudo eliminar, confirme que exista :P ");
			return false;
		} 
	}


	/**
	 * Busca un usuario por cedula
	 * @param cedula
	 * @return el usuario si existe, de lo contrario null
	 */
	public Usuario buscarUsuarioPorCedula(String cedula) {
		try {
			TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.BUSCAR_POR_CEDULA,Usuario.class);
			query.setParameter("cedula", cedula);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Actualizar un usuario, con sus debidas validaciones
	 * @param usuario
	 * @return usuario, de lo contrario null
	 */
	public Usuario actualizarUsuario(Usuario usuario){
		if ((entityManager.find(Usuario.class, usuario.getCedula()) != null)&&(buscarEmailEnUso(usuario.getEmail(), usuario.getCedula())!=true) ) {
			entityManager.merge(usuario);
			return usuario;
		}else {
			return null;
		}
	}

	/**
	 * Metodo para verificar si unna persona tiene un email en uso
	 * @param email
	 * @param cedula
	 * @return
	 */
	public boolean buscarEmailEnUso(String email,String cedula) {
		TypedQuery<Persona> query = entityManager.createNamedQuery(Persona.PERSONAS_EMAIL_EN_USO, Persona.class);
		query.setParameter("email", email);
		query.setParameter("cedula", cedula);

		return query.getResultList().size() > 0;
	}

	
	public Administrador buscarAdministradorEnvioCorreo(String cedula) throws InformacionInexistenteExcepcion {

		try {
			TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.BUSCAR_POR_CEDULA, Administrador.class);
			query.setParameter("cedula", cedula);
			if(query.getSingleResult()!=null) {
				return query.getSingleResult();
			}

			throw new InformacionInexistenteExcepcion("Administrador no encontrado");	

		} catch (NoResultException e) {
			return null;
		}

	}
	
	/**
	 * Listar todos los productos de la base de datos
	 * @return List Productos
	 */
	public List<Producto> listarProductos() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS, Producto.class);
		return query.getResultList();
	}

	
	/**
	 * Listar todos los productos vencidos de la base de datos
	 * @return List Productos Vencidos
	 */
	public List<Producto> listarProductosVencidos() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS, Producto.class);
		query.setParameter("fechaActual",new Date());
		return query.getResultList();
	}
	
	/**
	 * Listar todos los productos activos de la base de datos
	 * @return List Productos Vencidos
	 */
	public List<Producto> listarProductosActivos() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS, Producto.class);
		query.setParameter("fechaActual",new Date());
		return query.getResultList();
	}
	
	public List<Producto> listarProductosActivosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA, Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c", categoria);
		
		return query.getResultList();
	}
	public List<Producto> listarProductosVencidosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA, Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c", categoria);
		
		return query.getResultList();
	}
	
	public List<Producto> listarProductosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CATEGORIA, Producto.class);
		query.setParameter("c", categoria);
		
		return query.getResultList();
	}
	
	public List<Producto> listarProductosNombre(String nombreProducto){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_NOMBRE, Producto.class);
		query.setParameter("nombre","%"+nombreProducto+"%");	
		
		return query.getResultList();
	}
	
	public List<Producto> listarProductosUsuario(String ccUsuario){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_USUARIO, Producto.class);
		query.setParameter("cc",ccUsuario);	
		
		return query.getResultList();
	}
	

	public List<Comentario> listarComentariosProducto(int idProducto){
		TypedQuery<Comentario> query = entityManager.createNamedQuery(Comentario.LISTAR_COMENTARIOS_PRODUCTO, Comentario.class);
		query.setParameter("id",idProducto);	
		
		return query.getResultList();
	}
	






}
