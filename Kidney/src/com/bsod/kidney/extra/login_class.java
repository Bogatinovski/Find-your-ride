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
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

public class login_class {

	private String TAG = "login_class -";

	private Context context;

	private String username;
	private String password;
	private String first;
	private String last;

	public login_class(Context context_, String user_name_, String password_,
			String first_, String last_) {
		this.context = context_;

		this.first = last_;
		this.first = first_;
		this.username = user_name_;
		this.password = password_;

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	public login_class(Context context_) {
		this.context = context_;
	}

	public String getJSON_function(String url) {
		getJSON gettingJson = new getJSON();
		String reaspone = gettingJson.doInBackground(url);
		return reaspone;
	}

	private class getJSON extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			DefaultHttpClient httpclient = new DefaultHttpClient(
					new BasicHttpParams());
			HttpGet httpget = new HttpGet(urls[0]);

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

				// parshing json
				JSONObject jObject = new JSONObject(result);

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

	public String postJSON_function(String url) {
		postJSON sendingJson = new postJSON();
		String reaspone = sendingJson.doInBackground(url);
		return reaspone;
	}

	private class postJSON extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			String result = "";

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urls[0]);

			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs
						.add(new BasicNameValuePair("username", username));
				nameValuePairs
						.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("first", first));
				nameValuePairs.add(new BasicNameValuePair("last", last));
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

				result = response.getStatusLine().toString();

				Log.d("RESULTS", "" + result);

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

	public Boolean checkForLoged_in() {
		Boolean status = null;
		SharedPreferences prefs = context.getSharedPreferences("loged_in",
				Context.MODE_PRIVATE);
		status = prefs.getBoolean("loged_in", false);
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	public void store_Loged_in_user() {
		SharedPreferences prefs = context.getSharedPreferences("loged_in",
				Context.MODE_PRIVATE);
		prefs.edit().putBoolean("loged_in", true).commit();
	}

	public void remove_Loged_in_user() {
		SharedPreferences prefs = context.getSharedPreferences("loged_in",
				Context.MODE_PRIVATE);
		prefs.edit().putBoolean("loged_in", false).commit();
	}

	public void put_username(String username) {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		prefs.edit().putString("username", "" + username).commit();
	}

	public void put_password(String password) {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		prefs.edit().putString("password", "" + password).commit();
	}

	public void put_first(String first_) {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		prefs.edit().putString("first", "" + first_).commit();
	}

	public void put_last(String last_) {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		prefs.edit().putString("last", "" + last_).commit();
	}

	public String get_username() {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		return prefs.getString("username", "username");
	}

	public String get_password() {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		return prefs.getString("password", "password");
	}

	public String get_first() {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		return prefs.getString("first", "first");
	}

	public String get_last() {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		return prefs.getString("last", "last");
	}

	public void remove_username_and_password_and_firstLast(String username) {
		SharedPreferences prefs = context.getSharedPreferences(
				"username_password_first_last", Context.MODE_PRIVATE);
		prefs.edit().clear().commit();
	}

	public void updateUser(String user) {
		username = user;
	}

	public void updatePassword(String pass) {
		password = pass;
	}

	public void updateFirst(String first_) {
		first = first_;
	}

	public void updateLast(String last_) {
		last = last_;
	}
}
