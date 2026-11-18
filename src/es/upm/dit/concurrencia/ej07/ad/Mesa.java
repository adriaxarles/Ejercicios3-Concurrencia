package es.upm.dit.concurrencia.ej07.ad;

import java.util.concurrent.ThreadLocalRandom;

public class Mesa {
	private int numPersonas;
	Ingrediente elementoFalta;
	boolean mesaVacia;

	public Mesa(int numPersonas) {
		this.numPersonas = numPersonas;
		elementoFalta = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		mesaVacia = false;
	}

	public synchronized void cogerIngredientes(Ingrediente ingrediente) throws InterruptedException {
		while (mesaVacia || miIngredienteEnMesa(ingrediente)) {
			debug("Estoy esperando");
			wait();
		}
		mesaVacia = true;
		debug("Ya tengo todos los ingredientes que me faltaban");
	}

	public synchronized void reposicionIngredientes() throws InterruptedException {

		elementoFalta = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		mesaVacia = false;

		debug("El estanquero ha repuesto 2 ingredientes");
		notifyAll();
	}

	private boolean miIngredienteEnMesa(Ingrediente ingrediente) {
		return !mesaVacia && ingrediente != elementoFalta;
	}

	private void debug(String str) {
		String nombre = Thread.currentThread().getName();
		System.out.println(nombre + " " + str + " :numPersonas=" + numPersonas + " :elementoFalta=" + elementoFalta
				+ " :mesaVacia=" + mesaVacia);
	}

}
