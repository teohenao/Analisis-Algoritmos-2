package co.edu.uniquindio.hela.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.entidades.Usuario;
import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import co.edu.uniquindio.hela.utilidades.Utilidades;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductosController implements Initializable{

	@FXML
	private TableView<Producto> tablaProductos;

	@FXML
	private TableColumn<Producto, Integer> idProducto;

	@FXML
	private TableColumn<Producto, String> nombreProducto;

	@FXML
	private TableColumn<Producto, String> usuarioProducto;


	@FXML
	private TextField txtBuscarProducto;

	@FXML
	private TextField txtBuscarUsuario;

	@FXML
	private Button btnRegresar;

	@FXML
	private Button btnDetalles;

	@FXML
	private Button btnBuscarProducto;

	@FXML
	private Button btnBuscarProductoUsuario;

	@FXML
	private ComboBox <String> comboCategoria;

	@FXML
	private ComboBox<String> comboEstado;

	@FXML
	void regresar(ActionEvent event) {
		manejador.cargarEscenarioMenuAdmin(btnRegresar);
	}

	@FXML
	void buscarProducto(ActionEvent event) {
		if(txtBuscarProducto.getText().trim().isEmpty()) {
			Utilidades.mostrarMensaje(":(","Debe ingresar el producto a buscar");
		}else {
			listaProductos = FXCollections.observableArrayList(obtenerListaProductosNombre(txtBuscarProducto.getText()));
			llenarTablaProductos(listaProductos);
		}	
	}

	@FXML
	void buscarProductoUsuario(ActionEvent event) {
		if(txtBuscarUsuario.getText().isEmpty()) {
			Utilidades.mostrarMensaje("error", "por favor ingrese una cedula para buscar");
		}else if(delegado.buscarUsuarioPorCedula(txtBuscarUsuario.getText()) == null) {
			Utilidades.mostrarMensaje("error", "no se encontro el usuario registrado");
		}else {
			listaProductos = FXCollections.observableArrayList(obtenerListaProductosUsuario(txtBuscarUsuario.getText()));
			llenarTablaProductos(listaProductos);
		}
	}
	//instanciamos el controlador para poder pasar parametros al detalle producto
	ProductosController productosController;
	public int idSeleccionado;

	



	private void llenarComboCategoria() {
		comboCategoria.getItems().addAll(
				"todos",
				Categoria.deporte.toString(),
				Categoria.joyas.toString(),
				Categoria.libros.toString(),
				Categoria.moda.toString(),
				Categoria.tecnologia.toString()
				);
	}

	private void llenarComboEstado() {
		comboEstado.getItems().addAll(
				"todos",
				"activos",
				"inactivos"
				);
	}

	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	private AdministradorDelegado delegado = manejador.getDelegado();
	private Producto ProductoSeleccionado;
	private ObservableList<Producto> listaProductos;





	private String categoriaSeleccionada = "";
	private String estadoSeleccionado = "";



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//inicializamos, capturar controler
		productosController=this;

		idProducto.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("id"));		
		nombreProducto.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));		
		//usuarioProducto.setCellValueFactory(new PropertyValueFactory<Producto,String>("USUARIO_cedula"));	
		usuarioProducto.setCellValueFactory(new Callback<CellDataFeatures<Producto,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Producto, String> param) {
				return new SimpleStringProperty(param.getValue().getUsuario().getCedula());
			}
		} );

		inicializarListaProductos();
		llenarComboCategoria();
		llenarComboEstado();

		final ObservableList<Producto> tablaProductoSeleccionado = tablaProductos.getSelectionModel().getSelectedItems();
		tablaProductoSeleccionado.addListener(seleccionadoTabla);




		comboCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				String seleccionCategoria = comboCategoria.getSelectionModel().getSelectedItem();
				categoriaSeleccionada = seleccionCategoria;
				listaProductos = FXCollections.observableArrayList(obtenerListaProductosFiltrada(estadoSeleccionado,seleccionCategoria));
				llenarTablaProductos(listaProductos);

			}

		});

		comboEstado.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				String seleccionEstado = comboEstado.getSelectionModel().getSelectedItem();
				estadoSeleccionado = seleccionEstado;
				listaProductos = FXCollections.observableArrayList(obtenerListaProductosFiltrada(seleccionEstado,categoriaSeleccionada));
				llenarTablaProductos(listaProductos);

			}	
		});




	}
	public void inicializarListaProductos() {
		listaProductos = FXCollections.observableArrayList(obtenerLista());
		llenarTablaProductos(listaProductos);
	}
	public List<Producto> obtenerLista(){
		return delegado.listarProductos();
	}
	public List<Producto> obtenerListaProductosFiltrada(String estado, String categoria){

		if((estado == "" | estado == "todos")&&(categoria == "" | categoria == "todos" ) ) {
			return delegado.listarProductos();
		}else if ((estado == "activos")&&(categoria == "" | categoria =="todos")) {
			return delegado.listarProductosActivos();
		}else if ((estado == "inactivos")&&(categoria == "" | categoria =="todos")){
			return delegado.listarProductosVencidos();
		}else if((estado == "activos")&&(categoria != "" | categoria != "todos")) {
			return delegado.listarProductosActivosCategoria(categoria);
		}else if((estado == "inactivos")&&(categoria != "" | categoria != "todos")) {
			return delegado.listarProductosVencidosCategoria(categoria);
		}else if((estado == "" | estado == "todos")&&(categoria != "" | categoria != "todos")){
			return delegado.listarProductosCategoria(categoria);			
		}else {
			return delegado.listarProductos();
		}

	}
	public List<Producto> obtenerListaProductosNombre(String nombreProducto){
		return delegado.listarProductosNombre(nombreProducto);
	}

	public List<Producto> obtenerListaProductosUsuario(String cedulaUsuario){
		return delegado.listarProductosUsuario(cedulaUsuario);
	}
	public void llenarTablaProductos(ObservableList<Producto> listaProductos) {
		tablaProductos.setItems(listaProductos);
	}

	private final ListChangeListener<Producto> seleccionadoTabla = new ListChangeListener<Producto>() {

		@Override
		public void onChanged(ListChangeListener.Change<? extends Producto> p) {
			capturarProductoSeleccionado();
		}
	};


	public void capturarProductoSeleccionado() {

		ProductoSeleccionado = obtenerFilaSeleccionada();

		if(ProductoSeleccionado!=null) {
			idSeleccionado = ProductoSeleccionado.getId();
		}
	}

	public Producto obtenerFilaSeleccionada() {
		if(tablaProductos!=null) {
			Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
			return producto;
		}
		return null;
	}
	
	@FXML
	private void detalles(ActionEvent event){
		try {
		Stage detalleProducto = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/DetalleProducto.fxml"));	
		Parent root = (Parent) loader.load();
		
		//root.getStylesheets().add(getClass().getResource("../utilidades/styles.css").toString());
		DetalleProductoController detalleProductoController = loader.getController();
		detalleProductoController.detallesProducto(idSeleccionado);
		Scene scene = new Scene(root);
		detalleProducto.setScene(scene);
		detalleProducto.alwaysOnTopProperty();
		detalleProducto.initModality(Modality.APPLICATION_MODAL);
		detalleProducto.show();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}


	
}
