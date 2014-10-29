package com.fiap.icinema;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class ServiceAPI {

	private final String API_URL = "http://1-dot-fiapmobilecupa10.appspot.com/ListDataJDO";
	private AQuery aquery;
	private ProgressDialog pd;

	public void obterJson(final Context ctx) {
		aquery = new AQuery(ctx);
		aquery.progress(carregar(ctx, "Carregando...")).ajax("http://graph.facebook.com/10152840233733953",
				JSONObject.class, new AjaxCallback<JSONObject>() {

					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {

						if (object != null) {
							Toast.makeText(ctx, "Tem coisa",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(ctx, "Tem nada", Toast.LENGTH_SHORT)
									.show();
						}
					}

				});
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

}
