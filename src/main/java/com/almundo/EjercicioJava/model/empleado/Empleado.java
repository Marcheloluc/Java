package com.almundo.EjercicioJava.model.empleado;

import java.util.Random;

import com.almundo.EjercicioJava.model.Llamada;

/**
 * Empleado del Call Center
 * 
 * @author Mar
 *
 */
public abstract class Empleado {
	private String nombre;
	private String apellido;
	private String DNI;

	// Rol dentro del Call Center
	protected TipoEmpleadoEnum tipo;

	// Llamada que se encuentra procesando
	private Llamada llamadaActual;

	public Empleado() {
	};

	public Empleado(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Empleado(String nombre, String apellido, String dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		DNI = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public TipoEmpleadoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEmpleadoEnum tipo) {
		this.tipo = tipo;
	}

	public Llamada getLlamadaActual() {
		return llamadaActual;
	}

	/**
	 * Dar tratamiento a la llamada. Establece una duración aleatoria entre 5 y
	 * 10 segundos. Muestra por consola el nuúmero que se encuentra atendiendo.
	 * 
	 * @param llamada
	 */
	public void atenderLlamada(Llamada llamada) {
		this.llamadaActual = llamada;

		// Espero de 5 a 10 segundos
		Random r = new Random();
		int seconds = (r.nextInt(5) + 5) * 1000;
		try {
			String mensaje = "El empleado " + this.nombre + " está atendiendo al cliente con numero de telefono "
					+ this.llamadaActual.getNumeroTelefono();
			System.out.println(mensaje);
//			Dispatcher.getInstance().getDispatchLog().add(this.llamadaActual);
			Thread.sleep(seconds);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		this.terminarLlamada();
	}

	/**
	 * Libera al empleado de la llamada
	 */
	public void terminarLlamada() {
		if (this.llamadaActual != null) {
			llamadaActual.finalizar();
			this.llamadaActual = null;
		}
	}

	public boolean disponible() {
		return this.llamadaActual == null;
	}

}
