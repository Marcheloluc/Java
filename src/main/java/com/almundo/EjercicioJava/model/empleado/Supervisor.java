package com.almundo.EjercicioJava.model.empleado;

public class Supervisor extends Empleado {

	public Supervisor(String nombre) {
		super(nombre);
		this.tipo = TipoEmpleadoEnum.SUPERVISOR;
	}

	public Supervisor(String nombre, String apellido, String dni) {
		super(nombre, apellido, dni);
		this.tipo = TipoEmpleadoEnum.SUPERVISOR;
	}

}
