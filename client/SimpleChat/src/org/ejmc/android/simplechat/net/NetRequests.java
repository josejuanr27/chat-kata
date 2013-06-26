package org.ejmc.android.simplechat.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void chatGET(int seq, NetResponseHandler<ChatList> handler)
			throws ClientProtocolException, IOException {

		try {
			HttpClient client = AndroidHttpClient.newInstance("http.agent");
			HttpGet httpget = new HttpGet(
					"http://172.20.0.9/chat-kata/api/chat");
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			String entityString = EntityUtils.toString(entity);
			

			if (entity != null) {
				JSONObject jsonObject = new JSONObject(entityString);
				JSONArray jsonMainArr = new JSONArray();
				jsonMainArr = jsonObject.getJSONArray("messages");

				Message auxMessage;
				ChatList listaMensajes = new ChatList();

				for (int i = 0; i < jsonMainArr.length(); i++) {
					JSONObject childJson = jsonMainArr.getJSONObject(i);
					auxMessage = new Message(childJson.getString("nick"),
							childJson.getString("message"));
					listaMensajes.addMessage(auxMessage);
				}

				handler.onSuccess(listaMensajes);
			}
		} catch (Exception e) {
			Log.e("Net", "Error", e);
		}

	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 * @throws JSONException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public void chatPOST(Message message, NetResponseHandler<Message> handler)
			throws JSONException, ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://172.20.0.9/chat-kata/api/chat");
		post.setHeader("content-type", "application/json");

		JSONObject msg = new JSONObject();
		msg.put("nick", message.getUserMessage());
		msg.put("message", message.getMessage());

		StringEntity entity = new StringEntity(msg.toString());
		post.setEntity(entity);

		client.execute(post);
	}

}
