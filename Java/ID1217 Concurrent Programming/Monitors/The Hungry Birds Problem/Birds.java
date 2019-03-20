package hungrybirds;

import java.util.Random;

public class Birds implements Runnable {
	Dish dish;
	int id;
	int min;
	int max;
	
	public Birds(int id, int min, int max, Dish dish) {
		this.id = id;
		this.min = min;
		this.max = max;
		this.dish = dish;
	}
	public void run() {
		while(true) {
			try {
				dish.eating(id);
				Thread.sleep(((new Random()).nextInt(max - min + 1) + min)*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
