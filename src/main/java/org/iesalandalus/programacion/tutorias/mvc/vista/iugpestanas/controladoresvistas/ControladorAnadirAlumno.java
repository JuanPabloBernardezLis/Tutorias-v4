package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;



import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirAlumno implements Initializable{

		private static final String ER_NOMBRE = "[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚ]+)+";
		private static final String ER_CORREO= ".+@[a-zA-Z]+\\.[a-zA-Z]+";
		
		private IControlador controladorMVC;
		private ObservableList<Alumno> alumnos;
	
	
		@FXML private TextField tfCorreo;
	    @FXML private TextField tfNombre;
	    @FXML private Button btCancelar;
		@FXML private Button btAceptar;
	  
	
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			tfNombre.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombre));
			tfCorreo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreo));
			
		}
		
		public void setControladorMVC(IControlador controladorMVC) {
			this.controladorMVC = controladorMVC;
		}
		
		
		public void setAlumnos(ObservableList<Alumno> alumnos) {
			this.alumnos = alumnos;
		}
		
	
	 @FXML
	    void anadirAlumno(ActionEvent event) {

		 Alumno alumno = null;
			try {
				alumno = getAlumno();
				controladorMVC.insertar(alumno);
				alumnos.setAll(controladorMVC.getAlumnos());
				Stage propietario = ((Stage) btAceptar.getScene().getWindow());
				Dialogos.mostrarDialogoInformacion("Añadir Alumno", "Alumno añadido satisfactoriamente", propietario);
			} catch (Exception e) {
				Dialogos.mostrarDialogoError("Añadir Alumno", e.getMessage());
			}	
		 
		 
	    }
		
	    @FXML
	    void cancelar(ActionEvent event) {
	    	((Stage) btCancelar.getScene().getWindow()).close();
	    }
	
	    public void inicializa() {
	    	tfNombre.setText("");
	    	compruebaCampoTexto(ER_NOMBRE, tfNombre);
	    	tfCorreo.setText("");
	    	compruebaCampoTexto(ER_CORREO, tfCorreo);
	    	
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
		
		private Alumno getAlumno() {
			String nombre = tfNombre.getText();
			String correo = tfCorreo.getText();
			
			return new Alumno(nombre, correo);
		}

	

	
   
	
	
	
	
}
