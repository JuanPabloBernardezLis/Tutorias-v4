package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Sesiones;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirSesion implements Initializable {

	private LocalDate fecha;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int minutosDuracion;
	public final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

	
	private ObservableList<Integer> minutos = FXCollections.observableArrayList();
	private ObservableList<Integer> horas= FXCollections.observableArrayList();
	
	private Tutoria tutoria;

	private IControlador controladorMVC;
	private ObservableList<Sesion> sesiones;

	@FXML
	private DatePicker dPFecha;
    @FXML
    private ComboBox<Integer> cbHoraInicio;
    @FXML
    private ComboBox<Integer> cbMinInicio;
    @FXML
    private ComboBox<Integer> cbHoraFin;
    @FXML
    private ComboBox<Integer> cbMinFin;
	@FXML
	private ComboBox<Integer> cbMinDuracion;
	@FXML
	private Button btAceptar;
	@FXML
	private Button btCancelar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		setHoras();
		setMinutos();
		reset();
		
		
		//lvHoraInicio.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> setHorasFin(nv));
		/*cbHoraInicio.setItems(horasInicioSesion);
		cbMinInicio.setItems(minutosHorario);
		cbHoraFin.setItems(horasInicioSesion);
		cbMinFin.setItems(minutosHorario);
		cbMinDuracion.setItems(minutosHorario);*/
		
		
	}
	
	private void reset() {
		cbHoraInicio.getSelectionModel().clearSelection();
		cbHoraInicio.setValue(0);
				
		cbMinInicio.getSelectionModel().clearSelection();
		cbMinInicio.setValue(0);
		cbMinInicio.setItems(minutos);
		
		cbHoraFin.getSelectionModel().clearSelection();
		cbHoraFin.setValue(0);
		
		cbMinFin.getSelectionModel().clearSelection();
		cbMinFin.setValue(0);
		cbMinFin.setItems(minutos);
				
		cbMinDuracion.getSelectionModel().clearSelection();
		cbMinDuracion.setValue(0);
		
		cbMinDuracion.setItems(minutos);
		dPFecha.setValue(null);
		
	}

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}


	@FXML
	void anadirSesion(ActionEvent event) {
		Sesion sesion = null;
		try {		
			sesion = getSesion();
			controladorMVC.insertar(sesion);
			sesiones.setAll(controladorMVC.getSesiones(tutoria));
			
			System.out.println(sesion);
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Sesión", "Sesión añadida satisfactoriamente", propietario);
			reset();
			
		} catch (Exception e) {			
			Dialogos.mostrarDialogoError("Añadir Sesión", e.getMessage());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
	
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	public void inicializa(Tutoria tutoria,ObservableList<Sesion> sesiones) {
		this.tutoria=tutoria;
		this.sesiones=sesiones;
	}


	public void setHoras() {
		List<Integer> horasAL = new ArrayList<>();
		int hora=0; //Intentar poner aquí el atributo HORA_COMIENZO_CLASES
		do {
			horasAL.add(hora);
			hora++;
			
		} while (hora <= 23);//Intentar poner aquí el atributo HORA_FIN_CLASES
		
		horas.setAll(FXCollections.observableArrayList(horasAL));
		cbHoraInicio.setItems(horas);
		cbHoraFin.setItems(horas);

	}


	public void setMinutos() {
		
		List<Integer> minutosAL = new ArrayList<>();
				
		int minuto=0;
		do {
			minutosAL.add(minuto);
			minuto=minuto+5;
			
		} while (minuto <= 59);
	
		minutos.setAll(FXCollections.observableArrayList(minutosAL));

	}


	private Sesion getSesion() {
		LocalDate fecha = dPFecha.getValue();
		horaInicio = LocalTime.of(cbHoraInicio.getValue(), cbMinInicio.getValue());
		horaFin = LocalTime.of(cbHoraFin.getValue(), cbMinFin.getValue());
		minutosDuracion = cbMinDuracion.getValue();
		
		return new Sesion(tutoria, fecha, horaInicio, horaFin, minutosDuracion);
	}

}
