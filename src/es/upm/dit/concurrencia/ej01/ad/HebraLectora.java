package es.upm.dit.concurrencia.ej01.ad;

/**
 * Ejercicio 1: Hebra lectora
 */
public class HebraLectora extends Thread {
	public final RecursoCompartido recurso;

	public HebraLectora(RecursoCompartido recurso, String nombre) {
		super(nombre);
		this.recurso = recurso;
	}

	@Override
	public void run() {
		try {
			recurso.leer(getName());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}