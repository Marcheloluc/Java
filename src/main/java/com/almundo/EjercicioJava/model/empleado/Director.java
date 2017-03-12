package com.almundo.EjercicioJava.model.empleado;

public class Director extends Empleado {

	public Director(String nombre) {
		super(nombre);
		this.tipo = TipoEmpleadoEnum.DIRECTOR;
	}

	public Director(String nombre, String apellido, String dni) {
		super(nombre, apellido, dni);
		this.tipo = TipoEmpleadoEnum.DIRECTOR;
	}

}
