package com.bsod.kidney.download;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;


public class UpdateTrip {
	
	
	
	public static String updateAddTripToUrl(String url,String email,String od_mesto, String do_mesto,String mesta,
			String cena,String telefon,String datum,String vreme, String opis) {
		updateOrAddTrip gettingJson = new updateOrAddTrip();
	
		String reaspone = gettingJson.doInBackground(url,email,od_mesto,do_mesto,mesta,cena,telefon,datum,vreme,opis);
		return reaspone;
	}

	private static class updateOrAddTrip extends AsyncTask<String, Void, String>  {
		@Override
		protected String doInBackground(String... params) {

			String result = "";

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);

			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				
				Log.d("parametars", params.toString());
				
				
				nameValuePairs.add(new BasicNameValuePair("email", params[1]));
				nameValuePairs.add(new BasicNameValuePair("od", params[2]));
				nameValuePairs.add(new BasicNameValuePair("do", params[3]));
				nameValuePairs.add(new BasicNameValuePair("mesta", params[4]));
				nameValuePairs.add(new BasicNameValuePair("cena", params[5]));
				nameValuePairs.add(new BasicNameValuePair("telefon", params[6]));
				nameValuePairs.add(new BasicNameValuePair("datum", params[7]));
				nameValuePairs.add(new BasicNameValuePair("vreme", params[8]));
				nameValuePairs.add(new BasicNameValuePair("opis", params[9]));

				
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				InputStream inputStream = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"), 8);

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}

				result = sb.toString();

				Log.d("RESULTS", "" + result);
				
				result = response.getStatusLine().toString();

				

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}

			return result;
		}


		@Override
		protected void onPostExecute(String result) {

		}
	}

}

