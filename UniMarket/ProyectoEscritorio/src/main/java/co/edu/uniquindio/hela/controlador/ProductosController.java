package co.edu.uniquindio.hela.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.hela.entidades.Categoria;
import co.edu.uniquindio.hela.entidades.Producto;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Controlador de la vista de productos
 * @author mateo,AnaMaria
 * @version 1.0
 */
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
	private Label labelNombreSeleccionado;

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
	
	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	private AdministradorDelegado delegado = manejador.getDelegado();
	private Producto ProductoSeleccionado;
	private ObservableList<Producto> listaProductos;
	private String categoriaSeleccionada = "";
	private String estadoSeleccionado = "";
	ProductosController productosController;
	public Producto idSeleccionado;

	/**
	 * Evento del boton regresar que carga el escenario principal
	 * @param event
	 */
	@FXML
	void regresar(ActionEvent event) {
		manejador.cargarEscenarioMenuAdmin(btnRegresar);
	}

	/**
	 * Event que permite buscar un producto por nombre
	 * @param event
	 */
	@FXML
	void buscarProducto(ActionEvent event) {
		if(txtBuscarProducto.getText().trim().isEmpty()) {
			Utilidades.mostrarMensaje(":(","Debe ingresar el producto a buscar");
		}else {
			listaProductos = FXCollections.observableArrayList(obtenerListaProductosNombre(txtBuscarProducto.getText()));
			llenarTablaProductos(listaProductos);
		}	
	}

	/**
	 * Event que permite buscar los productos de un usuario
	 * @param event
	 */
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
	

	/**
	 * Metodo que llena el combo de categorias
	 */
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

	/**
	 * Metodo que llena el combo de estados
	 */
	private void llenarComboEstado() {
		comboEstado.getItems().addAll(
				"todos",
				"activos",
				"inactivos"
				);
	}



	/**
	 * Metodo que se inicializa al cargar el escenario de productos
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		idProducto.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("id"));		
		nombreProducto.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));		
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
	/**
	 * Inicializar vista de productos registrados
	 */
	public void inicializarListaProductos() {
		listaProductos = FXCollections.observableArrayList(obtenerLista());
		llenarTablaProductos(listaProductos);
	}
	/**
	 * obtener la lista de productos de unimarket
	 * @return List productos
	 */
	public List<Producto> obtenerLista(){
		return delegado.listarProductos();
	}
	/**
	 * Metodo que funciona como filtro para todos los posibles estados de los combox
	 * @param estado
	 * @param categoria
	 * @return ListFiltrada
	 */
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
	/**
	 * Metodo que permite obtener la lista filtrada por nombre
	 * @param nombreProducto
	 * @return ListProductosFiltrados
	 */
	public List<Producto> obtenerListaProductosNombre(String nombreProducto){
		return delegado.listarProductosNombre(nombreProducto);
	}

	/**
	 * Metodo que permite obtener la lista filtrada por productos de usuario
	 * @param cedulaUsuario
	 * @return ListProductosUsuario
	 */
	public List<Producto> obtenerListaProductosUsuario(String cedulaUsuario){
		return delegado.listarProductosUsuario(cedulaUsuario);
	}
	/**
	 * Metodo que llena la tabla con los productos
	 * @param listaProductos
	 */
	public void llenarTablaProductos(ObservableList<Producto> listaProductos) {
		tablaProductos.setItems(listaProductos);
	}


	private final ListChangeListener<Producto> seleccionadoTabla = new ListChangeListener<Producto>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Producto> p) {
			capturarProductoSeleccionado();
		}
	};

	/**
	 * Metodo  que se ejecuta al seleccionar un producto de la tabla
	 */
	public void capturarProductoSeleccionado() {

		ProductoSeleccionado = obtenerFilaSeleccionada();

		if(ProductoSeleccionado!=null) {
			labelNombreSeleccionado.setText(ProductoSeleccionado.getNombre());
			idSeleccionado = ProductoSeleccionado;
		}
	}

	/**
	 * Metodo que se encarga de obtener el producto seleccionado
	 * @return
	 */
	public Producto obtenerFilaSeleccionada() {
		if(tablaProductos!=null) {
			Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
			return producto;
		}
		return null;
	}

	/**
	 * Vista de detalle productos "MODAL"
	 * @param event
	 */
	@FXML
	private void detalles(ActionEvent event){
		if(idSeleccionado != null) {
			try {
				Stage detalleProducto = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../vista/DetalleProducto.fxml"));	
				Parent root = (Parent) loader.load();
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
		}else {
			Utilidades.mostrarMensaje("ERROR", "por favor seleccione un producto para mostrar los detalles");
		}

	}



}
