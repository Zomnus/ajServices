package its.webservice.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class TextMessageCreator implements MessageCreator {

	private String message;

	public TextMessageCreator(String message) {
		this.message = message;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		return session.createTextMessage(message);
	}
}