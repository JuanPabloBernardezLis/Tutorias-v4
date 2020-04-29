package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvista;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;

import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal implements Initializable {
	
	public static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
	private static final String BORRAR_PROFESOR = "Borrar Profesor";
	private static final String BORRAR_ALUMNO = "Borrar Alumno";
	private static final String BORRAR_TUTORIA = "Borrar Tutoría";
	private static final String BORRAR_SESION = "Borrar Sesión";
	private static final String BORRAR_CITA = "Borrar Cita";
	private static final String RESERVA_ANULADA_OK = "Reserva anulada satisfactoriamente";
	private static final String SEGURO_ANULAR_RESERVA = "¿Estás seguro de que quieres anular la reserva?";
	
	
	private IControlador controladorMVC;
	
   
    private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
    private ObservableList<Cita> citasAlumno = FXCollections.observableArrayList();
    private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
    private ObservableList<Tutoria> tutorias = FXCollections.observableArrayList();
    private ObservableList<Sesion> sesiones = FXCollections.observableArrayList();
    private ObservableList<Cita> citasSesion = FXCollections.observableArrayList();
    
    
    
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML private TableView<Alumno> tvAlumnos;
	@FXML private TableColumn<Alumno, String> tcExpediente;
	@FXML private TableColumn<Alumno, String> tcNombreAlumno;
	@FXML private TableColumn<Alumno, String> tcCorreoAlumno;
	
	@FXML private TableView<Cita> tvCitasAlumno;
	@FXML private TableColumn<Cita, String> tcFechaCitaAlumno;
	@FXML private TableColumn<Cita, String> tcHoraCitaAlumno;
	
	@FXML private TableView<Profesor> tvProfesores;
	@FXML private TableColumn<Profesor, String> tcDniProfesor;
	@FXML private TableColumn<Profesor, String> tcNombreProfesor;
	@FXML private TableColumn<Profesor, String> tcCorreoProfesor;
	
	@FXML private TableView<Tutoria> tvTutorias;
	@FXML private TableColumn<Tutoria, String> tcNombreTutoria;
    
	@FXML private TableView<Sesion> tvSesiones;
	@FXML private TableColumn<Sesion, String> tcFechaSesion;
	@FXML private TableColumn<Sesion, String> tcInicioSesion;
	@FXML private TableColumn<Sesion, String> tcFinSesion;
	@FXML private TableColumn<Sesion, String> tcDuracionSesion;
	
	@FXML private TableView<Cita> tvCitasSesion;
	@FXML private TableColumn<Cita, String> tcHoraCitaSesion;
	@FXML private TableColumn<Cita, String> tcAlumnoCitaSesion;
	

    private Stage anadirProfesor;
    private ControladorAnadirProfesor cAnadirProfesor;
    private Stage anadirAlumno;
	private ControladorAnadirAlumno cAnadirAlumno;
	private Stage anadirCitaSesion;
	private ControladorAnadirCitaSesion cAnadirCitaSesion;
	private Stage anadirCitaAlumno;
	private ControladorAnadirCitaAlumno cAnadirCitaAlumno;
	private Stage anadirTutoria;
	private ControladorAnadirTutoria cAnadirTutoria;
	private Stage anadirSesion;
	private ControladorAnadirSesion cAnadirSesion;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tcExpediente.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getExpediente()));
    	tcNombreAlumno.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getNombre()));
    	tcCorreoAlumno.setCellValueFactory(alumno -> new SimpleStringProperty(alumno.getValue().getCorreo()));
    	tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarCitasAlumno(nv));
    	tvAlumnos.setItems(alumnos);
    	
    	tcFechaCitaAlumno.setCellValueFactory(cita -> new SimpleStringProperty(FORMATO_FECHA.format(cita.getValue().getSesion().getFecha())));
    	tcHoraCitaAlumno.setCellValueFactory(cita -> new SimpleStringProperty(FORMATO_HORA.format(cita.getValue().getHora())));
     	tvCitasAlumno.setItems(citasAlumno);
    	
     	tcDniProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getDni()));
     	tcNombreProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
     	tcCorreoProfesor.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
     	tvProfesores.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarTutoriasProfesor(nv));
     	tvProfesores.setItems(profesores);
     	
     	tcNombreTutoria.setCellValueFactory(tutoria -> new SimpleStringProperty(tutoria.getValue().getNombre()));
     	tvTutorias.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarSesionesTutoria(nv));
     	tvTutorias.setItems(tutorias);
     	
     	tcFechaSesion.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATO_FECHA.format(sesion.getValue().getFecha())));
     	tcInicioSesion.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATO_HORA.format(sesion.getValue().getHoraInicio())));
     	tcFinSesion.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATO_HORA.format(sesion.getValue().getHoraFin())));
     	tcDuracionSesion.setCellValueFactory(sesion -> new SimpleStringProperty(String.valueOf(sesion.getValue().getMinutosDuracion())));
     	tvSesiones.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarCitasSesion(nv));
     	tvSesiones.setItems(sesiones);
     	
     	tcAlumnoCitaSesion.setCellValueFactory(cita -> new SimpleStringProperty(cita.getValue().getAlumno().getNombre()));
     	tcHoraCitaSesion.setCellValueFactory(cita -> new SimpleStringProperty(FORMATO_HORA.format(cita.getValue().getHora())));
     	tvCitasSesion.setItems(citasSesion); 
    }
	
    @FXML
	private void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}
	
	@FXML
	private void acercaDe() throws IOException {
		VBox contenido = FXMLLoader.load(getClass().getResource("../vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Gestión de tutorías", contenido);
	}
	
	public void inicializa() {
		profesores.setAll(controladorMVC.getProfesores());
		alumnos.setAll(controladorMVC.getAlumnos());
	}
	
	  @FXML
	   void anadirAlumno(ActionEvent event) throws IOException {
	    crearAnadirAlumno();
		anadirAlumno.showAndWait();
	}
	
	
	@FXML
	void borrarAlumno(ActionEvent event) {
		Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_ALUMNO,
					"¿Estás seguro de que quieres borrar el alumno?", null)) {
				controladorMVC.borrar(alumno);
				alumnos.remove(alumno);
				Dialogos.mostrarDialogoInformacion(BORRAR_ALUMNO, "Alumno borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_ALUMNO, e.getMessage());
		}
	}
	
	@FXML
	void anadirProfesor(ActionEvent event) throws IOException {
		crearAnadirProfesor();
		anadirProfesor.showAndWait();
	}

	@FXML
	void borrarProfesor(ActionEvent event) {
		Profesor profesor = null;
		try {
			profesor = tvProfesores.getSelectionModel().getSelectedItem();
			if (profesor != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PROFESOR,
					"¿Estás seguro de que quieres borrar el profesor?", null)) {
				controladorMVC.borrar(profesor);
				profesores.remove(profesor);
				Dialogos.mostrarDialogoInformacion(BORRAR_PROFESOR, "Profesor borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PROFESOR, e.getMessage());
		}
	}
 
    @FXML
    void anadirTutoria(ActionEvent event)  throws IOException  {
    	crearAnadirTutoria();
    	anadirTutoria.showAndWait();
    }
    
	@FXML
	void borrarTutoria(ActionEvent event) {
		Tutoria tutoria = null;
		try {
			tutoria = tvTutorias.getSelectionModel().getSelectedItem();
			if (tutoria != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_TUTORIA,
					"¿Estás seguro de que quieres borrar la tutoría?", null)) {
				controladorMVC.borrar(tutoria);
				tutorias.remove(tutoria);
				Dialogos.mostrarDialogoInformacion(BORRAR_TUTORIA, "Tutoría borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_TUTORIA, e.getMessage());
		}
	}

	@FXML
	void anadirSesion(ActionEvent event) throws IOException {
		crearAnadirSesion();
		anadirSesion.showAndWait();
	}

	@FXML
	void borrarSesion(ActionEvent event) {
		Sesion sesion = null;
		try {
			sesion = tvSesiones.getSelectionModel().getSelectedItem();
			if (sesion != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_SESION,
					"¿Estás seguro de que quieres borrar la sesión?", null)) {
				controladorMVC.borrar(sesion);
				sesiones.remove(sesion);
				Dialogos.mostrarDialogoInformacion(BORRAR_SESION, "Sesión borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_SESION, e.getMessage());
		}
	}

	@FXML
	void anadirCitaSesion(ActionEvent event) throws IOException {
		crearAnadirCitaSesion();
		anadirCitaSesion.showAndWait();
	}
   
	@FXML
	void borrarCitaSesion(ActionEvent event) {
		Cita cita = null;
		try {
			cita = tvCitasSesion.getSelectionModel().getSelectedItem();
			if (cita != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_CITA,
					"¿Estás seguro de que quieres borrar la sesión?", null)) {
				controladorMVC.borrar(cita);
				citasSesion.remove(cita);
				Dialogos.mostrarDialogoInformacion(BORRAR_CITA, "Cita borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_CITA, e.getMessage());
		}
	}

	@FXML
	void anadirCitaAlumno(ActionEvent event) throws IOException {
		crearAnadirCitaAlumno();
		anadirCitaAlumno.showAndWait();
	}
   
	@FXML
	void borrarCitaAlumno(ActionEvent event) {
		Cita cita = null;
		try {
			cita = tvCitasAlumno.getSelectionModel().getSelectedItem();
			if (cita != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_CITA,
					"¿Estás seguro de que quieres borrar la sesión?", null)) {
				controladorMVC.borrar(cita);
				citasSesion.remove(cita);
				Dialogos.mostrarDialogoInformacion(BORRAR_CITA, "Cita borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_CITA, e.getMessage());
		}
	}

   
	private void crearAnadirProfesor() throws IOException {
		if (anadirProfesor == null) {
			anadirProfesor = new Stage();
			FXMLLoader cargadorAnadirProfesor = new FXMLLoader(getClass().getResource("../vistas/AnadirProfesor.fxml"));
			VBox raizAnadirProfesor = cargadorAnadirProfesor.load();
			cAnadirProfesor = cargadorAnadirProfesor.getController();
			cAnadirProfesor.setControladorMVC(controladorMVC);
			cAnadirProfesor.setProfesores(profesores);
			cAnadirProfesor.inicializa();
			Scene escenaAnadirProfesor = new Scene(raizAnadirProfesor);
			anadirProfesor.setTitle("Añadir Profesor");
			anadirProfesor.initModality(Modality.APPLICATION_MODAL);
			anadirProfesor.setScene(escenaAnadirProfesor);
		} else {
			cAnadirProfesor.inicializa();
		}
	}
	
	private void crearAnadirAlumno() throws IOException {
		if (anadirAlumno == null) {
			anadirAlumno = new Stage();
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(getClass().getResource("../vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			cAnadirAlumno.setAlumnos(alumnos);
			cAnadirAlumno.inicializa();
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.initModality(Modality.APPLICATION_MODAL);
			anadirAlumno.setScene(escenaAnadirAlumno);
		} else {
			cAnadirAlumno.inicializa();
		}
	}
	
	private void crearAnadirTutoria() throws IOException {
		Profesor profesor=tvProfesores.getSelectionModel().getSelectedItem();
		if (anadirTutoria == null) {
			anadirTutoria = new Stage();
			FXMLLoader cargadorAnadirTutoria = new FXMLLoader(getClass().getResource("../vistas/AnadirTutoria.fxml"));
			VBox raizAnadirTutoria = cargadorAnadirTutoria.load();
			cAnadirTutoria = cargadorAnadirTutoria.getController();
			cAnadirTutoria.setControladorMVC(controladorMVC);
			cAnadirTutoria.setProfesor(profesor);
			cAnadirTutoria.setTutorias(tutorias);
			cAnadirTutoria.inicializa();
			Scene escenaAnadirTutoria = new Scene(raizAnadirTutoria);
			anadirTutoria.setTitle("Añadir tutoría");
			anadirTutoria.initModality(Modality.APPLICATION_MODAL);
			anadirTutoria.setScene(escenaAnadirTutoria);

		} else {
			cAnadirTutoria.inicializa();
		}
	}
	
	private void crearAnadirSesion() throws IOException {
		Tutoria tutoria=tvTutorias.getSelectionModel().getSelectedItem();
			if (anadirSesion == null) {
			anadirSesion = new Stage();
			FXMLLoader cargadorAnadirSesion = new FXMLLoader(getClass().getResource("../vistas/AnadirSesion.fxml"));
			VBox raizAnadirSesion = cargadorAnadirSesion.load();
			cAnadirSesion = cargadorAnadirSesion.getController();
			cAnadirSesion.setControladorMVC(controladorMVC);
			cAnadirSesion.setTutoria(tutoria);
			cAnadirSesion.setSesiones(sesiones);
			cAnadirSesion.inicializa();
			Scene escenaAnadirSesion=new Scene (raizAnadirSesion);
			anadirSesion.setTitle("Añadir Sesion");
			anadirSesion.initModality(Modality.APPLICATION_MODAL);
			anadirSesion.setScene(escenaAnadirSesion);

		} else {
			cAnadirSesion.inicializa();
		}
	}

	private void crearAnadirCitaSesion() throws IOException {
		Sesion sesion = tvSesiones.getSelectionModel().getSelectedItem();
		if (anadirCitaSesion == null) {
			anadirCitaSesion = new Stage();
			FXMLLoader cargadorAnadirCitaSesion = new FXMLLoader(getClass().getResource("../vistas/AnadirCitaSesion.fxml"));
			VBox raizAnadirCitaSesion = cargadorAnadirCitaSesion.load();
			cAnadirCitaSesion = cargadorAnadirCitaSesion.getController();
			cAnadirCitaSesion.setControladorMVC(controladorMVC);
			cAnadirCitaSesion.inicializa(alumnos, citasSesion, sesion);
			Scene escenaAnadirCitaSesion=new Scene (raizAnadirCitaSesion);
			anadirCitaSesion.setTitle("Añadir Cita");
			anadirCitaSesion.initModality(Modality.APPLICATION_MODAL);
			anadirCitaSesion.setScene(escenaAnadirCitaSesion);

		} else {
			cAnadirCitaSesion.inicializa(alumnos, citasSesion, sesion);
		}
	}  
	
	private void crearAnadirCitaAlumno() throws IOException {
		Alumno alumno = tvAlumnos.getSelectionModel().getSelectedItem();
		if (anadirCitaAlumno == null) {
			anadirCitaSesion = new Stage();
			FXMLLoader cargadorAnadirCitaAlumno = new FXMLLoader(getClass().getResource("../vistas/AnadirCitaAlumno.fxml"));
			VBox raizAnadirCitaAlumno = cargadorAnadirCitaAlumno.load();
			cAnadirCitaAlumno = cargadorAnadirCitaAlumno.getController();
			cAnadirCitaAlumno.setControladorMVC(controladorMVC);
			cAnadirCitaAlumno.inicializa(tutorias, citasAlumno, alumno);
			Scene escenaAnadirCitaAlumno=new Scene (raizAnadirCitaAlumno);
			anadirCitaAlumno.setTitle("Añadir Cita");
			anadirCitaAlumno.initModality(Modality.APPLICATION_MODAL);
			anadirCitaAlumno.setScene(escenaAnadirCitaAlumno);

		} else {
			cAnadirCitaAlumno.inicializa(tutorias, citasAlumno, alumno);
		}
	} 
	
    public void mostrarCitasAlumno(Alumno alumno) {
    	try {
    		if (alumno != null) {
    			citasAlumno.setAll(controladorMVC.getCitas(alumno));
    		}
		} catch (IllegalArgumentException e) {
			citasAlumno.setAll(FXCollections.observableArrayList());
		}
    }

    public void mostrarTutoriasProfesor(Profesor profesor) {
    	try {
    		if (profesor != null) {
    			tutorias.setAll(controladorMVC.getTutorias(profesor));
    		}
		} catch (IllegalArgumentException e) {
			tutorias.setAll(FXCollections.observableArrayList());
		}
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
