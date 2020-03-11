package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;


import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;

public class Tutorias implements ITutorias {
	
	
	private List<Tutoria> coleccionTutorias;
	
	public Tutorias() {
		coleccionTutorias = new ArrayList<>();
	}
	
	@Override
	public List<Tutoria> get() {
		List<Tutoria> TutoriasOrdenadas = copiaProfundaTutorias();
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getDni);
		//Comparator<Tutoria> comparadorNombreTutoria = Comparator.comparing(Tutoria::getNombre);
		TutoriasOrdenadas.sort(Comparator.comparing(Tutoria::getProfesor, comparadorProfesor).thenComparing(Tutoria::getNombre));
		return TutoriasOrdenadas;
	}
	
	
	private List<Tutoria> copiaProfundaTutorias() {
		List<Tutoria> copiaTutorias = new ArrayList<>();
		for (Tutoria tutoria : coleccionTutorias) {
			copiaTutorias.add(new Tutoria(tutoria));
		}
		return copiaTutorias;
	}
	
	
	@Override
	public List<Tutoria> get(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Tutoria> tutoriasProfesor = new ArrayList<>();
		for (Tutoria tutoria : coleccionTutorias) {
			if (tutoria.getProfesor().equals(profesor)) {
				tutoriasProfesor.add(new Tutoria(tutoria));
			}
		}
		//Comparator<Tutoria> comparadorNombreTutoria = Comparator.comparing(Tutoria::getNombre);
		tutoriasProfesor.sort(Comparator.comparing(Tutoria::getNombre));
		return tutoriasProfesor;
	}
	
	@Override
	public int getTamano() {
		return coleccionTutorias.size();
	}
	
	
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		int indice = coleccionTutorias.indexOf(tutoria);
		if (indice == -1) {
			coleccionTutorias.add(new Tutoria(tutoria));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}		
		
	}
	
	@Override
	public Tutoria buscar(Tutoria tutoria) {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		int indice = coleccionTutorias.indexOf(tutoria);
		if (indice == -1) {
			return null;
		} else {
			return new Tutoria(coleccionTutorias.get(indice));
		}
	}
	
	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		int indice = coleccionTutorias.indexOf(tutoria);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		} else {
			coleccionTutorias.remove(indice);
		}
	}

}