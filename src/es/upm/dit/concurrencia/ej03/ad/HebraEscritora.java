package es.upm.dit.concurrencia.ej03.ad;

/**
 * Ejercicio 1: Hebra escritora
 */
public class HebraEscritora extends Thread {
	private final RecursoCompartido recurso;
	public final Prioridad p;

	public HebraEscritora(RecursoCompartido recurso, String nombre, Prioridad p) {
		super(nombre);
		this.recurso = recurso;
		this.p = p;
	}

	@Override
	public void run() {
		try {
			// Intenta escribir en el recurso
			recurso.escribir(getName() + ":" + p, p);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}