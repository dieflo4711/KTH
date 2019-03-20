package bearandhoney;

import java.util.Scanner;

public class main {
	public static void main(String[] args) throws InterruptedException {
		
		int min = 1;
		int max = 10;
		
		System.out.println("Enter the pot size and amount of bees\n");
		Scanner sc = new Scanner(System.in);
		int potSize = sc.nextInt();
		int numBees = sc.nextInt();
		
		Pot pot = new Pot(potSize, potSize);
		
		Thread bees[] = new Thread[numBees];
		
		Thread bear = new Thread(new Bear(pot));
		
		
		for(int i = 0; i < numBees; i++) {
			bees[i] = new Thread(new Bees(i+1, min, max, pot));
			bees[i].start();
		}
		
		bear.start();
		
		bear.join();
		
		for(int i = 0; i < numBees; i++) {
			bees[i].join();
		}
		
		
		System.out.println("Not for ever??");
		
	}
}
