package es.upm.dit.concurrencia.ej03.ad;

import java.util.Arrays;
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

	private boolean escribiendo = false;
	private int numLectores = 0;

	private int[] numLectoresEnEspera;
	private int[] numEscritoresEnEspera;

	public RecursoCompartido() {
		this.numLectoresEnEspera = new int[Prioridad.values().length];
		this.numEscritoresEnEspera = new int[Prioridad.values().length];
	}

	/**
	 * Lectura.
	 */
	public void leer(String nombreLector, Prioridad p) throws InterruptedException {
		// 1. Adquirir permiso de lectura
		accesoLectura(nombreLector, p);
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

	private synchronized void accesoLectura(String nombreLector, Prioridad p) throws InterruptedException {
		numLectoresEnEspera[p.ordinal()]++;

		// esperar si
		// Si hay alguien escribiendo -
		// Si no hay numero -
		// Si hay algun escritor de mayor prioridad -
		while (entero == null || escribiendo || hayEscritorMayorPrioridad(p)) {
			System.out.println("-- " + nombreLector + " esperando para leer");
			wait();
		}
		numLectores++;
		numLectoresEnEspera[p.ordinal()]--;
	}

	private boolean hayEscritorMayorPrioridad(Prioridad p) {
//		System.out.println("-- hayEscritorMayorPrioridad prioridad =  " + p);
		int acumulador = 0;
		for (int i = p.ordinal() + 1; i < Prioridad.values().length; i++) {
			acumulador += numEscritoresEnEspera[i];
		}

		return acumulador > 0;
	}

	private synchronized void terminaLectura(String nombreLector) throws InterruptedException {
		System.out.println("-- " + nombreLector + " ha terminido de leer");
		numLectores--;
		notifyAll();
	}

	/**
	 * Escritura.
	 */
	public void escribir(String nombreEscritor, Prioridad p) throws InterruptedException {
		// 1. Adquirir permiso de escritura
		accesoEscritura(nombreEscritor, p);
		try {
			// 2. Acción de escritura
			// El entero se genera aleatoriamente
			Integer anterior = entero;
			entero = ThreadLocalRandom.current().nextInt(10_001);
			System.out.println(
					">> " + nombreEscritor + " está cambiando el número " + anterior + " por el número " + entero);
			System.out
					.println(">> " + nombreEscritor + ": pendientesLectores = " + Arrays.toString(numLectoresEnEspera));
			System.out.println(
					">> " + nombreEscritor + ": pendientesEscritores = " + Arrays.toString(numEscritoresEnEspera));
			// Espera hasta 500 ms
			Thread.sleep(ThreadLocalRandom.current().nextInt(500));
		} finally {
			// 3. Liberar permiso de escritura
			terminaEscritura(nombreEscritor);
		}
	}

	private synchronized void accesoEscritura(String nombreEscritor, Prioridad p) throws InterruptedException {
		if (entero == null && !hayEscritorMayorPrioridad(p) && !escribiendo && numLectores == 0) {
			// para evitar el bloqueo del caso inicial con entero == null
			escribiendo = true;
		} else {
			numEscritoresEnEspera[p.ordinal()]++;

			// esperar si
			// si hay alguien escribiendo o leyendo,
			// si hay pendiente algun escritor de mayor prioridad -
			// si hay pendiente algun lector de >= misma prioridad -
			while (escribiendo || numLectores != 0 || hayEscritorMayorPrioridad(p)
					|| hayLectorConAlMenosMismaPrioridad(p)) {
				System.out.println(">> " + nombreEscritor + " esperando para escribir");
				wait();
			}
			escribiendo = true;
			numEscritoresEnEspera[p.ordinal()]--;
		}
	}

	private boolean hayLectorConAlMenosMismaPrioridad(Prioridad p) {

//		System.out.println("-- hayLectorConAlMenosMismaPrioridad prioridad =  " + p);
		int acumulador = 0;
		for (int i = p.ordinal(); i < Prioridad.values().length; i++) {
			acumulador += numLectoresEnEspera[i];
		}

		return acumulador > 0;
	}

	private synchronized void terminaEscritura(String nombreEscritor) throws InterruptedException {
		System.out.println(">> " + nombreEscritor + " ha terminido de escribir");
		escribiendo = false;
		notifyAll();
	}
}