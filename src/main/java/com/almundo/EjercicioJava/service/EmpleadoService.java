package com.almundo.EjercicioJava.service;

import java.util.Queue;

import com.almundo.EjercicioJava.exceptions.EmpleadosNoDisponiblesException;
import com.almundo.EjercicioJava.model.empleado.Director;
import com.almundo.EjercicioJava.model.empleado.Empleado;
import com.almundo.EjercicioJava.model.empleado.Operador;
import com.almundo.EjercicioJava.model.empleado.Supervisor;

/**
 * Clase utilizada con el objetivo de manejar la disponibilidad de los empleados para atender llamadas.
 * @author Mar
 *
 */
public class EmpleadoService {

	private static EmpleadoService instance;

	private Queue<Operador> operadoresDisponibles;
	private Queue<Supervisor> supervisoresDisponibles;
	private Queue<Director> directoresDisponibles;

	public static EmpleadoService getInstance() {
		if (instance == null)
			instance = new EmpleadoService();
		return instance;
	}

	/**
	 * Obtiene un empleado disponible para atender una llamada. En primer instancia busca un operador, luego supervisor y finalmente un director.
	 * @return Empleado
	 * @throws EmpleadosNoDisponiblesException
	 */
	public synchronized Empleado getEmpleadoDisponible() throws EmpleadosNoDisponiblesException {

		if (!getOperadoresDisponibles().isEmpty()) {
			return getOperadoresDisponibles().poll();
		}

		if (!getSupervisoresDisponibles().isEmpty()) {
			return getSupervisoresDisponibles().poll();
		}

		if (!getDirectoresDisponibles().isEmpty()) {
			return getDirectoresDisponibles().poll();
		}

		throw new EmpleadosNoDisponiblesException();
	}

	/**
	 * Regresa un empleado al listado de disponibles para atender llamados.
	 * @param empleado
	 */
	public synchronized void liberarEmpleado(Empleado empleado) {
		switch (empleado.getTipo()) {
		case OPERADOR:
			getOperadoresDisponibles().offer((Operador) empleado);
			break;
		case SUPERVISOR:
			getSupervisoresDisponibles().offer((Supervisor) empleado);
			break;
		case DIRECTOR:
			getDirectoresDisponibles().offer((Director) empleado);
			break;
		}
	}

	public synchronized Queue<Operador> getOperadoresDisponibles() {
		return operadoresDisponibles;
	}

	public synchronized void setOperadoresDisponibles(Queue<Operador> operadoresDisponibles) {
		this.operadoresDisponibles = operadoresDisponibles;
	}

	public synchronized Queue<Supervisor> getSupervisoresDisponibles() {
		return supervisoresDisponibles;
	}

	public synchronized void setSupervisoresDisponibles(Queue<Supervisor> supervisoresDisponibles) {
		this.supervisoresDisponibles = supervisoresDisponibles;
	}

	public synchronized Queue<Director> getDirectoresDisponibles() {
		return directoresDisponibles;
	}

	public synchronized void setDirectoresDisponibles(Queue<Director> directoresDisponibles) {
		this.directoresDisponibles = directoresDisponibles;
	}
}
