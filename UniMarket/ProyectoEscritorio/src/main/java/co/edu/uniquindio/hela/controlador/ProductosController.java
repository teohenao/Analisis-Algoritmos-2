package co.edu.uniquindio.hela.controlador;

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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private ComboBox <Categoria> comboCategoria;

	@FXML
	private ComboBox<Producto> comboEstado;

	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	private AdministradorDelegado delegado = manejador.getDelegado();
	private Producto ProductoSeleccionado;
	private ObservableList<Producto> listaProductos;
	

	@FXML
	void regresar(ActionEvent event) {
		manejador.cargarEscenarioMenuAdmin(btnRegresar);
	}
	@FXML
	void detalles(ActionEvent event) {
	}

	@FXML
	void buscarProducto(ActionEvent event) {
	}

	@FXML
	void buscarProductoUsuario(ActionEvent event) {
	}
	
	@FXML
	void comboCategoriaProducto(ActionEvent event) {
	}
	
	@FXML
	void comboEstadoProducto(ActionEvent event) {
	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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

		final ObservableList<Producto> tablaProductoSeleccionado = tablaProductos.getSelectionModel().getSelectedItems();
		tablaProductoSeleccionado.addListener(seleccionadoTabla);
	}
	public void inicializarListaProductos() {
		listaProductos = FXCollections.observableArrayList(obtenerLista());
		llenarTablaProductos(listaProductos);
	}
	public List<Producto> obtenerLista(){
		return delegado.listarProductos();
	}
	public void llenarTablaProductos(ObservableList<Producto> listaProductos) {
		tablaProductos.setItems(listaProductos);
	}
	private final ListChangeListener<Producto> seleccionadoTabla = new ListChangeListener<Producto>() {

		@Override
		public void onChanged(ListChangeListener.Change<? extends Producto> p) {
			//mostrarUsuarioSeleccionado();
		}
	};

}
