package com.bsod.kidney;

import java.util.ArrayList;
import java.util.HashMap;

import com.bsod.kidney.download.UpdateTrip;
import com.bsod.kidney.extra.GetDestinationIds;
import com.bsod.kidney.ui.fragments.DatePickerFragment;
import com.bsod.kidney.ui.fragments.TimePickerFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForDriversActivity extends FragmentActivity {

		public final int DATE_CODE = 0001;
	    private DatePickerFragment datePickerFragment;
	    private FragmentManager fragmentManager;
	    private TimePickerFragment timePickerFragment;
	    private TextView tvDate;
	    private TextView tvTime;

	    private AutoCompleteTextView atvOdMesto;
	    private AutoCompleteTextView atvDoMesto;
	    private EditText edtSlobMesta;
	    private EditText edtCena;
	    private EditText edtTelefon;
	    private EditText edtOpis;
	    
	    private ArrayList<String> gradovi;
	    private HashMap<String, String> gradoviId;
	     
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_for_drivers);
	        
	        initialize();
	        
	        dowloadCities();
	        
	        
	    	ArrayAdapter<String> gradoviArrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, gradovi);
	        
	    	atvOdMesto.setAdapter(gradoviArrayAdapter);
	    	atvDoMesto.setAdapter(gradoviArrayAdapter);
	    	
	        
	        Button btnChooseDate = (Button) findViewById(R.id.btnChooseDate);

	        btnChooseDate.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                datePickerFragment = new DatePickerFragment();
	                fragmentManager = getSupportFragmentManager();
	                datePickerFragment.show(fragmentManager, "fragmentDate");
	            }
	        });


	        Button btnChooseTime = (Button) findViewById(R.id.btnChooseTime);

	        btnChooseTime.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                timePickerFragment = new TimePickerFragment();
	                fragmentManager = getSupportFragmentManager();
	                timePickerFragment.show(fragmentManager, "fragmentTime");
	            }
	        });


	        Button btnPublish = (Button) findViewById(R.id.btnPublish);

	        btnPublish.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                //publish on server
	            	
	            	String url = "http://192.168.1.129/kidney/public/scripts/add_trip.php";
	        		String email = getLogInEmail();
	        		String od_mesto= atvOdMesto.getText().toString();
	        				od_mesto = gradoviId.get(od_mesto);	        				
	        				
	        		String do_mesto = atvDoMesto.getText().toString();
	        				do_mesto = gradoviId.get(do_mesto);
	        		
	        		String mesta = edtSlobMesta.getText().toString();
	        		String cena = edtCena.getText().toString();
	        		String telefon = edtTelefon.getText().toString();
	        		String datum = tvDate.getText().toString();
	        		String vreme = tvTime.getText().toString();
	        		String opis = edtOpis.getText().toString();
	            	
	            	String statusResponse = UpdateTrip.updateAddTripToUrl(url,email,od_mesto,do_mesto,mesta,cena,telefon,datum,vreme,opis);
	            	
	            	Log.d("server post trip response", statusResponse);
	            	
	            	setResult(RESULT_OK);
	            	finish();
	            	
	            }
	        });
	    }

	    private void dowloadCities() {
			// TODO Auto-generated method stub
	    	GetDestinationIds getDesIds = new GetDestinationIds();
			String reaspone = getDesIds.GetDestinationIds_function("http://192.168.1.129/kidney/public/scripts/get_all_destinations.php");
			ArrayList<String> destinations = getDesIds.parshDestinationIds(reaspone);
	    	
			
			
			for(int i=0;i<destinations.size();i+=2){
				String city = destinations.get(i);
				String cityId = destinations.get(i+1);
				
				Log.d("destinacii", city + " " + cityId);

				
				gradovi.add(city);
				gradoviId.put(city,cityId);				
			}
			
		}

		public void updateDate(String pickedDate){
	        tvDate.setText(pickedDate);
	    }

	    public void updateTime(String pickedTime){
	        tvTime.setText(pickedTime);
	    }
		
	    public void initialize(){
	    	
	    	gradovi = new ArrayList<String>();
	    	gradoviId = new HashMap<String, String>();
	    	
	    	tvDate = (TextView) findViewById(R.id.tvDate);
	        tvTime = (TextView) findViewById(R.id.tvTime);
	    	
	    	
	    	atvOdMesto = (AutoCompleteTextView) findViewById(R.id.atvOdMesto);
	    	atvDoMesto = (AutoCompleteTextView) findViewById(R.id.atvDoMesto);
	    	
	    	tvDate = (TextView) findViewById(R.id.tvDate);
	    	tvTime = (TextView) findViewById(R.id.tvTime);
	    	
	    	edtCena = (EditText) findViewById(R.id.edtCena);
	    	edtSlobMesta = (EditText) findViewById(R.id.edtSlobMesta);
	    	edtTelefon = (EditText) findViewById(R.id.edtTelefon);
	    	
	    	edtOpis = (EditText) findViewById(R.id.edtDescription);
	    	
	    	
	    }
	    
	    public String getLogInEmail() {
			SharedPreferences prefs = getApplicationContext().getSharedPreferences(
					"username_password_first_last", Context.MODE_PRIVATE);
			return prefs.getString("username", "username");
		}
	    
		

	}

