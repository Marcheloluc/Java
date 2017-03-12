package com.almundo.EjercicioJava;

import org.junit.Test;

import com.almundo.EjercicioJava.model.Llamada;

/**
 * Test para comprobar funcionamiento cuando llegan mas de 10 llamadas.
 */
public class DispatcherTestCantidadMayorLimite extends AbastractBaseTest {

	@Test
	public void testCantidadLlamadosMayorLimiteConcurrencia() throws InterruptedException {
		esperarFinLlamadasActivas();

		for (int i = 0; i < 12; i++) {
			System.out.println("Llamada nro de telefono " + i);
			Llamada llamada = new Llamada(i);
			Dispatcher.getInstance().dispatchCall(llamada);
		}
		esperarFinLlamadasActivas();

		//Los llamados adicionales los pone en espera
		/*for (int i = 10; i < 12; i++) {
			Llamada llamada = Dispatcher.getInstance().getDispatchLog().get(i);
			assertTrue(llamada.tuvoEspera());
		}*/
		
		int index = 0;
		for(Llamada llamada : Dispatcher.getInstance().getDispatchLog()){
			if(index >= 10){
				assertTrue(llamada.tuvoEspera());
			}
			index++;
		}
	}

}
