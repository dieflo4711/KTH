
import java.net.*;
import java.io.*;
import myrunnable.MyRunnable;


public class ConcHTTPAsk {

	public static void main(String[] args) throws IOException {
		
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));
		
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			
			Runnable r = new MyRunnable(connectionSocket);
			new Thread(r).start();
		}	
    }
}
