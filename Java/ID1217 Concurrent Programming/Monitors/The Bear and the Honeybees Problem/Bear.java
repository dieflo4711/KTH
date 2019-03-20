package bearandhoney;


public class Bear implements Runnable {
	Pot pot;
	
	public Bear(Pot pot) {
		this.pot = pot;
	}
	
	public void run() {
		while(true)
			pot.eating();
	}
}
