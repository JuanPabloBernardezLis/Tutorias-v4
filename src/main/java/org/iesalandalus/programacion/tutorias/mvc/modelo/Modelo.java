package org.iesalandalus.programacion.tutorias.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ICitas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ISesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Alumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Citas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Profesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Sesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Tutorias;



public class Modelo implements IModelo {

	
	private IAlumnos alumnos;
	private ICitas citas;
	private IProfesores profesores;
	private ISesiones sesiones;
	private ITutorias tutorias;
	
	public Modelo(IFuenteDatos fuenteDatos) {
		
		alumnos = fuenteDatos.crearAlumnos();
		citas = fuenteDatos.crearCitas();
		profesores = fuenteDatos.crearProfesores();
		sesiones = fuenteDatos.crearSesiones();
		tutorias = fuenteDatos.crearTutorias();


	}
	
	@Override
	public void comenzar() {
		alumnos.comenzar();
		profesores.comenzar();
		tutorias.comenzar();
		sesiones.comenzar();
		citas.comenzar();
		
	}

	@Override
	public void terminar() {
		alumnos.terminar();
		profesores.terminar();
		tutorias.terminar();
		sesiones.terminar();
		citas.terminar();
	}
	
	
	
	
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if(alumno==null)
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		
		alumnos.insertar(alumno);
	}
	
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor==null)
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		
		profesores.insertar(profesor);
	}
	
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		if(tutoria==null)
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		
		Profesor profesorTutoria=profesores.buscar(tutoria.getProfesor());
		
		if(profesorTutoria==null)
			throw new OperationNotSupportedException("ERROR: No existe el profesor de esta tutoría.");
		
		tutorias.insertar(new Tutoria(profesorTutoria,tutoria.getNombre()));
	}
	
	@Override
	public void insertar(Sesion sesion) throws OperationNotSupportedException {
		if(sesion==null)
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		
		Tutoria tutoriaSesion=tutorias.buscar(sesion.getTutoria());
		
		if(tutoriaSesion==null)
			throw new OperationNotSupportedException("ERROR: No existe la tutoría de esta sesión.");
		
		sesiones.insertar(new Sesion(tutoriaSesion, sesion.getFecha(),sesion.getHoraInicio(),sesion.getHoraFin(),sesion.getMinutosDuracion()));
	}
	
	@Override
	public void insertar(Cita cita) throws OperationNotSupportedException {
		if(cita==null)
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		
		Alumno alumno=alumnos.buscar(cita.getAlumno());
		Sesion sesion=sesiones.buscar(cita.getSesion());
		
		
		if(alumno==null)
			throw new OperationNotSupportedException("ERROR: No existe el alumno de esta cita.");
		if(sesion==null)
			throw new OperationNotSupportedException("ERROR: No existe la sesión de esta cita.");
		
		
		citas.insertar(new Cita(alumno,sesion,cita.getHora()));
	}
	
	@Override
	public Alumno buscar(Alumno alumno) {
		if(alumno==null)
			throw new NullPointerException("ERROR: No se puede buscar un alumno nulo.");
		
		return alumnos.buscar(alumno);
	}
	@Override
	public Profesor buscar(Profesor profesor) {
		if(profesor==null)
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		
		return profesores.buscar(profesor);
	}
	
	@Override
	public Tutoria buscar(Tutoria tutoria) {
		if(tutoria==null)
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		
		return tutorias.buscar(tutoria);
	}
	
	@Override
	public Sesion buscar(Sesion sesion) {
		if(sesion==null)
			throw new NullPointerException("ERROR: No se puede buscar una sesión nula.");
		
		return sesiones.buscar(sesion);
	}
	
	@Override
	public Cita buscar(Cita cita) {
		if(cita==null)
			throw new NullPointerException("ERROR: No se puede buscar una cita nula.");
		
		return citas.buscar(cita);
	}
	
	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if(alumno==null)
			throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
		
		List<Cita> citasAlummno = citas.get(alumno);
		for (Cita cita : citasAlummno) {
			citas.borrar(cita);
		}
		alumnos.borrar(alumno);
	}
	
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if(profesor==null)
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		
		List<Tutoria> tutoriasProfesor = tutorias.get(profesor);
		for (Tutoria tutoria : tutoriasProfesor) {
			tutorias.borrar(tutoria);
		}
		
		profesores.borrar(profesor);
	}
	
	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if(tutoria==null)
			throw new NullPointerException("ERROR: No se puede borrar una tutoría nula.");
		
		List<Sesion> sesionesTutoria = sesiones.get(tutoria);
		for (Sesion sesion : sesionesTutoria) {
			sesiones.borrar(sesion);
		}
		
		tutorias.borrar(tutoria);
	}
	
	@Override
	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if(sesion==null)
			throw new NullPointerException("ERROR: No se puede borrar una sesión nula.");
		
		List<Cita> citasSesion = citas.get(sesion);
		for (Cita cita : citasSesion) {
			citas.borrar(cita);
		}
		sesiones.borrar(sesion);
	}
	
	@Override
	public void borrar(Cita cita) throws OperationNotSupportedException {
		if(cita==null)
			throw new NullPointerException("ERROR: No se puede borrar una cita nula.");
		
		citas.borrar(cita);
	}
	
	
	@Override
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}
	
	@Override
	public List<Profesor> getProfesores() {
		return profesores.get();
	}
	
	@Override
	public List<Tutoria> getTutorias() {
		return tutorias.get();
	}
	
	@Override
	public List<Tutoria> getTutorias(Profesor profesor) {
		if(profesor==null)
			throw new NullPointerException("ERROR: No se pueden listar las tutorías de un profesor nulo.");
		
		return tutorias.get(profesor);
	}
	@Override
	public List<Sesion> getSesiones() {
		return sesiones.get();
	}
	
	@Override
	public List<Sesion> getSesiones(Tutoria tutoria){
		if(tutoria==null)
			throw new NullPointerException("ERROR: No se pueden listar las sesiones de una tutoría nula.");
		
		return sesiones.get(tutoria);
	}
	@Override
	public List<Cita> getCitas() {
		return citas.get();
	}
	
	@Override
	public List<Cita> getCitas(Sesion sesion) {
		if(sesion==null)
			throw new NullPointerException("ERROR: No se pueden listar las citas de una sesión nula.");
		
		return citas.get(sesion);
	}
	
	@Override
	public List<Cita> getCitas(Alumno alumno) {
		if(alumno==null)
			throw new NullPointerException("ERROR: No se pueden listar las citas de un alumno nulo.");
		
		return citas.get(alumno);
	}
	
	
	
	
	
}
