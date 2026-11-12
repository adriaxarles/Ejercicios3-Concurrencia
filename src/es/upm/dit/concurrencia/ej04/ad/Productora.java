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
			for (int i = 0; i < 5; i++) {
				buffer.introducirDato(ThreadLocalRandom.current().nextInt(10_001));
				Thread.sleep(ThreadLocalRandom.current().nextInt(300));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
