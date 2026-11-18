package es.upm.dit.concurrencia.ej08.ad;

import java.util.concurrent.ThreadLocalRandom;

public class Mesa {
	private int numPersonas;
	private Ingrediente ingredienteEnMesa;
	private Ingrediente ingredienteAPrestar;

	public Mesa(int numPersonas) {
		this.numPersonas = numPersonas;
		ingredienteEnMesa = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];
		ingredienteAPrestar = null;
	}

	public synchronized Ingrediente cogerIngrediente(Ingrediente ingrediente) throws InterruptedException {
		while (ingredienteEnMesa == null || ingredienteEnMesa == ingrediente || ingredienteAPrestar != null) {
			if (ingredienteAPrestar == ingrediente) {
				debug("Presto el ingrediente" + ingrediente);
				ingredienteAPrestar = null;
				notifyAll();
			}
			debug("Estoy esperando al segundo ingrediente");
			wait();
		}
		debug("He cogido " + ingredienteEnMesa);
		Ingrediente i = ingredienteEnMesa;
		ingredienteEnMesa = null;

		return i;
	}

	public synchronized void pedirUltimoIngrediente(Ingrediente ingrediente) throws InterruptedException {
		ingredienteAPrestar = ingrediente;
		notifyAll();
		while (ingredienteAPrestar != null) {
			debug("Estoy esperando al tercer ingrediente");
			wait();
		}
		debug("tengo todos los ingredientes");
	}

	public synchronized void reposicionIngredientes() throws InterruptedException {

		ingredienteEnMesa = Ingrediente.values()[ThreadLocalRandom.current().nextInt(0, 3)];

		debug("El estanquero ha repuesto: " + ingredienteEnMesa);
		notifyAll();
	}

	private void debug(String str) {
		String nombre = Thread.currentThread().getName();
		System.out.println(nombre + " " + str);
		System.out.println(
				">>>>> ingredienteEnMesa=" + ingredienteEnMesa + " :ingredienteAPrestar: " + ingredienteAPrestar);
	}

}
