package org.ejmc.android.simplechat.net;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.RequestError;

/**
 * Empty response handler.
 * 
 * Base class for Net Response handlers.
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class NetResponseHandler<Response> {

	private ChatList lista;
	

	/**
	 * Handles a successful request
	 * */
	public void onSuccess(Response response) {
		lista = (ChatList) response;
	}

	/**
	 * @return the lista
	 */
	public ChatList getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(ChatList lista) {
		this.lista = lista;
	}

	/**
	 * Handles a network error.
	 */
	public void onNetError() {

	}

	/**
	 * Handles a request error.
	 */
	public void onRequestError(RequestError error) {

	}

}
