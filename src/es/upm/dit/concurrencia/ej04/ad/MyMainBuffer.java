package es.upm.dit.concurrencia.ej04.ad;

import java.util.concurrent.ThreadLocalRandom;

import es.upm.dit.concurrencia.ej03.ad.HebraEscritora;
import es.upm.dit.concurrencia.ej03.ad.HebraLectora;
import es.upm.dit.concurrencia.ej03.ad.Prioridad;
import es.upm.dit.concurrencia.ej03.ad.RecursoCompartido;

public class MyMainBuffer {

	// Configuración del número de hebras
	private static final int TAMANIO = 10;
	private static final int NUMERO_HEBRAS = 25;
	private static final int PROB_PRODUCTORA = 30;

	public static void main(String[] args) {
		Buffer buffer = new Buffer(TAMANIO);

		try {
			// Crear y arrancar hebras
			for (int i = 0; i < NUMERO_HEBRAS; i++) {
				if (ThreadLocalRandom.current().nextInt(100) < PROB_PRODUCTORA) {
					// Productora
					Thread t = new Productora(buffer, "P" + i);
					t.start();

				} else {
					// Consumidora
					Thread t2 = new Consumidora(buffer, "C" + i);
					t2.start();
				}
			}
			// Esperar hasta 500 ms entre creación de hebras.
			Thread.sleep(ThreadLocalRandom.current().nextInt(500));

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
