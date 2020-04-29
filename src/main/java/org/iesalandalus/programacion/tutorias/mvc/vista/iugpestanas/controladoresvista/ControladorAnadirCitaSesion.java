package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvista;



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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirCitaSesion implements Initializable{
		
		private LocalTime hora;
		private Alumno alumno;
		private Sesion sesion;
		public static final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
		
		private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
		
		private IControlador controladorMVC;
		private ObservableList<Cita> citas;
	
		@FXML private ListView<Alumno> lvAlumnos;
		@FXML private ComboBox<LocalTime> cbHora;
	    @FXML private Button btAceptar;
	    @FXML private Button btCancelar;
	  
	
	    private class CeldaAlumno extends ListCell<Alumno>{
	    	@Override
	    	public void updateItem (Alumno alumno, boolean vacio){
	    		super.updateItem(alumno, vacio);
	    		if(vacio) {
	    			setText("");
	    		}else {
	    			setText(alumno.getNombre());
	    		}
	    	}   	
	    }
	    
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
			cbHora.setDisable(true);
			lvAlumnos.setItems(alumnos);
			lvAlumnos.setCellFactory(l -> new CeldaAlumno());
		} 
		
		public void setControladorMVC(IControlador controladorMVC) {
			this.controladorMVC = controladorMVC;
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
	
	    public void inicializa(ObservableList<Alumno> alumnos, ObservableList<Cita> citas, Sesion sesion) {
	    	this.citas=citas;
	    	this.sesion=sesion;
	    	lvAlumnos.setItems(alumnos);
	    	lvAlumnos.getSelectionModel().clearSelection();
	    	cbHora.setItems(getHoraCitas());
	    }
	    	
		private Cita getCita() {
			final DateTimeFormatter FORMATO_HORA= DateTimeFormatter.ofPattern("HH:mm");
			Alumno alumno=lvAlumnos.getSelectionModel().getSelectedItem();
			LocalTime hora=LocalTime.parse(cbHora.getValue().format(FORMATO_HORA)); 
			return new Cita(alumno, sesion, hora);
		}

		
		public ObservableList<LocalTime> getHoraCitas(){
			List<LocalTime> horarioCitasSesionAl= new ArrayList<>();
			int i=0;
			LocalTime horaCita;
			do {
				horaCita=sesion.getHoraInicio().plusMinutes(i*sesion.getMinutosDuracion());
				horaCita.format(FORMATO_HORA);
				i++;
				horarioCitasSesionAl.add(horaCita);
			}while(
				horaCita.isBefore(sesion.getHoraFin().minusMinutes(sesion.getMinutosDuracion()))
				);
			ObservableList<LocalTime>horarioCitasSesion=FXCollections.observableArrayList(horarioCitasSesionAl);
			
			return horarioCitasSesion;		
		}
}
