package es.upm.dit.concurrencia.ej05.ad;

import java.util.concurrent.ThreadLocalRandom;

public class MyMain {

	// Configuración del número de hebras
	private static final int NUMERO_PERSONAS = 5;

	public static void main(String[] args) {
		Mesa mesa = new Mesa(NUMERO_PERSONAS);
		try {
			// Crear y arrancar hebras
			for (int i = 0; i < NUMERO_PERSONAS; i++) {
				// Productora
				Thread t = new MyFilosofo(mesa, "Filosofos-" + i, i);
				t.start();
				Thread.sleep(ThreadLocalRandom.current().nextInt(200));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
