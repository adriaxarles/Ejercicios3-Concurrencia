package es.upm.dit.concurrencia.ej06.ad;

public class MyMarmita {
	private final int numCanibales;
	private int cantidadComida;
	private final int capacidadTotal;
	private boolean ocupadoCucharon; // es true si está ocupado
	private boolean cocineroAvisado;
	private Thread canibalQueHaAvisado;

	public MyMarmita(int numCanibales, int capacidadTotal) {
		this.numCanibales = numCanibales;
		this.capacidadTotal = capacidadTotal;
		this.cantidadComida = capacidadTotal;
		ocupadoCucharon = false;
		cocineroAvisado = false;
		canibalQueHaAvisado = null;
	}

	public synchronized void cogeCucharon() throws InterruptedException {
		// condiciones de espera
		// - no hay comida
		// - cucharon esta ocupado
		// - en caso de aviso, no soy yo el que ha avisado
		while (ocupadoCucharon || cantidadComida == 0 || siHayAvisoNoSoyYoElAvisador()) {
			if (cantidadComida == 0 && !cocineroAvisado) {
				cocineroAvisado = true;
				notifyAll();
				debug("he AVISADO al cocinero");
				canibalQueHaAvisado = Thread.currentThread();
			}
			debug("Estoy esperando a coger el cucharon");
			wait();
		}
		ocupadoCucharon = true;
		canibalQueHaAvisado = null;
		debug("he cogido el cucharon");
	}

	public synchronized void dejaCucharon() {
		cantidadComida--;
		ocupadoCucharon = false;
		notifyAll();
		debug("he dejado el cucharon");
	}

	public synchronized void listoCocinar() throws InterruptedException {
		while (!cocineroAvisado) {
			debug("Cocinero esperando");
			wait();
		}
	}

	public synchronized void avisaCanibal() {
		cocineroAvisado = false;
		cantidadComida = capacidadTotal;
		notifyAll();
		debug("la comida está lista!!!");
	}

	// si no hay avisador -> true
	// else devuelve si esta hebra es el avisador
	private boolean siHayAvisoNoSoyYoElAvisador() {
		if (canibalQueHaAvisado == null)
			return false;

		return canibalQueHaAvisado != Thread.currentThread();
	}

	private void debug(String str) {
		System.out.println(">>>> " + Thread.currentThread().getName() + " " + str);
		System.out.println("      cantidadComida= " + cantidadComida + " << ocupadoCucharon: " + ocupadoCucharon
				+ "<< cocineroAvisado: " + cocineroAvisado);
	}
}
