package com.bsod.kidney;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

public class MainScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button toTravelers = (Button) findViewById(R.id.forTravelers);
		Button toDrivers = (Button) findViewById(R.id.forDrivers);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		fadeInOut(1, 0, 2000, imageView);
		
		toTravelers.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				forTravelers();
			}
		});

		toDrivers.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logIn();
			}
		});

	}

	void forTravelers() {
		Intent myIntent = new Intent(MainScreenActivity.this,
				ForTravelersActivity.class);
		MainScreenActivity.this.startActivity(myIntent);
	}

	void forDrivers() {
		Intent myIntent = new Intent(MainScreenActivity.this,
				ForDriversActivity.class);
		MainScreenActivity.this.startActivity(myIntent);
	}

	void logIn() {
		Intent myIntent = new Intent(MainScreenActivity.this,
				LogInActivity.class);
		MainScreenActivity.this.startActivity(myIntent);
	}
	
	void fadeInOut(final int from, final int to, final int time, final View v) {

		AlphaAnimation alpha = new AlphaAnimation(from, to);
		alpha.setDuration(time);
		v.setAnimation(alpha);
		v.startAnimation(alpha);

		alpha.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				fadeInOut(to, from, time, v);
				
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
}
