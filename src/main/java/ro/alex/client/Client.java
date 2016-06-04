package ro.alex.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import ro.alex.message.ChatMessage;
import ro.alex.message.Inbox;
import ro.alex.message.Outbox;

public class Client implements Runnable {

	private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

	public Client(Socket socketToServer) {
		this.socketToServer = socketToServer;
		serverIPAddress = this.socketToServer.getInetAddress();
		outbox = new Outbox();
		inbox = new Inbox();

	}

	private Socket socketToServer;
	private InetAddress serverIPAddress;
	private int timeout = 15000;
	private InputStream inStream;
	private InputStreamReader inReader;
	private Inbox inbox;
	private Outbox outbox;

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

	public InputStream getInStream() {
		return inStream;
	}

	public void setInStream(InputStream inStream) {
		this.inStream = inStream;
	}

	public InputStreamReader getInReader() {
		return inReader;
	}

	public void setInReader(InputStreamReader inReader) {
		this.inReader = inReader;
	}

	@Override
	public void run() {
		try {
			validate(socketToServer);
			socketToServer.setSoTimeout(timeout);
			LOGGER.info("A connetion is oppend to "
					+ serverIPAddress.toString());
			setInStream((socketToServer.getInputStream()));
			setInReader(new InputStreamReader(getInStream()));

			while (!socketToServer.isClosed()) {
				collectMessage();
				sendOutboundMessage();
			}

		} catch (SocketException e) {

			LOGGER.severe(e.getMessage());
			return;
		} catch (IOException e) {
			LOGGER.severe("An error ocured while trying to read from "
					+ socketToServer + "\n" + e.getMessage());
		}

	}

	private void sendOutboundMessage() {
		while (outbox.hasMessages()) {

		}

	}

	private void collectMessage() {
		String message = readServerMessage();

		if (message.contains("xml")) {

			try {
				JAXBContext jaxbContext = JAXBContext
						.newInstance(ChatMessage.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext
						.createUnmarshaller();
				ChatMessage chatMessage = (ChatMessage) jaxbUnmarshaller
						.unmarshal(new ByteArrayInputStream(message
								.getBytes(Charset.forName("UTF-8"))));
				inbox.addMessage(chatMessage);

			} catch (JAXBException e) {
				
				e.printStackTrace();
			}

		}

	}

	private void validate(Socket socketToServer) {
		if (socketToServer == null) {
			throw new RuntimeException("The socket is not connected");
		}
		LOGGER.info(socketToServer.toString() + " is a valid socket");

	}

	private String readServerMessage() {
		StringBuilder aa = new StringBuilder();
		try {

			for (int c = inReader.read(); c != -1; c = inReader.read()) {

				aa.append((char) c);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return aa.toString();
	}

	public void sendMessage(String toWhom, String content) {
		ChatMessage message = new ChatMessage();
		message.setFrom(Integer.toString(socketToServer.getLocalPort()));
		message.setTo(toWhom);
		message.setContent(content);
		outbox.addMessage(message);

	}

}
