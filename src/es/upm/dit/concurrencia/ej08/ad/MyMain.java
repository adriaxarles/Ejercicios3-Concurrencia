package es.upm.dit.concurrencia.ej08.ad;

public class MyMain {

	// Configuración del número de hebras
	private static final int NUMERO_FUMADORES = 3;

	public static void main(String[] args) {
		Mesa mesa = new Mesa(NUMERO_FUMADORES);
		// Crear y arrancar hebras
		for (int i = 0; i < NUMERO_FUMADORES; i++) {
			// Productora
			Thread t = new MyFumador(mesa, "Fumador-" + Ingrediente.values()[i], Ingrediente.values()[i]);
			t.start();
		}
	}
}
