package com.bsod.kidney;

import com.bsod.kidney.extra.login_class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends Activity {

	private String TAG = "LogInActivity";
	Context context;
	login_class login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);

		context = getApplicationContext();

		final EditText user_name = (EditText) findViewById(R.id.user);
		final EditText password = (EditText) findViewById(R.id.password);
		final EditText first = (EditText) findViewById(R.id.first);
		final EditText last = (EditText) findViewById(R.id.last);

		String user_name_string = "" + user_name.getText().toString();
		String password_string = "" + password.getText().toString();
		String first_string = "" + first.getText().toString();
		String last_string = "" + last.getText().toString();

		login = new login_class(getApplicationContext(), user_name_string,
				password_string, first_string, last_string);

		Button logIn = (Button) findViewById(R.id.logIn);
		logIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (login.isNetworkAvailable()) {
					Log.d(TAG, "network is okey!");
					if (!user_name.getText().toString().equals("")
							&& !password.getText().toString().equals("")) {
						login.updateUser(user_name.getText().toString());
						login.updatePassword(password.getText().toString());
						String reaspone = login
								.postJSON_function("http://192.168.1.129/kidney/public/scripts/login.php");
						if (reaspone.equals("HTTP/1.1 200 OK")) {
							// log in the user and redirect
							login.updateUser(user_name.getText().toString());
							login.updatePassword(password.getText().toString());
							login.put_password(password.getText().toString());
							login.put_username(user_name.getText().toString());

							Intent i = new Intent(LogInActivity.this,
									ControlPanelActivity.class);
							i.putExtra("username", login.get_username());
							i.putExtra("password", login.get_password());
							startActivity(i);
						} else {
							Toast.makeText(context, "Не сте регистрирани!",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(context, "Внесете е-пошта и лозинка!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, "Немате интернет конекција...",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button register = (Button) findViewById(R.id.register);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// TODO Auto-generated method stub
				if (login.isNetworkAvailable()) {
					if (!user_name.getText().toString().equals("")
							&& !password.getText().toString().equals("")) {
						login.updateUser(user_name.getText().toString());
						login.updatePassword(password.getText().toString());
						login.updateFirst(first.getText().toString());
						login.updateLast(last.getText().toString());
						String reaspone = login
								.postJSON_function("http://192.168.1.129/kidney/public/scripts/register.php");
						if (reaspone.equals("HTTP/1.1 200 OK")) {
							// log in the user and redirect
							login.put_password(password.getText().toString());
							login.put_username(user_name.getText().toString());
							login.put_first(first.getText().toString());
							login.put_last(last.getText().toString());
							login.store_Loged_in_user();

							// redirecting
							Intent i = new Intent(LogInActivity.this,
									ControlPanelActivity.class);
							i.putExtra("username", login.get_username());
							i.putExtra("password", login.get_password());
							i.putExtra("first", login.get_first());
							i.putExtra("last", login.get_last());
							startActivity(i);
							
							Toast.makeText(context, "Добродојдовте!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context, "Некаква грешка беше направена...",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(context,
								"Внесете ги потребните податоци...",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(context, "Немате интернет конекција...",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	void forTravelers() {
		Intent myIntent = new Intent(LogInActivity.this, LogInActivity.class);
		LogInActivity.this.startActivity(myIntent);
	}

	void forControlPanel() {
		Intent myIntent = new Intent(LogInActivity.this,
				ControlPanelActivity.class);
		LogInActivity.this.startActivity(myIntent);
	}

}
