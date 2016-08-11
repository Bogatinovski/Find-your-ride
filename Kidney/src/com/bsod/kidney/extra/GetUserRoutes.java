package com.bsod.kidney.extra;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetUserRoutes {

	public GetUserRoutes() {

	}

	public String GetDestinationIds_function(String url, String user) {
		GetDestinationIds_bg getDesIds = new GetDestinationIds_bg();
		String reaspone = getDesIds.doInBackground(url, user);
		return reaspone;
	}

	private class GetDestinationIds_bg extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			DefaultHttpClient httpclient = new DefaultHttpClient(
					new BasicHttpParams());
			HttpGet httpget = new HttpGet(urls[0] + "?user=" + urls[1]);

			InputStream inputStream = null;
			String result = null;

			try {

				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();

				inputStream = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"), 8);

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				result = sb.toString();

				Log.d("RESULTS", "" + result);

			} catch (Exception e) {
				// Oops
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception squish) {

				}
			}

			return result;
		}

		@Override
		protected void onPostExecute(String result) {

		}
	}

	public ArrayList<String> parshDestinationIds(String result) {
		
		final ArrayList<String> params = new ArrayList<String>();

		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject c = jArray.getJSONObject(i);
				String od = c.getString("od");
				String do_ = c.getString("do");
				String sits = c.getString("sits");
				String date = c.getString("datetime");
				String cena = c.getString("cena");
				String telefon = c.getString("telefon");
				String ime = c.getString("first");
				String id = c.getString("trip_id");
				params.add("" + od);
				params.add("" + do_);
				params.add("" + sits);
				params.add("" + date);
				params.add("" + cena);
				params.add("" + telefon);
				params.add("" + ime);
				params.add("" + id);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return params;
	}

}
