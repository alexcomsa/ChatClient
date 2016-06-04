package ro.alex.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class Client implements Runnable {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	public Client(Socket socketToServer) {
		this.socketToServer = socketToServer;
		serverIPAddress = this.socketToServer.getInetAddress();

	}

	private Socket socketToServer;
	private InetAddress serverIPAddress;
	private int timeout = 15000;
	private InputStreamReader inStream;
	private BufferedReader inReader;

	public Socket getSocketToServer() {
		return socketToServer;
	}

	public void setSocketToServer(Socket socketToServer) {
		this.socketToServer = socketToServer;
	}

	public InetAddress getServerIPAddress() {
		return serverIPAddress;
	}

	public void setServerIPAddress(InetAddress serverIPAddress) {
		this.serverIPAddress = serverIPAddress;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public InputStreamReader getInStream() {
		return inStream;
	}

	public void setInStream(InputStreamReader inStream) {
		this.inStream = inStream;
	}

	public BufferedReader getInReader() {
		return inReader;
	}

	public void setInReader(BufferedReader inReader) {
		this.inReader = inReader;
	}

	@Override
	public void run() {
		try {
			validate(socketToServer);
			socketToServer.setSoTimeout(timeout);
			LOGGER.info("A connetion is oppend to "
					+ serverIPAddress.toString());
			setInStream(new InputStreamReader(socketToServer.getInputStream()));
			setInReader(new BufferedReader(getInReader()));

			while (readServerMessage()!=null) {
				
				System.out.println("Message from "+serverIPAddress+readServerMessage()+":\t");
				
			}

		} catch (SocketException e) {

			LOGGER.severe(e.getMessage());
			return;
		} catch (IOException e) {
			LOGGER.severe("An error ocured while trying to read from "
					+ socketToServer + "\n" + e.getMessage());
		}

	}

	private void validate(Socket socketToServer) {
		if (socketToServer == null) {
			throw new RuntimeException("The socket is not connected");
		}
		LOGGER.info(socketToServer.toString() + " is a valid socket");

	}

	private String readServerMessage() {
		String line = null;
		try {
			while((line = inReader.readLine())!=null){
				
			}
			
		} catch (IOException e) {
			
			LOGGER.severe(e.getMessage());
		}
		return line;
	}

}
