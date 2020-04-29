 package org.iesalandalus.programacion.tutorias;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.FactoriaFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.controlador.Controlador;
import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.IModelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.Modelo;
import org.iesalandalus.programacion.tutorias.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.texto.VistaTexto;

public class MainApp {

	public static void main(String[] args) {
		System.out.println("Gestión Tutorías del IES Al-Ándalus");
		
		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		IVista vista = FactoriaVista.IUGPESTANAS.crear();//FactoriaVista.TEXTO.crear(); 
		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	

		
	}

}
