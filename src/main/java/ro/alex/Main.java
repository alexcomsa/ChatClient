package ro.alex;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import ro.alex.client.Client;


public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) {
		
		LOGGER.info("Clinet has started "+new Date());
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Socket serverSocket;
		try {
			serverSocket = new Socket("localhost",9999);
			es.submit(new Client(serverSocket));
		} catch (UnknownHostException e) {
			LOGGER.severe("A problem occured while connecting to server \n"+e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
		}
		
				
				
		
	}

}
