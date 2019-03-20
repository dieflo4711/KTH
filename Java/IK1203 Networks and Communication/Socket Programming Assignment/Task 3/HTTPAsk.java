import java.net.*;

import tcpclient.TCPClient;

import java.io.*;

public class HTTPAsk {

	public static void main(String[] args) throws IOException {
		
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

		while(true) {
			try {
				Socket connectionSocket = welcomeSocket.accept();
				
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			    
				String er400 = "400 Bad Request";
				String er404 = "404 Not Found";
				
				String clientSentence = "";
				String str = "";
				String serverOutput = "";
				String hostname = "";
				int port;
				
				while(inFromClient.ready()) {
					if((clientSentence = inFromClient.readLine()).contains("GET"))
						break;
				}
				
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
	
				outToClient.flush();
				outToClient.close();
				connectionSocket.close();
			} catch(Exception e) {
				
			}
		}	
    }
}
