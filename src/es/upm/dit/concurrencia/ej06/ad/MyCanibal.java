package es.upm.dit.concurrencia.ej06.ad;

import java.util.concurrent.ThreadLocalRandom;

public class MyCanibal extends Thread {
	private final MyMarmita marmita;

	public MyCanibal(MyMarmita marmita, String nombre) {
		super(nombre);
		this.marmita = marmita;
	}

	public void run() {
		try {
			while (true) {
				System.out.println("Tengo Hambre!!! " + Thread.currentThread().getName());
				marmita.cogeCucharon();
				System.out.println("Se está sirviendo el caníbal " + Thread.currentThread().getName());
				Thread.sleep(500);
				marmita.dejaCucharon();
				System.out.println("Está comiendo y durmiendo el canibal " + Thread.currentThread().getName());
				Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));

			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
