package es.upm.dit.concurrencia.ej08.pp;

import java.util.concurrent.ThreadLocalRandom;

public class Mesa {
	Ingrediente elementoEnMesa;
	boolean mesaVacia;
	Ingrediente elementoPedido;
	boolean seNecesitaIngrediente = false;

	public Mesa(int numPersonas) {
		elementoEnMesa = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		elementoPedido = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		mesaVacia = false;
		seNecesitaIngrediente = false;
	}

	public synchronized void cogerIngrediente(Ingrediente miIngrediente) throws InterruptedException {
		while (mesaVacia || miIngrediente == elementoEnMesa || seNecesitaIngrediente) {
			if (seNecesitaIngrediente && elementoPedido == miIngrediente) {
				debug("Le doy el ingrediente " + miIngrediente);
				seNecesitaIngrediente = false;
				notifyAll();
			}

			debug("Estoy esperando");
			wait();
		}
		mesaVacia = true;
		debug("He cogido el " + elementoEnMesa);
		// obtener el tercero
		Ingrediente tercero = obtTercero(miIngrediente, elementoEnMesa);
		seNecesitaIngrediente = true;
		elementoPedido = tercero;
		notifyAll();
		while (seNecesitaIngrediente) {
			debug("Estoy esperando a que me presten el " + tercero);
			wait();
		}
		debug("Ya tengo los 3 ingredientes");
	}

	public synchronized void reposicionIngredientes() throws InterruptedException {

		elementoEnMesa = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		mesaVacia = false;

		debug("El estanquero ha repuesto la mesa con el ingrediente " + elementoEnMesa);
		notifyAll();
	}

	private Ingrediente obtTercero(Ingrediente i1, Ingrediente i2) {
		for (Ingrediente i : Ingrediente.values()) {
			if (i != i1 && i != i2)
				return i;
		}
		debug("ERROR!!! fallo al obtener el 3er ingrediente. " + i1 + "," + i2);
		return null;
	}

	private void debug(String str) {
		String nombre = Thread.currentThread().getName();
		System.out.println(nombre + " " + str);
		// System.out.println(">>> elementoEnMesa=" + elementoEnMesa + " :mesaVacia=" +
		// mesaVacia + " :elementoPedido="
		// + elementoPedido + " :seNecesitaIngrediente=" + seNecesitaIngrediente);
	}

}
