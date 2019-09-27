package co.edu.uniquindio.hela.controlador;

import java.util.List;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.excepciones.InformacionRepetidaExcepcion;
import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import co.edu.uniquindio.hela.utilidades.Utilidades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;

public class UsuariosController implements Initializable{

	@FXML
	private TableView<Usuario> tablaUsuarios;

	@FXML
	private TableColumn<Usuario, String> cedulaUsuario;

	@FXML
	private TableColumn<Usuario, String> nombreUsuario;

	@FXML
	private TableColumn<Usuario, String> emailUsuario;

	@FXML
	private TableColumn<Usuario, String> telefonoUsuario;

	@FXML
	private TableColumn<Usuario, String> direccionUsuario;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtCedula;

	@FXML
	private TextField txtDireccion;

	@FXML
	private TextField txtTelefono;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtClave;

	@FXML
	private TextField txtBuscar;

	@FXML
	private Button btnRegresar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnActualizar;


	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	private AdministradorDelegado delegado = manejador.getDelegado();
	private Usuario UsuarioSeleccionado;
	private ObservableList<Usuario> listaUsuarios;

	@FXML
	void buscarUsuario(ActionEvent event) {

		if(txtBuscar.getText().trim().isEmpty()) {
			Utilidades.mostrarMensaje(":(","Debe ingresar una cedula para buscar thanks :/" );
		} else if (delegado.buscarUsuarioPorCedula(txtBuscar.getText()) == null) {
			Utilidades.mostrarMensaje(":(","No se encontro esta cedula en la base de datos srrrry :/" );
		} else {
			Usuario user = delegado.buscarUsuarioPorCedula(txtBuscar.getText());
			txtCedula.setText(user.getCedula());
			txtClave.setText(user.getClave());
			txtDireccion.setText(user.getDireccion());
			txtEmail.setText(user.getEmail());
			txtNombre.setText(user.getNombreCompleto());
			txtTelefono.setText(user.getNumeroTelefonico());
		}

	}

	@FXML
	void actualizarUsuario(ActionEvent event) {

		String nombreUsuario = txtNombre.getText();
		String direccionUsuario = txtDireccion.getText();
		String telefonoUsuario = txtTelefono.getText();
		String claveUsuario = txtClave.getText();
		String emailUsuario = txtEmail.getText();

		if(txtClave.getText().trim().isEmpty() | txtDireccion.getText().trim().isEmpty()
				| txtEmail.getText().trim().isEmpty() | txtNombre.getText().trim().isEmpty() | txtTelefono.getText().trim().isEmpty()) {
			Utilidades.mostrarMensaje("INTENTELO DE NUEVO", "Debe llenar todos los campos :D");
		}else{
			if(UsuarioSeleccionado != null) {
				UsuarioSeleccionado.setNombreCompleto(nombreUsuario);
				UsuarioSeleccionado.setDireccion(direccionUsuario);
				UsuarioSeleccionado.setNumeroTelefonico(telefonoUsuario);
				UsuarioSeleccionado.setClave(claveUsuario);
				UsuarioSeleccionado.setEmail(emailUsuario);

				if(delegado.actualizarUsuario(UsuarioSeleccionado)!=null) {
					Utilidades.mostrarMensaje("ACTUALIZACION EXITOSA", "Los datos del usuario han sido actualiados satisfactoriamente");
					manejador.cargarEscenarioUsuarios(btnActualizar);

				}else {
					JOptionPane.showMessageDialog(null, "El email ya esta en uso");	
				}

			}else if(delegado.buscarUsuarioPorCedula(txtCedula.getText())!=null){

				Usuario user = delegado.buscarUsuarioPorCedula(txtCedula.getText());
				user.setNombreCompleto(nombreUsuario);
				user.setDireccion(direccionUsuario);
				user.setNumeroTelefonico(telefonoUsuario);
				user.setClave(claveUsuario);
				user.setEmail(emailUsuario);

				if (delegado.actualizarUsuario(user)!=null) {
					Utilidades.mostrarMensaje("ACTUALIZACION EXITOSA", "Los datos del usuario han sido actualiados satisfactoriamente");
					manejador.cargarEscenarioUsuarios(btnActualizar);

				} else {
					JOptionPane.showMessageDialog(null, "El email ya esta en uso");	
				}

			}
		}
	}

	@FXML
	void eliminarUsuario(ActionEvent event) {

		if(txtCedula.getText().trim().isEmpty()){
			Utilidades.mostrarMensaje("ADVERTENCIA", "POR FAVOR INSERTE UNA CEDULA");
		}else if(delegado.buscarUsuarioPorCedula(txtCedula.getText()) == null ) {
			Utilidades.mostrarMensaje(":(","No se encontro esta cedula en la base de datos srrrry :/" );
		}
		else if (delegado.eliminarUsuario(txtCedula.getText())) {
			Utilidades.mostrarMensaje("USUARIO ELIMINADO", "El usuario identificado con C.C: "+txtCedula.getText()+"\nA sido eliminado satisfactoriamente");
			reiniciarCampos();
			listaUsuarios = FXCollections.observableArrayList(obtenerLista());
			llenarTablaUsuarios(listaUsuarios);

		}else {
			Utilidades.mostrarMensaje(":(","No se encontro esta cedula en la base de datos sorry :/" );

		}

	}

	@FXML
	void registrarUsuario(ActionEvent event) {
		if (txtCedula.getText().trim().isEmpty() | txtClave.getText().trim().isEmpty() | txtDireccion.getText().trim().isEmpty()
				| txtEmail.getText().trim().isEmpty() | txtNombre.getText().trim().isEmpty() | txtTelefono.getText().trim().isEmpty()) {
			Utilidades.mostrarMensaje("INTENTELO DE NUEVO", "Debe llenar todos los campos :D");
		} else {
			try {

				Usuario usuario = new Usuario();
				usuario.setCedula(txtCedula.getText());
				usuario.setClave(txtClave.getText());
				usuario.setDireccion(txtDireccion.getText());
				usuario.setEmail(txtEmail.getText());
				usuario.setNombreCompleto(txtNombre.getText());
				usuario.setNumeroTelefonico(txtTelefono.getText());

				if(delegado.registrarUsuario(usuario)!=null) {
					Utilidades.mostrarMensaje("EXITO", "El usuario se registro satisfactoriamente");
					reiniciarCampos();
					inicializarListaUsuarios();
				}

			} catch (InformacionRepetidaExcepcion e) {
				Utilidades.mostrarMensaje("ERROR", "a ocurrido un error al registrar el usuario"+"\n"+e.getMessage());
			}

		}


	}

	public void reiniciarCampos() {
		txtNombre.setText("");
		txtCedula.setText("");
		txtClave.setText("");
		txtDireccion.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
	}

	@FXML
	void regresar(ActionEvent event) {
		manejador.cargarEscenarioMenuAdmin(btnRegresar);
	}

	public void inicializarListaUsuarios() {
		listaUsuarios = FXCollections.observableArrayList(obtenerLista());
		llenarTablaUsuarios(listaUsuarios);
	}
	
	public List<Usuario> obtenerLista(){
		return delegado.listarUsuarios();
	}

	public void llenarTablaUsuarios(ObservableList<Usuario> listaUsuarios) {
		tablaUsuarios.setItems(listaUsuarios);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		nombreUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("nombreCompleto"));		
		cedulaUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("cedula"));		
		emailUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("email"));		
		direccionUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("direccion"));		
		telefonoUsuario.setCellValueFactory(new PropertyValueFactory<Usuario,String>("numeroTelefonico"));		

		inicializarListaUsuarios();

		final ObservableList<Usuario> tablaUsuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItems();
		tablaUsuarioSeleccionado.addListener(seleccionadoTabla);

	}    

	private final ListChangeListener<Usuario> seleccionadoTabla = new ListChangeListener<Usuario>() {

		@Override
		public void onChanged(ListChangeListener.Change<? extends Usuario> c) {
			mostrarUsuarioSeleccionado();
		}
	};


	public void mostrarUsuarioSeleccionado() {

		UsuarioSeleccionado = obtenerFilaSeleccionada();

		if(UsuarioSeleccionado!=null) {

			reiniciarCampos();
			txtNombre.setText(UsuarioSeleccionado.getNombreCompleto());
			txtCedula.setText(UsuarioSeleccionado.getCedula());
			txtDireccion.setText(UsuarioSeleccionado.getDireccion());
			txtTelefono.setText(UsuarioSeleccionado.getNumeroTelefonico());
			txtEmail.setText(UsuarioSeleccionado.getEmail());
			txtClave.setText(UsuarioSeleccionado.getClave());			
		}
	}

	public Usuario obtenerFilaSeleccionada() {
		if(tablaUsuarios!=null) {
			Usuario user = tablaUsuarios.getSelectionModel().getSelectedItem();
			return user;
		}

		return null;
	}

}
