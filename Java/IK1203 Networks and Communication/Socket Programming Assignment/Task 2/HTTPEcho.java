
import java.net.*;
import java.io.*;

public class HTTPEcho {
	public static void main( String[] args) throws IOException {
		
		ServerSocket welcomeSocket = new ServerSocket(Integer.parseInt(args[0]));

		while(true) {
				String clientSentence = "";
				
				Socket connectionSocket = welcomeSocket.accept();
				
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			    
				while(inFromClient.ready()) {
					clientSentence += inFromClient.readLine() + '\n';
				}
				
				outToClient.writeBytes("HTTP/1.1 200 OK" + "\r\n\r\n");
				outToClient.writeBytes(clientSentence + "\r\n\r\n");
				
				outToClient.flush();
				outToClient.close();
				connectionSocket.close();
		} 	
    }
}