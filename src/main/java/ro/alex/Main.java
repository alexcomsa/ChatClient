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
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		LOGGER.info("Clinet has started "+new Date());
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		Socket serverSocket =  new Socket("localhost",9999);
		
				es.submit(new Client(serverSocket));
				
		
	}

}
