package com.almundo.EjercicioJava;

import org.junit.Test;

import com.almundo.EjercicioJava.exceptions.EmpleadosNoDisponiblesException;
import com.almundo.EjercicioJava.model.Llamada;
import com.almundo.EjercicioJava.model.empleado.TipoEmpleadoEnum;

/**
 * Test unitario donde lleguen 10 llamadas.
 */
public class DispatcherTestConcurrencia extends AbastractBaseTest {

	@Test
	public void testLlamadosConcurrentes() throws EmpleadosNoDisponiblesException, InterruptedException {

		esperarFinLlamadasActivas();

		// ---------------------------------------//
		// LLEGADA DE LOS 10 LLAMADOS CONCURRENTES//
		// ---------------------------------------//

		for (int i = 0; i < Dispatcher.CANTIDAD_LLAMADOS_CONCURRENTES; i++) {
			// System.out.println("Llamada nro de telefono " + i);
			Llamada llamada = new Llamada(i);
			Dispatcher.getInstance().dispatchCall(llamada);
		}

		// Espero a que terminen los llamados
		esperarFinLlamadasActivas();

		System.out.println("DISPATCH LOG");
		System.out.println("------------");

		for (Llamada llamada : Dispatcher.getInstance().getDispatchLog()) {
			System.out.println("El empleado " + llamada.getEmpleado().getNombre()
					+ " está atendiendo al cliente con número de telefono " + llamada.getNumeroTelefono());
		}

		//for (int i = 0; i < Dispatcher.CANTIDAD_LLAMADOS_CONCURRENTES; i++) {
		int index = 0;
		for(Llamada llamada : Dispatcher.getInstance().getDispatchLog()){
			//Llamada llamada = Dispatcher.getInstance().getDispatchLog().get(i);

			// Si está dentro de los 6 primeros llamados procesados, deberían
			// ser atendidos por operadores
			if (index < 6) {
				assertEquals(TipoEmpleadoEnum.OPERADOR, llamada.getEmpleado().getTipo());
				assertEquals(false, llamada.tuvoEspera());
			} else if (index < 8) {
				assertEquals(TipoEmpleadoEnum.SUPERVISOR, llamada.getEmpleado().getTipo());
				assertEquals(false, llamada.tuvoEspera());
			} else if (index < 9) {
				assertEquals(TipoEmpleadoEnum.DIRECTOR, llamada.getEmpleado().getTipo());
				assertEquals(false, llamada.tuvoEspera());
			} else {
				assertTrue(llamada.tuvoEspera());
			}
			index++;
		}
	}
}
