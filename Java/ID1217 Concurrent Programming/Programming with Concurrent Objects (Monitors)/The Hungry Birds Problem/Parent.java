package hungrybirds;

public class Parent implements Runnable {
	Dish dish;
	
	public Parent(Dish dish) {
		this.dish = dish;
	}
	
	public void run() {
		while(true)
			dish.put();
	}
}