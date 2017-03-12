package com.almundo.EjercicioJava.model.empleado;

public class Operador extends Empleado {

	public Operador(String nombre) {
		super(nombre);
		this.tipo = TipoEmpleadoEnum.OPERADOR;
	}

	public Operador(String nombre, String apellido, String dni) {
		super(nombre, apellido, dni);
		this.tipo = TipoEmpleadoEnum.OPERADOR;
	}

}
