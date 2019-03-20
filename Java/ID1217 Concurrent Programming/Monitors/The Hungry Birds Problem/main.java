package hungrybirds;

import java.util.Scanner;

public class main {

	public static void main(String[] args) throws InterruptedException {
		
		int min = 1;
		int max = 10;
		
		System.out.println("Enter the worm and thread count\n");
		Scanner sc = new Scanner(System.in);
		int worms = sc.nextInt();
		int numbBirds = sc.nextInt();
		
		Dish put = new Dish(worms, worms);
		
		Thread birds[] = new Thread[numbBirds];
		
		Thread parent = new Thread(new Parent(put));
		
		
		for(int i = 0; i < numbBirds; i++) {
			birds[i] = new Thread(new Birds(i+1, min, max, put));
			birds[i].start();
		}
		
		parent.start();
		
		parent.join();
		
		for(int i = 0; i < numbBirds; i++) {
			birds[i].join();
		}
		
		
		System.out.println("Not for ever??");
		
	}
	
}
