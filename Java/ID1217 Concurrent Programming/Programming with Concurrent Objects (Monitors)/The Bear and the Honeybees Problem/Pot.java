package bearandhoney;

public class Pot {
	int honey;
	int init_honey;
	
	public Pot(int honey, int init_honey) {
		this.honey = honey;
		this.init_honey = init_honey;
	}
	
	public synchronized void eating() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.printf("Bear is eating\n");
		honey = 0;
	}
	
	public synchronized void put(int id) {
		if(honey < init_honey) {
			honey += 1;
			System.out.printf("Bee %d has produced honey, honey: %d\n", id, honey);
		} else
			notify();
	}
}
