package es.upm.dit.concurrencia.ej06.ad;

import java.util.concurrent.ThreadLocalRandom;

public class MainYo {

	private static final int NUMERO_CANIBALES = 6;
	private static final int CAPACIDAD_TOTAL = 4;

	public static void main(String[] args) {
		MyMarmita marmita = new MyMarmita(NUMERO_CANIBALES, CAPACIDAD_TOTAL);
		try {
			Thread c = new MyCocinero(marmita, "Cocinero");
			c.start();

			for (int i = 0; i < NUMERO_CANIBALES; i++) {
				Thread t = new MyCanibal(marmita, "Caníbal-" + i);
				t.start();
				Thread.sleep(ThreadLocalRandom.current().nextInt(500));
			}

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
