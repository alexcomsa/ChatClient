package ro.alex.message;

import java.util.LinkedList;

public class Inbox extends MessageBox<ChatMessage> {

	 public Inbox() {
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
	
	public String readInboxMessages(){
		if(messageContainer.isEmpty()){
			return null;
		}
		StringBuilder display = new StringBuilder();
		for(ChatMessage message : messageContainer){
			display.append(message.getFrom()).append(": ").append(message.getContent()).append("\n");
		}
		
		return display.toString();
		
	}

}
