package co.edu.uniquindio.hela.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;


import co.edu.uniquindio.hela.entidades.Administrador;
import co.edu.uniquindio.hela.entidades.Calificacion;
import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Compra;
import co.edu.uniquindio.hela.entidades.DetalleCompra;
import co.edu.uniquindio.hela.entidades.Favorito;
import co.edu.uniquindio.hela.entidades.Persona;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;

/**
 * Clase encargada de la capa de negocio, donde se encuentran los metodos que se implementaran en las otras capas
 * @author mateo,AnaMaria
 * @version 1.0
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
		}else if(buscarEmailEnUso(usuario.getEmail(),usuario.getCedula())) {
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
	 * Realiza una busqueda por cedula y clave para ver si coincide con algun
	 * administrador registrado para realizar el login a unimarket
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
	public Administrador buscarAdministradorPorCedula(String cedula) {
		try {
			TypedQuery<Administrador> query = entityManager.createNamedQuery(Administrador.BUSCAR_POR_CEDULA,Administrador.class);
			query.setParameter("cedula", cedula);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Listar todos los usuarios de la base de datos
	 * @return List Usuarios
	 */
	public List<Usuario> listarUsuarios() {
		TypedQuery<Usuario> query = entityManager.createNamedQuery(Usuario.LISTAR_USUARIOS, Usuario.class);
		return query.getResultList();
	}

	/**
	 * Metodo para eliminar un usuario de unimarKet
	 * @param cedula
	 * @return true si el usuario fue eliminado
	 */
	public boolean eliminarUsuario(String cedula){
		try {

			Usuario u = entityManager.find(Usuario.class, cedula);

			if ( u != null) {
				entityManager.remove(u);
				return true;
			}else {
				JOptionPane.showMessageDialog(null, "no se pudo eliminar, confirme que exista :P ");
				return false;
			} 
		} catch (NoResultException e) {
			e.printStackTrace();
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
			Usuario u = query.getSingleResult();
			return u;

		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Actualizar un usuario de la base de datos
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
	 * Metodo para verificar si una persona tiene un email en uso
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
	 * @return List Productos activos
	 */
	public List<Producto> listarProductosActivos() {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS, Producto.class);
		query.setParameter("fechaActual",new Date());
		return query.getResultList();
	}

	/**
	 * Listar productos que se encuentran activos, por categoria
	 * @param categoria
	 * @return List Productos Activos Categoria
	 */
	public List<Producto> listarProductosActivosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_ACTIVOS_CATEGORIA, Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c", categoria);

		return query.getResultList();
	}

	/**
	 * Metodo que permite listar los productos vencidos, por categoria
	 * @param categoria
	 * @return List Productos Vencidos Categoria
	 */
	public List<Producto> listarProductosVencidosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_VENCIDOS_CATEGORIA, Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("c", categoria);

		return query.getResultList();
	}

	/**
	 * Metodo que permite listar productos por categoria
	 * @param categoria
	 * @return List Productos Categoria
	 */
	public List<Producto> listarProductosCategoria(String categoria){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_CATEGORIA, Producto.class);
		query.setParameter("c", categoria);

		return query.getResultList();
	}

	/**
	 * Metodo que permite listar los productos, por nombre o coincidencias
	 * @param nombreProducto
	 * @return Lista filtrada por nombre
	 */
	public List<Producto> listarProductosNombre(String nombreProducto){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_NOMBRE, Producto.class);
		query.setParameter("nombre","%"+nombreProducto+"%");	

		return query.getResultList();
	}

	/**
	 * Metodo que permite listar los productos de un usuario
	 * @param ccUsuario
	 * @return list Productos Usuario
	 */
	public List<Producto> listarProductosUsuario(String ccUsuario){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_USUARIO, Producto.class);
		query.setParameter("cc",ccUsuario);	

		return query.getResultList();
	}


	/**
	 * Metodo que permite listar los comentarios de un producto
	 * @param idProducto
	 * @return List Comentarios Producto
	 */
	public List<Comentario> listarComentariosProducto(int idProducto){
		TypedQuery<Comentario> query = entityManager.createNamedQuery(Comentario.LISTAR_COMENTARIOS_PRODUCTO, Comentario.class);
		query.setParameter("id",idProducto);	

		return query.getResultList();
	}

	/**
	 * Metodo que permite obtener la calificacion final de un producto
	 * @param idProducto
	 * @return double, calificacion final
	 */
	public double calificacionFinalProducto(int idProducto){
		if(listarCalificacionesProducto(idProducto)) {
			Query query = entityManager.createNamedQuery(Calificacion.CALIFICACION_FINAL_PRODUCTO);
			query.setParameter("id", idProducto);
			Object resultado = query.getSingleResult();
			double promedio = (double)resultado;

			return promedio;
		}else {
			return 0;
		}
	}	

	/**
	 * Metodo que permite listar las calificaciones de un producto para determinar si tiene o no 
	 * @param idProducto
	 * @return true si el producto tiene calificaciones
	 */
	public Boolean listarCalificacionesProducto(int id) {

		TypedQuery<Calificacion> query = entityManager.createNamedQuery(Calificacion.LISTAR_CALIFICACIONES_PRODUCTO,Calificacion.class);
		query.setParameter("id", id);

		return query.getResultList().size() > 0;
	}

	/**
	 * Metodo que permite listar las imagenes de un producto
	 * @param idProducto
	 * @return List Imagenes Producto
	 */
	public List<Producto> listarImageneProducto(int id) {
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_IMAGENES_PRODUCTO,Producto.class);
		query.setParameter("id", id);

		List<Producto> productos = query.getResultList();

		return productos;
	}


	/**
	 * Realiza una busqueda por cedula y clave para ver si coincide con algun
	 * usuario registrado para realizar el login a unimarket
	 * @param cedula
	 * @param clave
	 * @return
	 */
	public boolean aprobarIngresoUser(String cedula, String clave) {
		Usuario user = buscarUsuarioPorCedula(cedula);
		if (user != null && user.getClave().equals(clave)) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que permite listar los productos activos por medio de una palabra o coincidencias en el nombre
	 * @param nombre o palabra a buscar en producto
	 * @return list productos filtrados
	 */
	public List<Producto> listarProductosNombreActivos(String nombreProducto){
		TypedQuery<Producto> query = entityManager.createNamedQuery(Producto.LISTAR_PRODUCTOS_NOMBRE_ACTIVOS, Producto.class);
		query.setParameter("fechaActual",new Date());
		query.setParameter("nombre","%"+nombreProducto+"%");	

		return query.getResultList();
	}

	/**
	 * Metodo que permite listar los productos favoritos de un usuario
	 * @param cedula del usuario
	 * @return list favoritos del usuario
	 */
	public List<Favorito> listarFavoritosUsuario(String cedula){
		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.LISTAR_FAVORITOS_USUARIO_ACTIVOS,Favorito.class);
		query.setParameter("fechaActual", new Date());
		query.setParameter("cc", cedula);
		return query.getResultList();
	}

	/**
	 * Metodo que permite actualizar los productos por parte del usuario creador
	 * @param producto
	 * @return producto
	 */
	public Producto actualizarProducto(Producto producto) {
		if((entityManager.find(Producto.class,producto.getId())!=null)) {
			entityManager.merge(producto);
			return producto;
		}else {
			return null;
		}
	}

	/**
	 * metodoq que se encarga de obtener el producto por el id
	 * @param idProducto
	 * @return Producto
	 */
	public Producto obtenerProductoId(int id) {
		Producto p = entityManager.find(Producto.class, id);
		return p;
	}
	/**
	 * Metodo que permite realizar un comentario a algun producto de unimarket
	 * @param Comentario
	 * @return Comentario
	 */
	public Comentario comentarProducto(Comentario c,Usuario u) {
		c.setUsuario(u);
		entityManager.persist(c);
		return c;
	}

	/**
	 * Metodo que permite determinar cada producto que pertenece a la lista de favorito de un usuario
	 * @param cedula
	 * @param id
	 * @return true si ya es favorito del ususario
	 */
	public Boolean esFavorito(String cedula, int id) {
		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.ES_FAVORITO, Favorito.class);
		query.setParameter("cc", cedula);
		query.setParameter("id", id);

		return query.getResultList().size() > 0;
	}

	/**
	 * metodo que permite registrar un produccto en la lista de favoritos del usuario
	 * @param favorito
	 * @return favorito
	 */
	public Favorito registrarFavorito(Favorito f,Usuario u) {
		f.setUsuario(u);
		entityManager.persist(f);
		return f;

	}

	/**
	 * Metodo que permite eliminar un producto de la lista de favoritos de un usuario
	 * @param cedula
	 * @param id
	 * @return true
	 */
	public Boolean eliminarFavorito(String cedula,int id) {
		TypedQuery<Favorito> query = entityManager.createNamedQuery(Favorito.ES_FAVORITO, Favorito.class);
		query.setParameter("cc", cedula);
		query.setParameter("id", id);
		Favorito f = query.getSingleResult();
		entityManager.remove(f);
		return true;
	}

	/**
	 * Metodo que permite obtener la lista de los 3 mas vendidos de unimarket
	 * @return list mas vendidos
	 */
	public List<Object[]> listaTopVendidos(){

		TypedQuery<Object[]> query = entityManager.createNamedQuery(DetalleCompra.LISTAR_5PRODUCTOS_MAS_VENDIDOS, Object[].class);
		query.setMaxResults(3);
		List<Object[]> top = query.getResultList();

		return top;
	}

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	/**
	 * Metodo que permite enviar un correo electronico
	 * @param Persona  al cual se desea enviar el email
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public Boolean envioEmail(Persona p,String mensaje) throws AddressException, MessagingException {
		try {
			System.out.println("\n 1st ===> configurando propiedades de mailServer..");
			mailServerProperties = System.getProperties();
			mailServerProperties.put("mail.smtp.port", "587");
			mailServerProperties.put("mail.smtp.auth", "true");
			mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			System.out.println("Propiedades de mailServer configuradas..");

			System.out.println("\n\n 2nd ===> enviando email Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(p.getEmail()));
			generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(p.getEmail()));
			generateMailMessage.setSubject("Ingreso UniMarket..");
			String emailBody = mensaje;
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session creado satisfactoriamente..");

			System.out.println("\n\n 3rd ===> Obteniendo session y enviando email");
			Transport transport = getMailSession.getTransport("smtp");

			transport.connect("smtp.gmail.com", "unimarkethela@gmail.com", "41925469");
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
			return true;
		}catch(Exception e) {
			return false;
		}
	}


	public Boolean registrarProducto(Producto producto){

		try {
			entityManager.persist(producto);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean registrarDetalleCompra(DetalleCompra dc)
	{
		try {
			entityManager.persist(dc);
			Producto p = entityManager.find(Producto.class,dc.getProducto().getId());
			int cantidadActualizada = p.getDisponibilidad() - dc.getCantidad();
			p.setDisponibilidad(cantidadActualizada);
			entityManager.merge(p);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public Boolean registrarCompra(Compra c)
	{
		try {
			entityManager.persist(c);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public List<Compra> listarComprasUsuario(Usuario u) {
		TypedQuery<Compra> query = entityManager.createNamedQuery(Compra.LISTAR_COMPRAS_USUARIO, Compra.class);
		query.setParameter("cc",u.getCedula());
		return query.getResultList();
	}


	public Calificacion obtenerCalificacionProductoUsuario(Producto p,Usuario u)
	{
		try {
			Query query = entityManager.createNamedQuery(Calificacion.CALIFICACION_PRODUCTO_USUARIO);
			query.setParameter("cc",u.getCedula());
			query.setParameter("id",p.getId());
			Object resultado = query.getSingleResult();
			Calificacion c =(Calificacion)resultado;
			System.out.println(c);
			return c;

		} catch (NoResultException e) {
			return null;
		}
	}

	public void registrarCalificacion(Calificacion c)
	{
		try {
			entityManager.persist(c);
		} catch (Exception e) {			
		}
	}

	public Boolean actualizarCalificacion(Calificacion calificacion){
		if ((entityManager.find(Calificacion.class, calificacion.getId())!=null)) {
			entityManager.merge(calificacion);
			return true;
		}else {
			return false;
		}
	}

	public List<Producto> top3MasVendidos()
	{
		try {
			TypedQuery<Object[]> query = entityManager.createNamedQuery(DetalleCompra.LISTAR_5PRODUCTOS_MAS_VENDIDOS, Object[].class);
			query.setMaxResults(3);
			List<Producto> productos = new ArrayList<Producto>();
			Producto p;
			for (int i = 0; i < query.getResultList().size(); i++) {
				p = entityManager.find(Producto.class,query.getResultList().get(i)[0]);
				productos.add(p);
			}
			return productos;
		}catch (Exception e) {
			return null;
		}

	}

}