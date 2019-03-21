package bearandhoney;

import java.util.Random;

public class Bees implements Runnable {
	int id;
	int min;
	int max;
	
	Pot pot;
	
	public Bees(int id, int min, int max, Pot pot) {
		this.id = id;
		this.min = min;
		this.max = max;
		this.pot = pot;
	}
	
	public void run() {
		while(true) {
			try {
				pot.put(id);
				Thread.sleep(((new Random()).nextInt(max - min + 1) + min)*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
