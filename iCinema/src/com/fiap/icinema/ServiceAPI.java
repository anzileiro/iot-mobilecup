package com.fiap.icinema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class ServiceAPI {

	private final String API_URL = "http://1-dot-fiapmobilecupa5.appspot.com/ListDataJDO";
	private AQuery aquery;
	private ProgressDialog pd;
	private boolean logou;
	private AudioManager myAudioManager;
	
	public ServiceAPI(AudioManager audioManager){
		myAudioManager = audioManager;
	}

	public boolean isLogou() {
		return logou;
	}

	public void setLogou(boolean logou) {
		this.logou = logou;
	}

	private int vlLuz;

	public void resolverLuz(final Context ctx) {
		aquery = new AQuery(ctx);
		aquery.progress(carregar(ctx, "Carregando...")).ajax(API_URL,
				JSONObject.class, new AjaxCallback<JSONObject>() {

					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {

						if (object != null) {
							try {

								JSONArray data = object.getJSONArray("data");
								JSONObject dados = data.getJSONObject(1);
								int luz = dados.getInt("luz");
								
								if(luz < 300){
									vibraCallMode();
									irParaTelaBoaSessao((Activity) ctx);									
								}else{
									normalMode();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						} else {
							Toast.makeText(ctx, "JSON NULL", Toast.LENGTH_SHORT)
									.show();
						}
					}

				});
	}
	
	private void irParaTelaBoaSessao(Activity activity){
		Intent i = new Intent(activity, BoaSessaoActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		activity.startActivity(i);
		activity.finish();
	}

	private ProgressDialog carregar(Context ctx, String msg) {
		pd = new ProgressDialog(ctx);
		pd.setMessage(msg);
		pd.setIndeterminate(false);
		pd.setMax(100);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setCancelable(false);
		return pd;
	}

	public int getVlLuz() {
		return vlLuz;
	}

	public void setVlLuz(int vlLuz) {
		this.vlLuz = vlLuz;
	}


	private void vibraCallMode() {
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}

	private void normalMode() {
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}

}

class Base extends Activity {
	public void iniciarIntent(Context contexto, Class<?> classe) {
		Intent i = new Intent(contexto, classe);
		startActivity(i);
	}
}