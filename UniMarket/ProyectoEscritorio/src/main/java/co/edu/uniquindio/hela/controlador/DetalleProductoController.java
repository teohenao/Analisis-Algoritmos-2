package co.edu.uniquindio.hela.controlador;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.hela.entidades.Comentario;
import co.edu.uniquindio.hela.entidades.Producto;
import co.edu.uniquindio.hela.main.ManejadorEscenarios;
import co.edu.uniquindio.hela.modelo.AdministradorDelegado;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class DetalleProductoController implements Initializable{
	
	private ManejadorEscenarios manejador = new ManejadorEscenarios();
	public int idProductoMostrar;
	private AdministradorDelegado delegado = manejador.getDelegado();
	private ObservableList<Comentario> listaComentarios;
	
	@FXML
	private TableView<Comentario> tablaComentarios;

	@FXML
	private TableColumn<Comentario, String> comentariosProducto;

	@FXML
	private TableColumn<Comentario, String> comentariosProductosUsuario;


	@FXML
	private Label labelNombre;
	
	@FXML
	private Label labelId;

	@FXML
	private Label labelDisponibles;

	@FXML
	private Label labelPrecio;
	
	@FXML
	private Label labelFecha;
	
	@FXML
	private Label labelCategoria;
	
	@FXML
	private Label labelUsuario;
	
	@FXML
	private Label labelUsuarioNombre;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	public void detallesProducto(Producto p) {
		
		idProductoMostrar = p.getId();
		
		labelNombre.setText(p.getNombre());
		labelId.setText(Integer.toString(p.getId()));
		labelDisponibles.setText(Integer.toString(p.getDisponibilidad()));
		labelPrecio.setText("$ "+Double.toString(p.getPrecio()));
		labelCategoria.setText(p.getCategoria().toString());
		labelUsuario.setText(p.getUsuario().getCedula());
		labelUsuarioNombre.setText(p.getUsuario().getNombreCompleto());
		String d = new SimpleDateFormat("dd-MM-yyyy").format(p.getFechaLimite());
		labelFecha.setText(d);
		
		comentariosProducto.setCellValueFactory(new PropertyValueFactory<Comentario,String>("comentario"));		
		comentariosProductosUsuario.setCellValueFactory(new Callback<CellDataFeatures<Comentario,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Comentario, String> param) {
				return new SimpleStringProperty(param.getValue().getUsuario().getNombreCompleto());
			}
		} );
		inicializarListaComentarios();
		verificarEstado(p.getFechaLimite());
		
		
	}

	
	public void inicializarListaComentarios() {
		listaComentarios = FXCollections.observableArrayList(obtenerLista());
		llenarTablaComentarios(listaComentarios);
	}
	public List<Comentario> obtenerLista(){
		return delegado.listarComentariosProducto(idProductoMostrar);
	}
	public void llenarTablaComentarios(ObservableList<Comentario> listaComeentarios) {
		tablaComentarios.setItems(listaComentarios);
	}
	
	public void verificarEstado(Date fecha){
		Date fechaActual = new Date();
		//Date tiene un Compareto que compara fechas, devuelve 1 si esta activo o -1 si esta inactivo
		int res = fecha.compareTo(fechaActual);
		if(res <= 0) {
			labelNombre.setTextFill(Color.web("#FF0040"));
		}else {
			labelNombre.setTextFill(Color.web("#00FF40"));
		}
		}
		
	

	
	
	
}
