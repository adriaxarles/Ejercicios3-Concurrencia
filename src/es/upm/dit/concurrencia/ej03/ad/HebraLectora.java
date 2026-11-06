package es.upm.dit.concurrencia.ej03.ad;

/**
 * Ejercicio 1: Hebra lectora
 */
public class HebraLectora extends Thread {
	public final RecursoCompartido recurso;
	public final Prioridad p;

	public HebraLectora(RecursoCompartido recurso, String nombre, Prioridad p) {
		super(nombre);
		this.recurso = recurso;
		this.p = p;
	}

	@Override
	public void run() {
		try {
			recurso.leer(getName() + ":" + p, p);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}