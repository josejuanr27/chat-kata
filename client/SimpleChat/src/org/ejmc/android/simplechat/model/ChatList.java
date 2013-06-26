package org.ejmc.android.simplechat.model;

import java.util.ArrayList;

/**
 * List off chat messages..
 * 
 * @author startic
 * 
 */
public class ChatList {

	ArrayList<Message> msgs;
	int last_seq = 0;

	public ChatList() {
		super();
		this.msgs = new ArrayList<Message>();
	}

	/**
	 * @return the msgs
	 */
	public ArrayList<Message> getMsgs() {
		return msgs;
	}

	public ArrayList<String> getMessages() {
		ArrayList<String> mensajes = new ArrayList<String>();
		for (int i = 0; i < msgs.size(); i++) {
			mensajes.add(msgs.get(i).getUserMessage() + ": " + msgs.get(i).getMessage());
		}
		last_seq = last_seq + msgs.size();
		return mensajes;
	}

	/**
	 * @return the last_seq
	 */
	public int getLast_seq() {
		return last_seq;
	}

	/**
	 * @param last_seq the last_seq to set
	 */
	public void setLast_seq(int last_seq) {
		this.last_seq = last_seq;
	}

	/**
	 * @param msgs
	 *            the msgs to set
	 */
	public void setMsgs(ArrayList<Message> msgs) {
		this.msgs = msgs;
	}

	public void addMessage(Message msg) {
		msgs.add(msg);
	}

}
