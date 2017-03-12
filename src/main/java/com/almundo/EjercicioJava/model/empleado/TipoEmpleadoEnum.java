package com.almundo.EjercicioJava.model.empleado;

/**
 * Cargos posibles para cada empleado.
 * @author Mar
 *
 */
public enum TipoEmpleadoEnum {
	OPERADOR(0), SUPERVISOR(1), DIRECTOR(2);

	private int value;

	private TipoEmpleadoEnum(int v) {
		this.value = v;
	}

	public int getValue() {
		return value;
	};
}
