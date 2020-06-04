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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirCitaAlumno implements Initializable {
	
	public static final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
	public static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalTime hora;
	private Alumno alumno;
	private Sesion sesion;
	
	private ObservableList<Tutoria> tutorias = FXCollections.observableArrayList();
	private ObservableList<Sesion> sesiones = FXCollections.observableArrayList();
	private ObservableList<Sesion> sesionesTutoria = FXCollections.observableArrayList();
	private ObservableList<Cita> citas = FXCollections.observableArrayList();
	
	private IControlador controladorMVC;
	private ObservableList<LocalTime>horarioCitasSesion=FXCollections.observableArrayList();
	private ObservableList<LocalDate> fechasSesiones;

	@FXML
	private TableView<Tutoria> tvTutorias;
	@FXML
	private TableColumn<Tutoria, String> tcTutoria;
	@FXML
	private TableView<Sesion> tvSesiones;
	@FXML
	private TableColumn<Sesion, String> tcFecha;
	
	@FXML
	private ComboBox<LocalTime> cbHora;
	@FXML
	private Button btAceptar;
	@FXML
	private Button btCancelar;

    
       
    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	tvTutorias.getSelectionModel().clearSelection();
    	tcTutoria.setCellValueFactory(tutoria -> new SimpleStringProperty(tutoria.getValue().getNombre()));
    	tvTutorias.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarSesionesTutoria(nv));
     	tvTutorias.setItems(tutorias);
     	
     	cbHora.getSelectionModel().clearSelection();
		cbHora.setValue(null);

     	
     	tcFecha.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATO_FECHA.format(sesion.getValue().getFecha())));
     	tvSesiones.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> setHoraCitas(nv));
     	tvSesiones.setItems(sesionesTutoria);
     	
	
	} 
    

	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
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

    public void inicializa(ObservableList<Tutoria> tutorias, ObservableList<Sesion> sesiones, Alumno alumno, ObservableList<Cita> citasAlumno) {
    	this.tutorias.setAll(controladorMVC.getTutorias());
    	this.sesiones.setAll(controladorMVC.getSesiones());    	
    	this.alumno=alumno;
    	tvTutorias.getSelectionModel().clearSelection();

     }
    
	
   
    
	private Cita getCita() {
		
		sesion=tvSesiones.getSelectionModel().getSelectedItem();
		LocalTime hora=cbHora.getValue(); 
		return new Cita(alumno, sesion, hora);
	}
	
	   public void mostrarSesionesTutoria(Tutoria tutoria) {
	    	
			try {
	    		if (tutoria != null) {
	    			sesionesTutoria.setAll(controladorMVC.getSesiones(tutoria));
	    		}else {sesionesTutoria.setAll(FXCollections.observableArrayList());
				
	    		}
			} catch (IllegalArgumentException e) {
				sesionesTutoria.setAll(FXCollections.observableArrayList());
			}
	    }
	   
	   
		public void setHoraCitas(Sesion sesion) {

			if (sesion != null) {
				List<LocalTime> horarioCitasSesionAl = new ArrayList<>();
				LocalTime horaCita;
				horaCita = sesion.getHoraInicio();

				do {
					horarioCitasSesionAl.add(horaCita);
					horaCita = horaCita.plusMinutes(sesion.getMinutosDuracion());

				} while (horaCita.isBefore(sesion.getHoraFin()));// .minusMinutes(sesion.getMinutosDuracion())));

				horarioCitasSesion.setAll(FXCollections.observableArrayList(horarioCitasSesionAl));
				cbHora.setItems(horarioCitasSesion);
			}else {
				cbHora.setItems(FXCollections.observableArrayList());
			
			}
		}
	    	
			
}
