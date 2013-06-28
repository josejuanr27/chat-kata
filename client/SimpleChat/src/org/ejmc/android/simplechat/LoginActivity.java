package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

	private Button btnEntrar;
	private EditText nombre;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		nombre = (EditText) findViewById(R.id.txtNombre);
		btnEntrar = (Button) findViewById(R.id.btnEntrar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void ejecutar(View view) {
		if (nombre.getText() != null && !nombre.getText().toString().equals("")) {
			Intent i = new Intent(this, ChatActivity.class);
			i.putExtra("nombre", nombre.getText().toString());
			startActivity(i);

		} else {
			Toast.makeText(getApplicationContext(), R.string.alertSetUser,
					Toast.LENGTH_LONG).show();
		}
	}
	
	public void limpiar(View view){
		nombre.setText(null);
		Toast.makeText(getApplicationContext(), R.string.alertCleanUser, Toast.LENGTH_LONG).show();
	}

}
