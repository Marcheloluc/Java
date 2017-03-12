package com.almundo.EjercicioJava.model;

import com.almundo.EjercicioJava.model.empleado.Empleado;

/**
 * Representa la llamada de un usuario que debe ser atendida por un empleado,
 * sea operador, supervisor o en última instancia director
 * 
 * @author Mar
 *
 */
public class Llamada {

	/* Persona que llama al callcenter */
	private long numeroTelefono;

	/* Operador/Supervisor/Director que atiende la llamada */
	private Empleado empleado;

	/*
	 * En el caso de no ser atendido en el momento de llegar, pasa a la cola de
	 * llamadas y se indica que se encuentra en espera.
	 */
	private boolean espera;

	public Llamada(long numeroTelefono) {
		super();
		this.numeroTelefono = numeroTelefono;
		this.espera = false;
	}

	public Llamada(long numeroTelefono, Empleado operador) {
		super();
		this.numeroTelefono = numeroTelefono;
		this.empleado = operador;
	}

	/**
	 * Finaliza la llamada, notifica que el Empleado ya se encuentra nuvamente disponible.
	 */
	public void finalizar() {
		System.out.println("Fin de la llamada del numero de telefono " + this.numeroTelefono + ". Empleado "
				+ this.empleado.getNombre() + " liberado");
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public long getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(long numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public boolean tuvoEspera() {
		return espera;
	}

	public void setEspera(boolean espera) {
		this.espera = espera;
	}

}
