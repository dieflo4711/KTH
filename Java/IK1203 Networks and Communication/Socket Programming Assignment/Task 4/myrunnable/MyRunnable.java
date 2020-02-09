package myrunnable;

import java.net.*;
import java.io.*;

import tcpclient.TCPClient;
//http://localhost:8825/ask?hostname=time.nist.gov&port=13
//http://localhost:8825/ask?hostname=java.lab.ssvl.kth.se&port=13
//http://localhost:8825/ask?hostname=java.lab.ssvl.kth.se&port=7&string=diego

public class MyRunnable implements Runnable {
	   public Socket clientSocket;

	   public MyRunnable(Socket cSocket) {
		   clientSocket = cSocket;
	   }
	   
	   public void run() {
			try {	
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
			    
				String er400 = "400 Bad Request";
				String er404 = "404 Not Found";
				
				String clientSentence = "";
				String str = "";
				String serverOutput = "";
				String hostname = "";
				int port;
				
				clientSentence = inFromClient.readLine();
				
				String s = (clientSentence.replace("GET ", "").replace(" HTTP/1.1", "").replace(" HTTP/1.0", ""));
				
				if(s.startsWith("/ask")) {
				
					s = s.substring(s.indexOf("?")+1);
					
					String[] pairs = s.split("&");
		
					hostname = pairs[0].substring(pairs[0].lastIndexOf("=") + 1);			
					port = Integer.parseInt(pairs[1].substring(pairs[1].lastIndexOf("=") + 1));
					
					if(pairs.length == 3) {
						str = pairs[2].substring(pairs[2].lastIndexOf("=") + 1);
						serverOutput = TCPClient.askServer(hostname, port, str);
					} else
						serverOutput = TCPClient.askServer(hostname, port);
				} else
					serverOutput = er400;
				
				outToClient.writeBytes("HTTP/1.1 200 OK" + "\r\n\r\n");
				outToClient.writeBytes(serverOutput + "\r\n\r\n");
				
				outToClient.close();
				inFromClient.close();
				clientSocket.close();
				
			} catch(Exception e) {

			}
	   }
}
