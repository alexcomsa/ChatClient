package ro.alex.message;

import java.util.LinkedList;

public abstract class MessageBox<T> {
	
	protected LinkedList<T> messageContainer;
	
	public abstract void addMessage(T message);
	
	public abstract void removeMessage(T message);
	
	
	

}
