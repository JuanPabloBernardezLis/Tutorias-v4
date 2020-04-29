package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvista;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirSesion implements Initializable {

	private LocalDate fecha;
	private LocalTime horaInicio;
	private LocalTime horaFin;
	private int minutosDuracion;
	public final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");

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
		cbHoraInicio.setItems(getHorasInicio());
		cbMinInicio.setItems(getMinutosHorario());
		cbHoraFin.setItems(getHorasFin());
		cbMinFin.setItems(getMinutosHorario());
		cbMinDuracion.setItems(getMinutosHorario());

	}

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setSesiones(ObservableList<Sesion> sesiones) {
		this.sesiones = sesiones;
	}

	@FXML
	void anadirSesion(ActionEvent event) {
		Sesion sesion = null;
		try {
			sesion = getSesion();
			controladorMVC.insertar(sesion);
			sesiones.setAll(controladorMVC.getSesiones());
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Sesión", "Sesión añadida satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Sesión", e.getMessage());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	public void inicializa() {
		cbHoraInicio.setItems(getHorasInicio());
		cbMinInicio.setItems(getMinutosHorario());
		cbHoraFin.setItems(getHorasFin());
		cbMinFin.setItems(getMinutosHorario());
		cbMinDuracion.setItems(getMinutosHorario());

	}

	public void setTutoria(Tutoria tutoria) {
		this.tutoria = tutoria;
	}

	public ObservableList<Integer> getHorasInicio() {
		List<Integer> horasInicio = new ArrayList<>();
		int i = 0;
		int horaInicio;
		do {
			horaInicio = 0 + i;
			i++;
			horasInicio.add(horaInicio);
		} while (horaInicio <= 23);
		ObservableList<Integer> horasiInicioSesion = FXCollections.observableArrayList(horasInicio);

		return horasiInicioSesion;
	}

	public ObservableList<Integer> getHorasFin() {
		List<Integer> horasFin = new ArrayList<>();
		int i = 0;
		int horaFin;
		do {
			horaFin = 0 + i;
			i++;
			horasFin.add(horaFin);
		} while (horaFin <= 23);
		ObservableList<Integer> horasFinSesion = FXCollections.observableArrayList(horasFin);

		return horasFinSesion;
	}

	public ObservableList<Integer> getMinutosHorario() {
		List<Integer> minutos = new ArrayList<>();
		int i = 0;
		int minuto;
		do {
			minuto = 0 + i;
			i++;
			minutos.add(minuto);
		} while (minuto <= 23);
		ObservableList<Integer> minutosHorario = FXCollections.observableArrayList(minutos);

		return minutosHorario;
	}

	private Sesion getSesion() {
		LocalDate fecha = dPFecha.getValue();
		horaInicio = LocalTime.of(cbHoraInicio.getValue(), cbMinInicio.getValue());
		horaFin = LocalTime.of(cbHoraFin.getValue(), cbMinFin.getValue());
		minutosDuracion = cbMinDuracion.getValue();
		return new Sesion(tutoria, fecha, horaInicio, horaFin, minutosDuracion);
	}

}
