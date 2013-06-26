package org.ejmc.android.simplechat.model;

/**
 * Simple message.
 * 
 * @author startic
 * 
 */
public class Message {

	private String userMessage;
	private String message;

	/**
	 * @param userMessage
	 * @param message
	 */
	public Message() {
		super();
		this.userMessage = new String();
		this.message = new String();
	}
	
	public Message(String nick, String msg) {
		this.userMessage = nick;
		this.message= msg;
	}

	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}

	/**
	 * @param userMessage
	 *            the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}