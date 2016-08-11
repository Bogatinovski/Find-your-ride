package com.bsod.kidney.extra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ControlPanelServerCom {
	
	public ControlPanelServerCom(){
		
	}
	
	
	public String sendPost(String url, String parm1) {
		sendPost_bg c = new sendPost_bg();
		String reaspone = c.doInBackground(url, parm1);
		return reaspone;
	}
	
	public String sendPost_2x(String url, String parm1, String parm2) {
		sendPost_bg_2x c = new sendPost_bg_2x();
		String reaspone = c.doInBackground(url, parm1, parm2);
		return reaspone;
	}

	private class sendPost_bg extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			
			DefaultHttpClient httpclient = new DefaultHttpClient(
					new BasicHttpParams());
			
			HttpPost httppost = new HttpPost(urls[0] + 
					"?" + "trip_id=" + urls[1]);

			InputStream inputStream = null;
			String result = null;

			try {

				HttpResponse response = httpclient.execute(httppost);
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
	
	private class sendPost_bg_2x extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			
			DefaultHttpClient httpclient = new DefaultHttpClient(
					new BasicHttpParams());
			
			HttpPost httppost = new HttpPost(urls[0] + 
					"?" + "trip_id=" + urls[1] + "&action=" + urls[2]);

			InputStream inputStream = null;
			String result = null;

			try {

				HttpResponse response = httpclient.execute(httppost);
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
	
	public ArrayList<String> parshDestinationIds(String result){
		
		final ArrayList<String> params = new ArrayList<String>();
		
		try {
			JSONArray jArray = new JSONArray(result);
			for(int i = 0; i < jArray.length(); i++){
				JSONObject c = jArray.getJSONObject(i);
				String od = c.getString("od");
				String do_ = c.getString("do");
				String sits = c.getString("sits");
				String date = c.getString("datetime");
				String telefon = c.getString("telefon");
				String cena = c.getString("cena");
				String ime = c.getString("first");
				params.add("" + od);
				params.add("" + do_);
				params.add("" + sits);
				params.add("" + date);
				params.add("" + telefon);
				params.add("" + cena);
				params.add("" + ime);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return params;
	}
}
