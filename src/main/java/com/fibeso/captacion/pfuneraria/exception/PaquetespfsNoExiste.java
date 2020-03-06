package com.fibeso.captacion.pfuneraria.exception;

public class PaquetespfsNoExiste extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5600539151194001701L;

	public PaquetespfsNoExiste() {
		super("ID de paquete no existe en repositorio");
	}
	
	public PaquetespfsNoExiste(String mensaje) {
		super(mensaje);
	}
}
