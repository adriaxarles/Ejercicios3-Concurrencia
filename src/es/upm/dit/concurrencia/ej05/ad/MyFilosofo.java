package es.upm.dit.concurrencia.ej05.ad;

import java.util.concurrent.ThreadLocalRandom;

public class MyFilosofo extends Thread {
	private final Mesa mesa;
	private final int posicion;

	public MyFilosofo(Mesa mesa, String nombre, int posicion) {
		super(nombre);
		this.mesa = mesa;
		this.posicion = posicion;
	}

	public void run() {
		try {
			while (true) {
				mesa.cogerDosTenedores(posicion);
				System.out.println(Thread.currentThread().getName() + " Está comiendo");
				Thread.sleep(500);
				mesa.dejarTenedores(posicion);
				Thread.sleep(ThreadLocalRandom.current().nextInt(500));
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
