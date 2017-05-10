package me.rami.sub;

import javax.jms.JMSException;

public class UnreadableSqsMessageException extends RuntimeException {

    public UnreadableSqsMessageException(String message, Throwable cause) {
        super(message, cause);
    }

	public UnreadableSqsMessageException(String message, JMSException e) {
		// TODO Auto-generated constructor stub
	}	
}
