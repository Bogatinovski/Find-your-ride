package com.bsod.kidney;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bsod.kidney.extra.DetaliMoiPatuvanja;
import com.bsod.kidney.extra.DetaliPatuvanje;
import com.bsod.kidney.extra.GetDestinationIds;
import com.bsod.kidney.extra.GetUserRoutes;
import com.bsod.kidney.extra.Patuvanje;
import com.bsod.kidney.extra.SendDestinationIds;

public class ControlPanelActivity extends FragmentActivity {
	
	public  String username = "";
	public  String password = "";
	
	PatuvanjeAdapter patuvanjeAdapter;
	ArrayList<Patuvanje> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel);
		
		getExtrasF();
		
		Button controlPanel = (Button) findViewById(R.id.newDestination);
		controlPanel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toCreatingNewDestination();
			}
		});
		
		reLoad();
		
		
	}
	
	public void reLoad(){
		GetUserRoutes getUserRoute = new GetUserRoutes();
		String reaspone = getUserRoute.GetDestinationIds_function(
				"http://192.168.1.129/kidney/public/scripts/get_trips_user2.php", username);
		final ArrayList<String> routes = getUserRoute.parshDestinationIds(reaspone);
		
		for(int i = 0; i < routes.size(); i++){
			Log.d("", "" + routes.get(i));
		}
		
		ListView listaPatuvanja = (ListView) findViewById(R.id.myTravels_ListView);
		lista = new ArrayList<Patuvanje>();
		
		for(int i = 0; i < routes.size(); i=i+8){
			Patuvanje nekoj_put = new Patuvanje();
			nekoj_put.setGradDo(routes.get(i));
			nekoj_put.setGradOd(routes.get(i+1));
			nekoj_put.setSlobodniMesta(Integer.parseInt(routes.get(i+2)));
			Date d = new Date();
			nekoj_put.setDataPatuvanje(d);
			nekoj_put.setCenaPatuvanje(Integer.parseInt(routes.get(i+4)));
			nekoj_put.setTelefon(routes.get(i+5));
			nekoj_put.setImeVozac(routes.get(i+6));
			nekoj_put.setId(routes.get(i+7));
			
			lista.add(nekoj_put);
			
			patuvanjeAdapter = new PatuvanjeAdapter(lista);
			listaPatuvanja.setAdapter(patuvanjeAdapter);
		}
		
		listaPatuvanja.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FragmentManager fm = getSupportFragmentManager();
				DetaliMoiPatuvanja dialog = DetaliMoiPatuvanja.newInstance(lista
						.get(position));
				dialog.show(fm, "DETALI_DIALOG");
			}
		});
	}

	private class PatuvanjeAdapter extends ArrayAdapter<Patuvanje> {

		public PatuvanjeAdapter(ArrayList<Patuvanje> lista) {
			super(getBaseContext(), android.R.layout.simple_list_item_1, lista);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if we weren't given a view, inflate one
			if (null == convertView) {
				convertView = getLayoutInflater().inflate(
						R.layout.patuvanja_lista, null);
			}
			Patuvanje c = getItem(position);
			// configure the view for this Crime
			TextView Od = (TextView) convertView
					.findViewById(R.id.patuvanja_Od);
			TextView Do = (TextView) convertView
					.findViewById(R.id.patuvanja_Do);
			TextView Vreme = (TextView) convertView
					.findViewById(R.id.patuvanja_Cena);
			TextView Cena = (TextView) convertView
					.findViewById(R.id.patuvanja_Vreme);
			Od.setText("Од: " + c.getGradOd());
			Do.setText("До: " + c.getGradDo());
			Cena.setText(String.valueOf(c.getCenaPatuvanje()) + " денари");
			Vreme.setText(String.valueOf(c.getDataPatuvanje()));

			return convertView;
		}

		@SuppressWarnings("unused")
		public ArrayList<Patuvanje> patuvanjaOdJson(InputStream jsonOdServer)
				throws IOException, JSONException {
			ArrayList<Patuvanje> patuvanja = new ArrayList<Patuvanje>();
			BufferedReader reader = null;
			try {
				// open and read the file into a StringBuilder
				reader = new BufferedReader(new InputStreamReader(jsonOdServer));
				StringBuilder jsonString = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					// line breaks are omitted and irrelevant
					jsonString.append(line);
				}
				// parse the JSON using JSONTokener
				JSONArray array = (JSONArray) new JSONTokener(
						jsonString.toString()).nextValue();
				// build the array of crimes from JSONObjects
				for (int i = 0; i < array.length(); i++) {
					patuvanja.add(new Patuvanje(array.getJSONObject(i)));
				}
			} catch (FileNotFoundException e) {
				// we will ignore this one, since it happens when we start fresh
			} finally {
				if (reader != null)
					reader.close();
			}
			return patuvanja;
		}
	}

	void toCreatingNewDestination() {
		Intent myIntent = new Intent(ControlPanelActivity.this,
				ForDriversActivity.class);
		startActivityForResult(myIntent, 000);
	}
	
	void getExtrasF(){
		Intent intent = getIntent();
		
		username = intent.getStringExtra("username");
		password = intent.getStringExtra("password");
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		Log.d("test","result");
		reLoad();
	}
	
	
}
