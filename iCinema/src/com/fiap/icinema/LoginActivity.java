package com.fiap.icinema;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

	private EditText txtTicket;
	private ServiceAPI se;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		se = new ServiceAPI((AudioManager)getSystemService(AUDIO_SERVICE));		
		
		se.resolverLuz(this);			
			
		txtTicket = (EditText) findViewById(R.id.txtTicket);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void validarTicket(View v) {
		if (txtTicket.getText().toString().equalsIgnoreCase("a")) {
			Intent i = new Intent(this, PedidoActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(this, "Ticket inválido",
					Toast.LENGTH_SHORT).show();
		}
	}

	
	
	

}
