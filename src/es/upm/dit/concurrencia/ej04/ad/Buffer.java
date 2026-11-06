package es.upm.dit.concurrencia.ej04.ad;

public class Buffer {
	private int numDatos = 0;
	private int[] arr;

	public Buffer(int tamanio) {
		this.arr = new int[tamanio];
	}

	public synchronized void introducirDato(int dato) throws InterruptedException {
		String nombre = Thread.currentThread().getName();
		while (numDatos >= arr.length) {
			wait();
		}
		arr[numDatos] = dato;
		numDatos++;
		notifyAll();
	}

	public synchronized int leerDato() throws InterruptedException {
		String nombre = Thread.currentThread().getName();
		while (numDatos == 0) {
			wait();
		}
		int dato = arr[0];
		for (int i = 0; i < numDatos - 1; i++) {
			arr[i] = arr[i + 1];
		}
		numDatos--;
		notifyAll();
		return dato;
	}

}
