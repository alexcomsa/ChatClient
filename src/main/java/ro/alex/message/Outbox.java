package ro.alex.message;

import java.util.LinkedList;

public class Outbox extends MessageBox<ChatMessage> {

	public Outbox() {
		messageContainer = new LinkedList<ChatMessage>();
	}

	@Override
	public void addMessage(ChatMessage message) {
		messageContainer.add(message);

	}

	@Override
	public void removeMessage(ChatMessage message) {
		messageContainer.remove(message);

	}

	public  boolean hasMessages() {
		
		
		return !messageContainer.isEmpty();
	}
	
	

}
