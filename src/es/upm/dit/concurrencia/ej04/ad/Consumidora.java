package es.upm.dit.concurrencia.ej04.ad;

import java.util.concurrent.ThreadLocalRandom;

public class Consumidora extends Thread {
	private final Buffer buffer;

	public Consumidora(Buffer buffer, String nombre) {
		super(nombre);
		this.buffer = buffer;
	}

	public void run() {
		try {
			// Intenta leer en el recurso
			for (int i = 0; i < 7; i++) {
				int dato = buffer.leerDato();
				Thread.sleep(ThreadLocalRandom.current().nextInt(600));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
