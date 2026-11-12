package es.upm.dit.concurrencia.ej05.ad;

import java.util.Arrays;

public class Mesa {
	private int numPersonas;
	private boolean[] tenedores; // tenedores encima de la mesa

	public Mesa(int numPersonas) {
		this.numPersonas = numPersonas;
		tenedores = new boolean[numPersonas];
		for (int i = 0; i < tenedores.length; i++) {
			this.tenedores[i] = true;
		}
	}

	public synchronized void cogerDosTenedores(int posicion) throws InterruptedException {
		while (!(hayTenedor(posicion) && hayTenedor(posicion + 1))) {
			debug("Estoy esperando");
			wait();
		}
		tenedores[posicion % numPersonas] = false;
		tenedores[(posicion + 1) % numPersonas] = false;
		debug("Ya tengo los tenedores");
	}

	public synchronized void dejarTenedores(int posicion) {
		tenedores[posicion % numPersonas] = true;
		tenedores[(posicion + 1) % numPersonas] = true;
		debug("Ya he dejado los tenedores");
		notifyAll();
	}

	private boolean hayTenedor(int posicion) {
		return tenedores[posicion % numPersonas];
	}

	private void debug(String str) {
		String nombre = Thread.currentThread().getName();
		System.out.println(
				nombre + " " + str + " :numPersonas=" + numPersonas + " :tenedores=" + Arrays.toString(tenedores));
	}

}
