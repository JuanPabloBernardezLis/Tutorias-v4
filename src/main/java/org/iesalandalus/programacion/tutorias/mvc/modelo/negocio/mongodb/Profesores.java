package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.mongodb;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IFuenteDatos;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ICitas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ISesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Profesores implements IProfesores {
	
private static final String COLECCION = "profesores";
	
	private MongoCollection<Document> coleccionProfesores;

	@Override
	public void comenzar() {
		coleccionProfesores = MongoDB.getBD().getCollection(COLECCION);
		
	}
	
	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Profesor> get() {
		List<Profesor> profesores = new ArrayList<>();
		for (Document documentoProfesor : coleccionProfesores.find().sort(MongoDB.getCriterioOrdenacionProfesor())) {
			profesores.add(MongoDB.getProfesor(documentoProfesor));
		}
		return profesores;
	}


	@Override
	public int getTamano() {
		return (int)coleccionProfesores.countDocuments();
	}

	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede insertar un profesor nulo.");
		}
		if (buscar(profesor) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		} else {
			coleccionProfesores.insertOne(MongoDB.getDocumento(profesor));
		} 
	}

	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		Document documentoProfesor = coleccionProfesores.find()
				.filter(eq(MongoDB.DNI, profesor.getDni())).first();
		return MongoDB.getProfesor(documentoProfesor);	
	}

	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (buscar(profesor) != null) {
			coleccionProfesores.deleteOne(eq(MongoDB.DNI, profesor.getDni()));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		} 
	}

	
}
