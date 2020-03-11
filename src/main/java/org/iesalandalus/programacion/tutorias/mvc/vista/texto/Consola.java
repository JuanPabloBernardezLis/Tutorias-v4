package org.iesalandalus.programacion.tutorias.mvc.vista.texto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {	
	}
	
	public static void mostrarMenu() {
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoStr, 0).replace("0", "-"));
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}
	
	public static Alumno leerAlumno() {
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		String correo = Entrada.cadena();
		
		Alumno alumno = new Alumno(nombre, correo);
			
		return alumno;
	}
	
	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}
	
	public static Profesor leerProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el DNI del profesor: ");
		String dni = Entrada.cadena();
		System.out.print("Introduce el Correo del profesor: ");
		String correo = Entrada.cadena();
		Profesor profesor = new Profesor(nombre, dni, correo);
			
		return profesor;
	}
	
	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduce el dni del profesor: ");
		return Profesor.getProfesorFicticio(Entrada.cadena());
	}
	
	public static Tutoria leerTutoria() {
		
		System.out.print("Introduce el nombre de la tutoría: ");
		String nombre = Entrada.cadena();
		Profesor profesor = leerProfesorFicticio();
		
		return new Tutoria(profesor, nombre);
	}
	
	public static Sesion leerSesion() {
		
		Tutoria tutoria=leerTutoria();
		System.out.print("Introduce la fecha de la tutoría: ");
		String fechaCadena = Entrada.cadena();
		LocalDate fecha;
		LocalTime horaInicio;
		LocalTime horaFin;
		try {
		fecha = LocalDate.parse(fechaCadena, Sesion.FORMATO_FECHA);
		} catch (DateTimeParseException e) {

			throw new IllegalArgumentException("ERROR: La fecha introducida no tiene el formato adecuado.");

		}
		System.out.print("Introduce la hora de inicio: ");
		String horaInicioCadena = Entrada.cadena();
		try {
		horaInicio=LocalTime.parse(horaInicioCadena, Sesion.FORMATO_HORA);
		} catch (DateTimeParseException e) {

			throw new IllegalArgumentException("ERROR: La hora introducida no tiene el formato adecuado.");

		}
		System.out.print("Introduce la hora de fin: ");
		String horaFinCadena = Entrada.cadena();
		try {
		horaFin=LocalTime.parse(horaFinCadena, Sesion.FORMATO_HORA);
		} catch (DateTimeParseException e) {

			throw new IllegalArgumentException("ERROR: La hora introducida no tiene el formato adecuado.");

		}
		System.out.print("Introduce los minutos de duración: ");
		int minutosDuracion = Entrada.entero();
		
		return new Sesion(tutoria, fecha, horaInicio, horaFin, minutosDuracion);
	}
	
	
	public static Sesion leerSesionFicticia() {
		
		System.out.print("Introduce la fecha de la tutoría: ");
		String fechaCadena = Entrada.cadena();
		LocalDate fecha = LocalDate.parse(fechaCadena, Sesion.FORMATO_FECHA);
		return Sesion.getSesionFicticia(leerTutoria(), fecha);
	
	
	}
public static Cita leerCita() {
		
		System.out.print("Introduce la hora: ");
		String horaCadena = Entrada.cadena();
		LocalTime hora;
        try {

			hora = LocalTime.parse(horaCadena, Cita.FORMATO_HORA);

		} catch (DateTimeParseException e) {

			throw new IllegalArgumentException("ERROR: La hora introducida no tiene el formato adecuado.");

		}
		Alumno alumno=leerAlumno();
		Sesion sesion=leerSesion();
		
		return new Cita(alumno, sesion, hora);
	}

}
	