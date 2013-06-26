package org.ejmc.android.simplechat;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	private TextView nom;
	private Button send;
	private Button refresh;
	private EditText mensaje;
	private EditText listaMsg;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msgAndroid){
			ChatList chatList = (ChatList) msgAndroid.obj;
			ArrayList<Message> messages = chatList.getMsgs();
			
			for (Message message : messages) {
				listaMsg.append("\n "+message.getUserMessage()+": "+message.getMessage()+"\n---");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		send = (Button) findViewById(R.id.btnEnviar);
		refresh = (Button) findViewById(R.id.btnRecibir);
		nom = (TextView) findViewById(R.id.user);
		mensaje = (EditText) findViewById(R.id.mensaje);
		listaMsg = (EditText) findViewById(R.id.listaMensajes);

		Bundle bundle = getIntent().getExtras();
		nom.setText(bundle.getString("nombre"));
		// Show the Up button in the action bar.
		setupActionBar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

	public void recibir(View view) {
		new Thread(new Runnable() {
			public void run() {
				NetRequests prueba = new NetRequests();
				NetResponseHandler<ChatList> handler = new NetResponseHandler<ChatList>();
				try {
					 
					prueba.chatGET(0, handler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				android.os.Message mensajeAnd = new android.os.Message();
				mensajeAnd.obj = handler.getLista();
				mHandler.sendMessage(mensajeAnd);
			}
		}).start();
	}

	public void enviar(View view) {
		new Thread(new Runnable() {
			public void run() {
				NetRequests prueba = new NetRequests();
				NetResponseHandler<Message> handler = new NetResponseHandler<Message>();
				Message msg = new Message();
				try {
					msg.setUserMessage(nom.getText().toString());
					msg.setMessage(mensaje.getText().toString());
					prueba.chatPOST(msg, handler);
				} catch (Exception e) {
					e.printStackTrace();
				}
		
			}
		}).start();
	}

}
