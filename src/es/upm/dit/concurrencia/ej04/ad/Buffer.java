package es.upm.dit.concurrencia.ej04.ad;

import java.util.Arrays;

public class Buffer {
	private int[] arr;
	private int primerDato = 0;
	private int ultimoDato = 0;
	private int numDatos = 0;

	public Buffer(int tamanio) {
		this.arr = new int[tamanio];
	}

	public synchronized void introducirDato(int dato) throws InterruptedException {
		while (numDatos >= arr.length) {
			debug("buffer lleno. Esperando...");
			wait();
		}
		arr[ultimoDato] = dato;
		ultimoDato = (ultimoDato + 1) % arr.length;
		numDatos++;
		notifyAll();
		debug("he introducido el dato =" + dato);
	}

	public synchronized int leerDato() throws InterruptedException {
		while (numDatos == 0) {
			debug("buffer vacío. Esperando...");
			wait();
		}
		int dato = arr[primerDato];
		arr[primerDato] = -1;
		primerDato = (primerDato + 1) % arr.length;
		numDatos--;
		notifyAll();
		debug("leído el dato=" + dato);
		return dato;
	}

	private void debug(String str) {
		String nombre = Thread.currentThread().getName();
		System.out.println(nombre + " " + str + " :numDatos=" + numDatos + " :arr=" + Arrays.toString(arr) + " :pD="
				+ primerDato + " :uD=" + ultimoDato);
	}

}
