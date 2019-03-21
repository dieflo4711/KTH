package hungrybirds;

public class Dish {
	int worms;
	int init_worms;
	
	public Dish(int worms, int init_worms) {
		this.worms = worms;
		this.init_worms = init_worms;
	}
	
	public synchronized void eating(int id) {
		if(worms > 0) {
			worms -= 1;
			System.out.printf("Bird %d is eating, %d worms left\n", id, worms);
		} else
			notify();
	}
	
	public synchronized void put() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		worms = init_worms;
		notify();
		System.out.printf("Mom got more food\n");

	}
}
