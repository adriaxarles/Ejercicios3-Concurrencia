package es.upm.dit.concurrencia.ej06.ad;

public class MyCocinero extends Thread {
	private final MyMarmita marmita;

	public MyCocinero(MyMarmita marmita, String nombre) {
		super(nombre);
		this.marmita = marmita;
	}

	@Override
	public void run() {
		try {
			while (true) {
				marmita.listoCocinar();
				System.out.println(Thread.currentThread().getName() + ":está cocinando ");
				Thread.sleep(500);
				System.out.println(Thread.currentThread().getName() + ":la comida ya está lista!!!! ");
				marmita.avisaCanibal();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
