package es.upm.dit.concurrencia.ej07.ad;


public class MyFumador extends Thread {
	private final Mesa mesa;
	private final Ingrediente ingrediente;

	public MyFumador(Mesa mesa, String nombre, Ingrediente ingrediente) {
		super(nombre);
		this.mesa = mesa;
		this.ingrediente = ingrediente;
	}

	public void run() {
		try {
			while (true) {
				mesa.cogerIngredientes(ingrediente);
				System.out.println(Thread.currentThread().getName() + " Está liándose y fumándose el cigarro");
				Thread.sleep(500);
				System.out.println(
						Thread.currentThread().getName() + " Ya he dejado de fumar. Voy a avisar al estanquero");
				Thread.sleep(500);
				mesa.reposicionIngredientes();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
