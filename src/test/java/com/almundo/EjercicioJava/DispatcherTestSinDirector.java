package com.almundo.EjercicioJava;

import org.junit.Test;

import com.almundo.EjercicioJava.model.Llamada;
import com.almundo.EjercicioJava.model.empleado.TipoEmpleadoEnum;

/**
 * Test para verificar que los llamados primero se atienden por Operadores y Supervisores.
 */
public class DispatcherTestSinDirector extends AbastractBaseTest {

	@Test
	public void testSinDirector() throws InterruptedException {

		esperarFinLlamadasActivas();

		for (int i = 0; i < 8; i++) {
			Llamada llamada = new Llamada(i);
			Dispatcher.getInstance().dispatchCall(llamada);
		}

		esperarFinLlamadasActivas();

		for (Llamada llamada : Dispatcher.getInstance().getDispatchLog()) {
			assertTrue(llamada.getEmpleado().getTipo() != TipoEmpleadoEnum.DIRECTOR);
		}
	}

}
