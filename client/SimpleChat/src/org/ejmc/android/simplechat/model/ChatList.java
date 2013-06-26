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

	/**
	 * @param msgs the msgs to set
	 */
	public void setMsgs(ArrayList<Message> msgs) {
		this.msgs = msgs;
	}
	
	public void addMessage(Message msg){
		msgs.add(msg);
	}

	
}
