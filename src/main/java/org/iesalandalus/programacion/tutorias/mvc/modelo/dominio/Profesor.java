package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.Compression;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Profesor implements Serializable {

	private static final String ER_NOMBRE = "[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚ]+)+";
	private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";

	private String nombre;
	private String dni;
	private String correo;

	public Profesor(String nombre, String dni, String correo) {

	
		setNombre(nombre);
		setDni(dni);
		setCorreo(correo);
	}

	public Profesor(Profesor p) {
		if (p == null) {
			throw new NullPointerException("ERROR: No es posible copiar un profesor nulo.");
		}
		setNombre(p.getNombre());
		setDni(p.getDni());
		setCorreo(p.getCorreo());
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {

		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}

		if (nombre.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}

		if (!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}

		this.nombre = formateaNombre(nombre);
	}

private String formateaNombre(String nombre) {
		
		
		String nombreSinEspaciosIYF=nombre.trim();
		String nombreSinEspaciosSobrantes=nombreSinEspaciosIYF.replaceAll("\\s+", " ").toLowerCase();
		char[] nombreArray = nombreSinEspaciosSobrantes.toCharArray();	
			
		for(int i = 0; i<nombreArray.length; i++) {

	           if(nombreArray[i] == ' '){

	        	   nombreArray[i+1] = Character.toUpperCase(nombreArray[i+1]);

	           }
	         
		}
		
	    String nombreFormateado = new String(nombreArray);
	    nombreFormateado=Character.toUpperCase(nombreFormateado.charAt(0)) + nombreFormateado.substring(1); 
		return nombreFormateado;
		
		
	
	}
	

	public String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		
		
		if (dni==null) {
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		
		if (!comprobarLetraDni(dni)) {
			
			throw new IllegalArgumentException("ERROR: El DNI no es válido.");	
		}
		
		
		
		this.dni = dni;
	}


	
	private boolean comprobarLetraDni(String dni) {
		
		boolean dniEsCorrecto=false;
		Pattern patron;
		Matcher comparador;
		
		char[] LETRAS_DNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};

		patron = Pattern.compile(ER_DNI);
		comparador = patron.matcher(dni);
		
		if(!comparador.matches()) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		
		int cifrasDni=Integer.parseInt(comparador.group(1));
		int resto=cifrasDni%23;
		
		if (LETRAS_DNI [resto]==comparador.group(2).charAt(0)) {
			
			dniEsCorrecto=true;
			
			}else {
				throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta."); 
			}
		
		return dniEsCorrecto;
			
	}

	public String getCorreo() {
		return correo;
	}

	private void setCorreo(String correo) {
		
		if (correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if (!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}
	
	public static Profesor getProfesorFicticio(String dni) {
		
		if(dni==null) {
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		
		return new Profesor("Profesor Ficticio",dni, "correo@correo.es");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "nombre=" + nombre + " ("+getIniciales()+ "), DNI=" + dni + ", correo=" + correo;
	}
	
private String getIniciales() {
		
		
		String[] palabras = nombre.split("\\s");

		String iniciales = "";
		for (int i = 0; i < palabras.length; i++) {
			if (!palabras[i].equals(""))
				iniciales = iniciales + palabras[i].charAt(0);
		}
	
	return iniciales;
	
	}	
}
