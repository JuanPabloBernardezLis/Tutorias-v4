package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;



import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirCitaSesion implements Initializable{
		
		private LocalTime hora;
		private Alumno alumno;
		private Sesion sesion;
		public static final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
		
		private ObservableList<Alumno> alumnos=FXCollections.observableArrayList();
		private ObservableList<LocalTime>horarioCitasSesion=FXCollections.observableArrayList();
		private ObservableList<Cita> citas=FXCollections.observableArrayList();
		
		private IControlador controladorMVC;
		
	
		@FXML
		private TableView<Alumno> tvAlumnos;
		@FXML
		private TableColumn<Alumno, String> tcAlumno;
		@FXML
		private ComboBox<LocalTime> cbHora;
		@FXML
		private Button btAceptar;
		@FXML
		private Button btCancelar;
	
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
		
			
			tcAlumno.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getNombre()));			
			tvAlumnos.setItems(alumnos);
			tvAlumnos.getSelectionModel().clearSelection();
			
			cbHora.getSelectionModel().clearSelection();
			cbHora.setItems(horarioCitasSesion);

		}
		
		public void setControladorMVC(IControlador controladorMVC) {
			this.controladorMVC = controladorMVC;
		}
		

		public void inicializa(ObservableList<Alumno> alumnos, ObservableList<Cita> citas, Sesion sesion) {
			
			this.alumnos.setAll(alumnos);			
			this.citas = citas;
			this.sesion=sesion;
			
			tvAlumnos.getSelectionModel().clearSelection();
			
			cbHora.getSelectionModel().clearSelection();
			
			setHoraCitas(sesion);
			
		}

		@FXML
		void anadirCitaSesion(ActionEvent event) {

			Cita cita = null;
			try {
				cita = getCita();
				controladorMVC.insertar(cita);
				citas.setAll(controladorMVC.getCitas(sesion));
				Stage propietario = ((Stage) btAceptar.getScene().getWindow());
				Dialogos.mostrarDialogoInformacion("Añadir Cita", "Cita añadida satisfactoriamente", propietario);
			} catch (Exception e) {
				Dialogos.mostrarDialogoError("Añadir Cita", e.getMessage());
			}
		}
		
	    @FXML
	    void cancelar(ActionEvent event) {
	    	((Stage) btCancelar.getScene().getWindow()).close();
	    }
	
	  
	    	
		private Cita getCita() {
			final DateTimeFormatter FORMATO_HORA= DateTimeFormatter.ofPattern("HH:mm");
			Alumno alumno=tvAlumnos.getSelectionModel().getSelectedItem();
			LocalTime hora=LocalTime.parse(cbHora.getValue().format(FORMATO_HORA)); 
			
			return new Cita(alumno, sesion, hora);
		}

		public void setHoraCitas(Sesion sesion) {
			List<LocalTime> horarioCitasSesionAl = new ArrayList<>();
			LocalTime horaCita;
			horaCita = sesion.getHoraInicio();

			do {
				horarioCitasSesionAl.add(horaCita);
				horaCita = horaCita.plusMinutes(sesion.getMinutosDuracion());

			} while (horaCita.isBefore(sesion.getHoraFin()));// .minusMinutes(sesion.getMinutosDuracion())));

			horarioCitasSesion.setAll(FXCollections.observableArrayList(horarioCitasSesionAl));

		}
}
