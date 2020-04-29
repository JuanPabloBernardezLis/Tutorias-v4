package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvista;

import java.net.URL;
import java.time.LocalDate;
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
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirCitaAlumno implements Initializable {
	
	public static final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
	public static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalTime hora;
	private Alumno alumno;
	private Sesion sesion;
	
	private ObservableList<Tutoria> tutorias = FXCollections.observableArrayList();
	private ObservableList<Sesion> sesiones = FXCollections.observableArrayList();;
	private ObservableList<Cita> citasSesion = FXCollections.observableArrayList();
	private ObservableList<Cita> citas;
	
	private IControlador controladorMVC;
	private ObservableList<LocalTime> horasCitas;

	@FXML
	private ListView<Tutoria> lvTutoriasCitaAlumno;
	@FXML
	private ListView<Sesion> lvFechasSesionesTutoria;
	@FXML
	private ComboBox<LocalTime> cbHoraCitaAlumno;
	@FXML
	private Button btAceptar;
	@FXML
	private Button btCancelar;

    private class CeldaTutoria extends ListCell<Tutoria>{
    	@Override
    	public void updateItem (Tutoria tutoria, boolean vacio){
    		super.updateItem(tutoria, vacio);
    		if(vacio) {
    			setText("");
    		}else {
    			setText(tutoria.getNombre());
    		}
    	}   	
    }
    
    private class CeldaFecha extends ListCell<Sesion>{
    	@Override
    	public void updateItem (Sesion sesion, boolean vacio){
    		super.updateItem(sesion, vacio);
    		if(vacio) {
    			setText("");
    		}else {
    			setText(sesion.getFecha().format(FORMATO_FECHA));
    		}
    	}   	
    }
       
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	cbHoraCitaAlumno.setDisable(true);
    	
    	lvTutoriasCitaAlumno.setItems(tutorias);
    	lvTutoriasCitaAlumno.setCellFactory(l -> new CeldaTutoria());
    	lvTutoriasCitaAlumno.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarSesionesTutoria(nv));
		
    	lvFechasSesionesTutoria.setItems(sesiones);
		lvFechasSesionesTutoria.setCellFactory(l -> new CeldaFecha());
		lvFechasSesionesTutoria.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> setHorario(nv));
		cbHoraCitaAlumno.setItems(horasCitas);
		
	} 
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	
	public void setTutorias(ObservableList<Tutoria> tutorias) {
		this.tutorias = tutorias;
	}
	

 @FXML
    void anadirCitaAlumno(ActionEvent event) {

	 Cita cita = null;
		try {
			cita = getCita();
			controladorMVC.insertar(cita);
			citas.setAll(controladorMVC.getCitas(alumno));
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

    public void inicializa(ObservableList<Tutoria> tutorias, ObservableList<Cita> citas, Alumno alumno) {
    	this.alumno=alumno;
    	this.citas=citas;
    	lvTutoriasCitaAlumno.setItems(tutorias);
    	lvTutoriasCitaAlumno.getSelectionModel().clearSelection();
    	
    }
    

    private void compruebaCampoTexto(String er, TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
	private Cita getCita() {
		final DateTimeFormatter FORMATO_HORA= DateTimeFormatter.ofPattern("HH:mm");
		Tutoria tutoria=lvTutoriasCitaAlumno.getSelectionModel().getSelectedItem();
		LocalTime hora=cbHoraCitaAlumno.getValue(); 
		return new Cita(alumno, sesion, hora);
	}

	
	public void setHorario(Sesion sesion){
		List<LocalTime> horasCitasAl= new ArrayList<>();
		int i=0;
		LocalTime horaCita;
		do {
			horaCita=sesion.getHoraInicio().plusMinutes(i*sesion.getMinutosDuracion());
			horaCita.format(FORMATO_HORA);
			i++;
			horasCitasAl.add(horaCita);
		}while(
			horaCita.isBefore(sesion.getHoraFin().minusMinutes(sesion.getMinutosDuracion()))
			);
		horasCitas=FXCollections.observableArrayList(horasCitasAl);
		
				
	}

	
	   public void mostrarSesionesTutoria(Tutoria tutoria) {
	    	
			try {
	    		if (tutoria != null) {
	    			sesiones.setAll(controladorMVC.getSesiones(tutoria));
	    		}
			} catch (IllegalArgumentException e) {
				sesiones.setAll(FXCollections.observableArrayList());
			}
	    }

	    public void mostrarCitasSesion(Sesion sesion) {
	    	
			try {
	    		if (sesion != null) {
	    			citasSesion.setAll(controladorMVC.getCitas(sesion));
	    		}
			} catch (IllegalArgumentException e) {
				citasSesion.setAll(FXCollections.observableArrayList());
			}
	    }
}
