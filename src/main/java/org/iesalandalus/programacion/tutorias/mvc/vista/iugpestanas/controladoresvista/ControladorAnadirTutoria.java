package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvista;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirTutoria implements Initializable {

	private static final String ER_NOMBRE = "[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚ]+)+";
	Profesor profesor;
	String nombre;

	private IControlador controladorMVC;
	private ObservableList<Tutoria> tutorias;

	@FXML
	private TextField tfNombre;
	@FXML
	private Button btCancelar;
	@FXML
	private Button btAceptar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfNombre.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombre));
	}

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setTutorias(ObservableList<Tutoria> tutorias) {
		this.tutorias = tutorias;
	}

	@FXML
	void anadirTutoria(ActionEvent event) {
		Tutoria tutoria = null;
		try {
			tutoria = getTutoria();
			controladorMVC.insertar(tutoria);
			tutorias.setAll(controladorMVC.getTutorias());
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Tutoría", "Tutoría añadida satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Tutoría", e.getMessage());
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	public void inicializa() {
		tfNombre.setText("");
		compruebaCampoTexto(ER_NOMBRE, tfNombre);
	}

	private void compruebaCampoTexto(String er, TextField campoTexto) {
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		} else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}

	private Tutoria getTutoria() {
		String nombre = tfNombre.getText();

		return new Tutoria(profesor, nombre);
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
}
