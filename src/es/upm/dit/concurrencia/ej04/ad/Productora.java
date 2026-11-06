package es.upm.dit.concurrencia.ej04.ad;

import java.util.concurrent.ThreadLocalRandom;

public class Productora extends Thread {
	private final Buffer buffer;

	public Productora(Buffer buffer, String nombre) {
		super(nombre);
		this.buffer = buffer;
	}

	public void run() {
		try {
			// Intenta escribir en el recurso
			buffer.introducirDato(ThreadLocalRandom.current().nextInt(10_001));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
