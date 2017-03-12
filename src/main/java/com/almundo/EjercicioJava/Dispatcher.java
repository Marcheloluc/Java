package com.almundo.EjercicioJava;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.almundo.EjercicioJava.exceptions.CantidadMaximaLlamadasConcurrentesAlcanzadaException;
import com.almundo.EjercicioJava.exceptions.EmpleadosNoDisponiblesException;
import com.almundo.EjercicioJava.model.Llamada;
import com.almundo.EjercicioJava.model.empleado.Empleado;
import com.almundo.EjercicioJava.service.EmpleadoService;

/**
 * Clase encargada de manejar las llamadas y la asignación a los empleados
 * disponibles
 * 
 * @author Mar
 *
 */
public class Dispatcher {

	public static final int CANTIDAD_LLAMADOS_CONCURRENTES = 10; // cantidad de
																	// hilos
	public static final int MAXIMA_DURACION_LLAMADAS = 10000; // milisegundos

	private static Dispatcher instance;
	private ExecutorService threadPool;
	private Queue<Llamada> colaLlamadas;

	private Queue<Llamada> dispatchLog;

	private Dispatcher() {
		// Setea un maximo de 10 llamados concurrentes.
		threadPool = Executors.newFixedThreadPool(CANTIDAD_LLAMADOS_CONCURRENTES);

		// Arma una cola de tipo ConcurrentLinkedQueue para que se encuentre
		// sincronizada para todos los hilos.
		colaLlamadas = new ConcurrentLinkedQueue<Llamada>();

		// Instancia lista para registrar los llamados redirigidos por el
		// Dispatcher mediante dispatchCall.
		// dispatchLog = Collections.synchronizedList(new ArrayList<Llamada>());
		dispatchLog = new ConcurrentLinkedQueue<Llamada>();
	}

	/**
	 * Singleton
	 */
	public static Dispatcher getInstance() {
		if (instance == null)
			instance = new Dispatcher();
		return instance;
	}

	/**
	 * Asigna llamadas a los empleados disponibles. Puede procesar hasta 10
	 * llamadas al mismo tiempo
	 * 
	 * @throws InterruptedException
	 * @throws EmpleadosNoDisponiblesException
	 */
	public void dispatchCall(final Llamada llamada) throws InterruptedException {

		try {
			Runnable r = new Runnable() {
				public void run() {
					Empleado empleado = null;
					try {
						while (empleado == null) {
							empleado = EmpleadoService.getInstance().getEmpleadoDisponible();
						}
						procesarLlamada(llamada, empleado);
					} catch (EmpleadosNoDisponiblesException e) {
						respuestaAutomaticaEmpleadosNoDiponibles(llamada);
						ponerEnEspera(llamada);
					}
				}

			};

			// En el caso de encontrarse activamente la cantidad limite de
			// llamadas concurrentes
			// Se lanza una excepción que luego termina en informarle al cliente
			// y luego ponerlo en espera.
			if (((ThreadPoolExecutor) threadPool).getActiveCount() == CANTIDAD_LLAMADOS_CONCURRENTES) {
				throw new CantidadMaximaLlamadasConcurrentesAlcanzadaException();

			}
			threadPool.execute(r);
		} catch (CantidadMaximaLlamadasConcurrentesAlcanzadaException e) {
			respuestaAutomaticaEmpleadosNoDiponibles(llamada);
			ponerEnEspera(llamada);
		}
	}

	/**
	 * Mensaje a mostrar en el caso de no poder darle tratamiento directo a la
	 * llamada.
	 * 
	 * @param llamada
	 */
	private void respuestaAutomaticaEmpleadosNoDiponibles(final Llamada llamada) {
		System.out.println("Respuesta automática a la llamada entrante del nro telefónico "
				+ llamada.getNumeroTelefono() + ":"
				+ "\nTodas nuestras lineas están ocupadas, por favor aguarde unos momentos y será atendido. Muchas Gracias");
	}

	/**
	 * Asigna empleado a llamada y éste procede a atenderla.
	 * 
	 * @param llamada
	 * @param empleado
	 */
	private void procesarLlamada(Llamada llamada, Empleado empleado) {
		llamada.setEmpleado(empleado);
		Dispatcher.getInstance().getDispatchLog().add(llamada);
		empleado.atenderLlamada(llamada);
		if (!asignarLlamadaPendiente(empleado)) {
			EmpleadoService.getInstance().liberarEmpleado(empleado);
		}
	}

	/**
	 * Luego de que un Empleado se libera chequea si hay llamadas pendientes y
	 * en el caso de encontrar alguna la procesa.
	 * 
	 * @param empleado
	 * @return
	 */
	private boolean asignarLlamadaPendiente(Empleado empleado) {
		if (!colaLlamadas.isEmpty()) {
			Llamada llamada = colaLlamadas.poll();
			if (llamada != null)
				procesarLlamada(llamada, empleado);

		}
		return false;
	}

	private void ponerEnEspera(final Llamada llamada) {
		llamada.setEspera(true);
		colaLlamadas.offer(llamada);
	}

	public int getLlamadasActivas() {
		return ((ThreadPoolExecutor) threadPool).getActiveCount();
	}

	public Queue<Llamada> getDispatchLog() {
		return dispatchLog;
	}

	public void setDispatchLog(Queue<Llamada> dispatchLog) {
		this.dispatchLog = dispatchLog;
	}

}
