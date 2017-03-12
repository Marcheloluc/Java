package com.almundo.EjercicioJava;

import org.junit.Test;

import com.almundo.EjercicioJava.model.Llamada;
import com.almundo.EjercicioJava.model.empleado.TipoEmpleadoEnum;

/**
 * Test para verificar que si llega una cantidad de llamados manejable por
 * operadores sólo sea atendido por ellos.
 */
public class DispatcherTestSoloOperador extends AbastractBaseTest {

	@Test
	public void testSoloOperadores() throws InterruptedException {

		esperarFinLlamadasActivas();

		for (int i = 0; i < 12; i++) {
			Llamada llamada = new Llamada(i);
			if (i == 6) {
				Thread.sleep(Dispatcher.MAXIMA_DURACION_LLAMADAS);
			}
			Dispatcher.getInstance().dispatchCall(llamada);
		}

		Thread.sleep(Dispatcher.MAXIMA_DURACION_LLAMADAS);

		for (Llamada llamada : Dispatcher.getInstance().getDispatchLog()) {
			assertEquals(TipoEmpleadoEnum.OPERADOR, llamada.getEmpleado().getTipo());
		}
	}

}
