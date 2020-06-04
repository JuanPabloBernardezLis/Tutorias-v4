package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;



import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirProfesor implements Initializable{

		private static final String ER_NOMBRE = "[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚ]+)+";
		private static final String ER_DNI = "([0-9]{8})([A-Za-z])"; //"\\d{8}[A-Z]";
		private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";

		private String nombre;
		private String dni;
		private String correo;
		
		private IControlador controladorMVC;
		private ObservableList<Profesor> profesores;
	
	
		@FXML private TextField tfNombreProfesor;
		@FXML private Button btAceptar;
		@FXML private TextField tfDniProfesor;
		@FXML private Button btCancelar;
		@FXML private TextField tfCorreoProfesor;
	
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			//tfNombreProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombreProfesor));
			//tfCorreoProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreoProfesor));
			//tfDniProfesor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_DNI, tfDniProfesor));
		}
		
		public void setControladorMVC(IControlador controladorMVC) {
			this.controladorMVC = controladorMVC;
		}
		
		
		public void setProfesores(ObservableList<Profesor> profesores) {
			this.profesores = profesores;
		}
		
	
	 @FXML
	    void anadirProfesor(ActionEvent event) {

		 Profesor profesor = null;
			try {
				profesor = getProfesor();
				controladorMVC.insertar(profesor);			
				profesores.setAll(controladorMVC.getProfesores());
				Stage propietario = ((Stage) btAceptar.getScene().getWindow());
				Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido satisfactoriamente", propietario);
			} catch (Exception e) {
				Dialogos.mostrarDialogoError("Añadir Profesor", e.getMessage());
			}	
		 
		 
	    }
		
	    @FXML
	    void cancelar(ActionEvent event) {
	    	((Stage) btCancelar.getScene().getWindow()).close();
	    }
	
	    public void inicializa() {
	    	tfNombreProfesor.setText("");
	    	//compruebaCampoTexto(ER_NOMBRE, tfNombreProfesor);
	    	tfCorreoProfesor.setText("");
	    	//compruebaCampoTexto(ER_CORREO, tfCorreoProfesor);
	    	tfDniProfesor.setText("");
	    	/*compruebaCampoTexto(ER_DNI, tfDniProfesor);*/
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
		
		private Profesor getProfesor() {
			String nombre = tfNombreProfesor.getText();
			String correo = tfCorreoProfesor.getText();
			String dni = tfDniProfesor.getCharacters().toString();
			
			return new Profesor(nombre, dni, correo);
		}

	
}