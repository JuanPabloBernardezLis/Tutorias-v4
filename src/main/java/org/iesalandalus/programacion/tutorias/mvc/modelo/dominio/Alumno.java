package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.Alumnos;

public class Alumno  implements Serializable {
	
	private static final String ER_NOMBRE = "[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚ]+)+";
	private static final String PREFIJO_EXPEDIENTE= "SP_";
	private static final String ER_CORREO= ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	private static int ultimoIdentificador=0;
	private String nombre;
	private String correo;
	private String expediente;
		

	

	
	

	public Alumno(String nombre, String correo) {
		
		setNombre(nombre);
		setCorreo(correo); 
		setExpediente();
	}


	public Alumno(Alumno a) {
		
		if (a==null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(a.getNombre());
		setCorreo(a.getCorreo());
		expediente=a.getExpediente();

	}
	
	public static Alumno getAlumnoFicticio(String correo) {
		
		if (correo==null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		return new Alumno ("Alumno Ficticio", correo);
		
		
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
		
		if (!nombre.matches(ER_NOMBRE)){
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


		public String getExpediente() {
			return expediente;
		}

		private void setUltimoIdentificador() {
			
			
			ultimoIdentificador=Alumnos.getMaxId();
			
		}
		

		private static void incrementaUltimoIdentificador() {
		ultimoIdentificador++;
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

	private void setExpediente() {
		
		setUltimoIdentificador();
		
		incrementaUltimoIdentificador();
		
		this.expediente=PREFIJO_EXPEDIENTE + getIniciales() +"_"+ ultimoIdentificador;
		
		
	}
;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "nombre=" + nombre + " ("+ getIniciales()+"), correo=" + correo + ", expediente=" + expediente;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


	
