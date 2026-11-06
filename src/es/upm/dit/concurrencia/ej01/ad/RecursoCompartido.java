package es.upm.dit.concurrencia.ej01.ad;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ejercicio 1: Monitor
 *
 * Gestiona el acceso concurrente a un recurso (un Integer). El recurso se
 * considera no inicializado si es 'null'.
 */
public class RecursoCompartido {

	// Recurso compartido
	private Integer entero = null;

	private int numLectores = 0;
	private boolean escribiendo = false;

	/**
	 * Lectura.
	 */
	public void leer(String nombreLector) throws InterruptedException {
		// 1. Adquirir permiso de lectura
		accesoLectura(nombreLector);
		try {
			// 2. Acción de lectura (Fuera de sincronización)
			System.out.println("-- " + nombreLector + " está leyendo el número " + entero);
			// Espera hasta 500 ms
			Thread.sleep(ThreadLocalRandom.current().nextInt(500));
		} finally {
			// 3. Liberar permiso de lectura
			terminaLectura(nombreLector);
		}
	}

	private synchronized void accesoLectura(String nombreLector) throws InterruptedException {
		// Si el entero es null debe esperar
		while (entero == null || escribiendo) {
			System.out.println("-- " + nombreLector + " esperando para leer");
			wait();
		}
		numLectores++;
	}

	private synchronized void terminaLectura(String nombreLector) throws InterruptedException {
		System.out.println("-- " + nombreLector + " ha terminido de leer");
		numLectores--;
		notifyAll();
	}

	/**
	 * Escritura.
	 */
	public void escribir(String nombreEscritor) throws InterruptedException {
		// 1. Adquirir permiso de escritura
		accesoEscritura(nombreEscritor);
		try {
			// 2. Acción de escritura
			// El entero se genera aleatoriamente
			Integer anterior = entero;
			entero = ThreadLocalRandom.current().nextInt(10_001);
			System.out.println(
					">> " + nombreEscritor + " está cambiando el número " + anterior + " por el número " + entero);
			// Espera hasta 500 ms
			Thread.sleep(ThreadLocalRandom.current().nextInt(500));
		} finally {
			// 3. Liberar permiso de escritura
			terminaEscritura(nombreEscritor);
		}
	}

	private synchronized void accesoEscritura(String nombreEscritor) throws InterruptedException {
		while (escribiendo || numLectores != 0) {
			System.out.println(">> " + nombreEscritor + " esperando para escribir");
			wait();
		}
		escribiendo = true;
	}

	private synchronized void terminaEscritura(String nombreEscritor) throws InterruptedException {
		System.out.println(">> " + nombreEscritor + " ha terminido de escribir");
		escribiendo = false;
		notifyAll();
	}
}