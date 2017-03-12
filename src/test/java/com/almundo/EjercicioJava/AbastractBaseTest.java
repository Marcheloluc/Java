package com.almundo.EjercicioJava;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.junit.Before;

import com.almundo.EjercicioJava.model.empleado.Director;
import com.almundo.EjercicioJava.model.empleado.Operador;
import com.almundo.EjercicioJava.model.empleado.Supervisor;
import com.almundo.EjercicioJava.service.EmpleadoService;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public abstract class AbastractBaseTest extends TestCase {

	public final int CANT_OPERADORES = 6;
	public final int CANT_SUPERVISORES = 2;
	public final int CANT_DIRECTORES = 1;

	@Before
	public void setUp() {

		// Genera listado de Operadores.
		Queue<Operador> operadores = new ArrayBlockingQueue<Operador>(CANT_OPERADORES);
		for (int k = 0; k < CANT_OPERADORES; k++) {
			operadores.add(new Operador("Operador" + k));
		}
		EmpleadoService.getInstance().setOperadoresDisponibles(operadores);

		// Genero listado de Supervisores.
		Queue<Supervisor> supervisores = new ArrayBlockingQueue<Supervisor>(CANT_SUPERVISORES);
		for (int k = 0; k < CANT_SUPERVISORES; k++) {
			supervisores.add(new Supervisor("Supervisor" + k));
		}
		EmpleadoService.getInstance().setSupervisoresDisponibles(supervisores);

		// Genero listado de Directores.
		Queue<Director> directors = new ArrayBlockingQueue<Director>(CANT_DIRECTORES);
		for (int k = 0; k < CANT_DIRECTORES; k++) {
			directors.add(new Director("Director" + k));
		}
		EmpleadoService.getInstance().setDirectoresDisponibles(directors);

		Dispatcher.getInstance().getDispatchLog().clear();
		esperarFinLlamadasActivas();
	}
	
	public void esperarFinLlamadasActivas(){
		while (Dispatcher.getInstance().getLlamadasActivas() != 0) {}		
	}
}
