package es.upm.dit.concurrencia.ej03.ad;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ejercicio 1: Main
 */
public class MyMain {
	// Configuración del número de hebras
	private static final int NUM_LECTORAS = 300;
	private static final int RATIO_LECTORAS_ESCRITORAS = 10;

	public static void main(String[] args) {
		// Recurso compartido (monitor)
		RecursoCompartido recurso = new RecursoCompartido();
		Prioridad[] prioridades = Prioridad.values();

		System.out.println("Iniciando: " + NUM_LECTORAS + " lectoras y " + NUM_LECTORAS / RATIO_LECTORAS_ESCRITORAS
				+ " escritoras.");
		try {
			// Crear y arrancar hebras
			for (int i = 0; i < NUM_LECTORAS; i++) {
				Prioridad pL = prioridades[ThreadLocalRandom.current().nextInt(3)];
				Thread t = new HebraLectora(recurso, "L" + i, pL);
				t.start();

				// 1 escritora por cada N lectoras
				if (i % RATIO_LECTORAS_ESCRITORAS == 0) {
					Prioridad pE = prioridades[ThreadLocalRandom.current().nextInt(3)];
					Thread t2 = new HebraEscritora(recurso, "E" + i, pE);
					t2.start();
				}
				// Esperar hasta 500 ms entre creación de hebras.
				Thread.sleep(ThreadLocalRandom.current().nextInt(500));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}