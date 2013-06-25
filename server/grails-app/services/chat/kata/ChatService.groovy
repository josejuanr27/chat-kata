package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock


class ChatService {
	private final ArrayList<ChatMessage> listaMs = new ArrayList<ChatMessage>()
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock()
	private final Lock r = rwl.readLock()
	private final Lock w = rwl.writeLock()
	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		r.lock()
		try {
			if(fromSeq==null){
				fromSeq=0
			}
			else{
				fromSeq++
			}

			//If rápido
			//fromSeq==null?fromSeq=0:fromSeq++

			while(listaMs.size() > fromSeq){
				collector.add(listaMs[fromSeq])
				fromSeq++
			}
			fromSeq--
		}
		finally { r.unlock() }
		return fromSeq

	}

	/**listaMs
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		w.lock()
		try { listaMs.add(message) }
		finally { w.unlock()}
	}
}
