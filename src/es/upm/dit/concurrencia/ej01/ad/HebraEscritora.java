package es.upm.dit.concurrencia.ej01.ad;

/**
 * Ejercicio 1: Hebra escritora
 */
public class HebraEscritora extends Thread {
	private final RecursoCompartido recurso;

	public HebraEscritora(RecursoCompartido recurso, String nombre) {
		super(nombre);
		this.recurso = recurso;
	}

	@Override
	public void run() {
		try {
			// Intenta escribir en el recurso
			recurso.escribir(getName());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}