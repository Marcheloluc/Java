package com.almundo.EjercicioJava;

import org.junit.Test;

import com.almundo.EjercicioJava.model.Llamada;

/**
 * Test intento de varios llamados en simultaneo.
 */
public class DispatcherTestStress extends AbastractBaseTest {

	@Test
	public void testStress() throws InterruptedException {

		esperarFinLlamadasActivas();

		for (int i = 0; i < 20; i++) {
			Llamada llamada = new Llamada(i);
			Dispatcher.getInstance().dispatchCall(llamada);
		}

		esperarFinLlamadasActivas();

		assertEquals(20, Dispatcher.getInstance().getDispatchLog().size());
	}
}
