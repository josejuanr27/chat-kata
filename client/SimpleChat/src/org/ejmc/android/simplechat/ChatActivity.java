package org.ejmc.android.simplechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	private ListView listaMsg;
	private NetRequests prueba = new NetRequests();
	private NetResponseHandler<ChatList> handler = new NetResponseHandler<ChatList>();
	private int last_seq = 0;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msgAndroid) {//
			ChatList cl = (ChatList) msgAndroid.obj;
			ArrayAdapter adaptador = new ArrayAdapter<String>(
					ChatActivity.this, android.R.layout.simple_list_item_1,
					cl.getMessages());

			last_seq = cl.getLast_seq();
			ListView lv = (ListView) findViewById(R.id.listaMensajes);
			lv.setAdapter(adaptador);
			lv.smoothScrollToPosition(last_seq);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		send = (Button) findViewById(R.id.btnEnviar);
		refresh = (Button) findViewById(R.id.btnRecibir);
		nom = (TextView) findViewById(R.id.user);
		mensaje = (EditText) findViewById(R.id.txtMensaje);
		listaMsg = (ListView) findViewById(R.id.listaMensajes);

		Bundle bundle = getIntent().getExtras();
		nom.setText(bundle.getString("nombre"));
		// Show the Up button in the action bar.
		setupActionBar();

		// Hilo para comprobar mensajes cada 10 seg
		new Thread(new Runnable() {
			Timer timer;

			@Override
			public void run() {
				timer = new Timer();
				timer.scheduleAtFixedRate(timerTask, 0, 10000);
			}

			TimerTask timerTask = new TimerTask() {
				public void run() {
					try {
						prueba.chatGET(last_seq, handler);
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					android.os.Message mensajeAnd = new android.os.Message();
					mensajeAnd.obj = handler.getLista();
					mHandler.sendMessage(mensajeAnd);
				}
			};

			public void stopTimer() {
				timer.cancel();
			}
		}).start();
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

					prueba.chatGET(last_seq, handler);
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
		Toast t2 = Toast.makeText(getApplicationContext(), R.string.updateMessages,
				Toast.LENGTH_LONG);
		t2.setGravity(Gravity.CENTER, 0, 0);
		t2.show();
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
		Toast.makeText(getApplicationContext(), R.string.sendMessage,
				Toast.LENGTH_LONG).show();
		recibir(view);
		mensaje.setText("");
	}

}
