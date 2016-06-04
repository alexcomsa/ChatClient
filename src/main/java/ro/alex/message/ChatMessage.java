package ro.alex.message;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="message")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatMessage {
	
	public ChatMessage(){
		
	}
	
	@XmlElement
	private String content;
	@XmlElement
	private String from;
	@XmlElement
	private String to;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	@Override
	public String toString() {
		StringWriter retVal = new StringWriter();
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ChatMessage.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(this, retVal);
		} catch (JAXBException e) {

			e.printStackTrace();
		}

		return retVal.toString();
	}

}
