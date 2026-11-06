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
			// Intenta escribir en el recurso
			int dato = buffer.leerDato();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
