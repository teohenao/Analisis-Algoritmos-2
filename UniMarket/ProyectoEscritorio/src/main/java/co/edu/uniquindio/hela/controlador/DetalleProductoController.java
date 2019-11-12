package co.edu.uniquindio.hela.controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

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
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * Controlador del escenario detalleProducto
 * @author mateo,AnaMaria
 * @version 1.0
 */
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
	private ListView<ImageView> listImagenes;

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

	@FXML
	private Label labelCalificacion;

	@FXML
	private Rating estrellas;
	
	@FXML 
	private Text textDescripcion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	private ObservableList<ImageView> listaImagenes;

	ObservableList<Path> imageFiles;

	/**
	 * Metodo que se ejecuta desde la vista de productos, efectuando la carga de los detalles del producto seleccionado
	 * @param p
	 * @throws FileNotFoundException
	 */
	public void detallesProducto(Producto p) throws FileNotFoundException {

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
		textDescripcion.setText(p.getDescripcion().toString());

		comentariosProducto.setCellValueFactory(new PropertyValueFactory<Comentario,String>("comentario"));		
		comentariosProductosUsuario.setCellValueFactory(new Callback<CellDataFeatures<Comentario,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Comentario, String> param) {
				return new SimpleStringProperty(param.getValue().getUsuario().getNombreCompleto());
			}
		} );
		inicializarListaComentarios();
		verificarEstado(p.getFechaLimite());
		calificacionProducto();
		imageFiles = FXCollections.observableArrayList();
		listaImagenes = FXCollections.observableArrayList();
		listarImagenes();
		listImagenes.setItems(listaImagenes);
		listImagenes.setOrientation(Orientation.HORIZONTAL);


	}

	/**
	 * Inicializar lista de los comentarios de un producto
	 */
	public void inicializarListaComentarios() {
		listaComentarios = FXCollections.observableArrayList(obtenerLista());
		llenarTablaComentarios(listaComentarios);
	}
	/**
	 * Metodo que obtiene la lista de comentarios de un producto
	 * @return ListComentariosProducto
	 */
	public List<Comentario> obtenerLista(){
		return delegado.listarComentariosProducto(idProductoMostrar);
	}
	/**
	 * Metodo que se encarga de llenar la tabla de comentarios de un producto
	 * @param listaComeentarios
	 */
	public void llenarTablaComentarios(ObservableList<Comentario> listaComeentarios) {
		tablaComentarios.setItems(listaComentarios);
	}

	/**
	 * Metodo para verificar el estado de un producto y darle un color que lo diferencie
	 * @param fecha
	 */
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

	/**
	 * Metodo que se encarga de obtener la calificacion final de un producto y mostrarla
	 */
	public void calificacionProducto() {

		double resultado = delegado.calificacionFinalProducto(idProductoMostrar);
		resultado = Math.round(resultado * 10) / 10d;

		estrellas.setPartialRating(true);
		estrellas.setRating(resultado);
		estrellas.setDisable(false);	

		labelCalificacion.setText(Double.toString(resultado));

		if(resultado <= 2) {
			labelCalificacion.setTextFill(Color.web("#FA2525"));
		}else if(resultado > 2 && resultado <= 4) {
			labelCalificacion.setTextFill(Color.web("#F0B30B"));
		}else if(resultado > 4) {
			labelCalificacion.setTextFill(Color.web("#08ED82"));
		}

	}

	/**
	 * Metodo que se encarga de obtener las imagenes de un producto y mostrarla obtienendo su ubicacion
	 * @throws FileNotFoundException
	 */
	public void listarImagenes() throws FileNotFoundException {

		List<Producto> imagenes = delegado.listarImageneProducto(idProductoMostrar);
		for (int i = 0; i < imagenes.size(); i++) {
			if(imagenes.get(0)==null) {
				FileInputStream imageStream = new FileInputStream("/home/mateo/Documentos/Analisis-Algoritmos-2/UniMarket/ProyectoEscritorio/src/main/java/co/edu/uniquindio/hela/utilidades/hela.jpg");
				Image image = new Image (imageStream );
				ImageView img = new ImageView(image);
				img.setFitWidth(150);
				img.setFitHeight(150);
				listaImagenes.add(img);
			}else {
				String rutaImg = ""+imagenes.get(i)+"";
				FileInputStream imageStream = new FileInputStream(rutaImg);
				Image image = new Image (imageStream );
				ImageView img = new ImageView(image);
				img.setFitWidth(176);
				img.setFitHeight(176);
				listaImagenes.add(img);
			}
			
			
		}

	}







}
