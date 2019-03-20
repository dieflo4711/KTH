package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
    	String sentence;
    	String modifiedSentence ="";
    	String tempModSenten ="";
    	int soTime = 1000;
    	int streamCheck;
    	
    	Socket clientSocket = new Socket(hostname, port); 
    	clientSocket.setSoTimeout(soTime); 
    	
    	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    	sentence = ToServer;
    	outToServer.writeBytes(sentence + '\n'); 
    	
    	while(true){     
            try {
            	while(inFromServer.ready()) {
            		if((tempModSenten = inFromServer.readLine()) != null) {
            			modifiedSentence += tempModSenten + "\n";
            			clientSocket.setSoTimeout(soTime);
            		}
            	}
            	
            	streamCheck = clientSocket.getInputStream().read();
            	
            	if(streamCheck == -1) 
            		break;
            } catch (SocketTimeoutException e) {
                break;
            } catch (IOException ex) {
            	break;
            }
        }
    	
    	clientSocket.close();
    	return modifiedSentence;
    	
    }

    public static String askServer(String hostname, int port) throws  IOException {
    	String sentence;
    	String modifiedSentence ="";
    	String tempModSenten ="";
    	int soTime = 1000;
    	int streamCheck;
    	
    	Socket clientSocket = new Socket(hostname, port); 
    	clientSocket.setSoTimeout(soTime); 
    	
    	DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
    	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    	
    	while(true){     
            try {
            	while(inFromServer.ready()) {
            		if((tempModSenten = inFromServer.readLine()) != null) {
            			modifiedSentence += tempModSenten + "\n";
            			clientSocket.setSoTimeout(soTime);
            		}
            	}
            	
            	streamCheck = clientSocket.getInputStream().read();
            	
            	if(streamCheck == -1)
            		break;
            } catch (SocketTimeoutException e) {
                break;
            } catch (IOException ex) {
            	break;
            }
        }
    	
    	clientSocket.close();
    	return modifiedSentence;
    }
}

